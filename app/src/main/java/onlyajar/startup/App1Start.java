package onlyajar.startup;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.List;

@AutoService(Initializer.class)
public class App1Start implements Initializer<String>{
    private static final String TAG = "AppStart";
    @NonNull
    @Override
    public String create(@NonNull Context context) {
        Log.d(TAG, "create: App1Start");
        return "AppStart";
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        List<Class<? extends  Initializer<?>>> des = new ArrayList<>();
        des.add(AppStart.class);
        return des;
    }
}
