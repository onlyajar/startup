package onlyajar.startup;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.auto.service.AutoService;

import java.util.Collections;
import java.util.List;

@AutoService(Initializer.class)
public class AppStart implements Initializer<String>{
    private static final String TAG = "AppStart";
    @NonNull
    @Override
    public String create(@NonNull Context context) {
        Log.d(TAG, "create: ");
        return "AppStart";
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
