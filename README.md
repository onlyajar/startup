# startup

[![](https://www.jitpack.io/v/onlyajar/startup.svg)](https://www.jitpack.io/#onlyajar/startup)
### 快速配置 

#### gradle
```groovy
implementation 'com.github.onlyajar:startup:0.0.1'
annotationProcessor 'com.google.auto.service:auto-service:1.1.1'
```
#### gradle.kts
```groovy
implementation("com.github.onlyajar:startup:0.0.1")
kapt("com.google.auto.service:auto-service:1.1.1")
```

### 使用说明
#### 无依赖执行
```java
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
```

#### 根据依赖按照顺序执行
```java
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
```

