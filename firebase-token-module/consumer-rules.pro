-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault

-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}

# Keep GoogleCredentials and its constructors

-keep class com.google.** { *;}
-keep interface com.google.** { *;}
-dontwarn com.google.**

# Keep classes that might be instantiated via reflection (used by GoogleCredentials)
-keepclassmembers class * {
    public <init>(...);
}

-keep class java.io.ByteArrayInputStream { *;}

# Keep StandardCharsets class and its members
-keep class java.nio.charset.StandardCharsets { *; }


# Needed by google-http-client-android when linking against an older platform version
-dontwarn com.google.api.client.extensions.android.**

# Needed by google-api-client-android when linking against an older platform version
-dontwarn com.google.api.client.googleapis.extensions.android.**