package onlyajar.startup;

import android.content.Context;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import onlyajar.router.ServiceClassLoader;

public final class AppInitializer {

    // Tracing
    private static final String SECTION_NAME = "Startup";

    /**
     * The {@link AppInitializer} instance.
     */
    private static volatile AppInitializer sInstance;

    /**
     * Guards app initialization.
     */
    private static final Object sLock = new Object();

    final Map<Class<?>, Object> mInitialized;

    final Set<Class<? extends Initializer<?>>> mDiscovered;

    final Context mContext;

    AppInitializer(Context context) {
        mContext = context.getApplicationContext();
        mDiscovered = new HashSet<>();
        mInitialized = new HashMap<>();
    }

    public static AppInitializer getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new AppInitializer(context);
                }
            }
        }
        return sInstance;
    }

    static void setDelegate(AppInitializer delegate) {
        synchronized (sLock) {
            sInstance = delegate;
        }
    }

    public <T> T initializeComponent(Class<? extends Initializer<?>> component) {
        return doInitialize(component);
    }


    public boolean isEagerlyInitialized(Class<? extends Initializer<?>> component) {
        // If discoverAndInitialize() was never called, then nothing was eagerly initialized.
        return mDiscovered.contains(component);
    }

    <T> T doInitialize(Class<? extends Initializer<?>> component) {
        Object result;
        synchronized (sLock) {
            result = mInitialized.get(component);
            if (result == null) {
                result = doInitialize(component, new HashSet<Class<?>>());
            }
        }
        return (T) result;
    }

    private <T> T doInitialize(Class<? extends Initializer<?>> component, Set<Class<?>> initializing) {
        if (initializing.contains(component)) {
            String message = String.format(
                    "Cannot initialize %s. Cycle detected.", component.getName()
            );
            throw new IllegalStateException(message);
        }
        Object result;
        if (!mInitialized.containsKey(component)) {
            initializing.add(component);
            try {
                Initializer<?> instance = component.getDeclaredConstructor().newInstance();
                Initializer<?> initializer = instance;
                List<Class<? extends Initializer<?>>> dependencies = initializer.dependencies();
                if (!dependencies.isEmpty()) {
                    for (Class<? extends Initializer<?>> clazz : dependencies) {
                        if (!mInitialized.containsKey(clazz)) {
                            doInitialize(clazz, initializing);
                        }
                    }
                }
                result = initializer.create(mContext);
                initializing.remove(component);
                mInitialized.put(component, result);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        } else {
            result = mInitialized.get(component);
        }
        return (T) result;
    }

    void discoverAndInitialize() {
        Set<Class<?>> initializing = new HashSet<>();
        List<Class<Initializer<?>>> allInitializers = ServiceClassLoader.load(Initializer.class).getAllClasses();
        for (Class<Initializer<?>> initializerClass : allInitializers) {
            mDiscovered.add(initializerClass);
        }
        for (Class<? extends Initializer<?>> component : mDiscovered) {
            doInitialize(component, initializing);
        }
    }
}

