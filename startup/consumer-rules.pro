-keep interface onlyajar.startup.Initializer { *; }
# 保留所有直接或间接实现 Initializer 接口的类
-keep class * implements onlyajar.startup.Initializer { *; }