# Basic Android rules
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# Keep Kotlin metadata
-keepattributes *Annotation*,InnerClasses
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleAnnotations,RuntimeInvisibleParameterAnnotations
-keepattributes KotlinVisibility
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Keep coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# Keep serialization
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

# -------------------------------
# GSON Serialization/Deserialization
# -------------------------------
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes *Annotation*


# Keep domain models and interfaces
-keep class com.elfeky.domain.model.** { *; }
-keep interface com.elfeky.domain.repo.** { *; }
-keep class com.elfeky.domain.usecase.** { *; }

# Keep Retrofit services
-keepattributes Signature
-keepattributes *Annotation*
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembers class * {
    @retrofit2.http.* <fields>;
}

# Keep Moshi/Gson serialized classes
-keepclassmembers class com.elfeky.data.remote.** {
    public <init>(...);
}



# Keep all domain models (adjust package name)
-keep class com.elfeky.domain.model.** { *; }

# Keep toString() methods
-keepclassmembers class * {
    public java.lang.String toString();
}

-dontwarn java.lang.invoke.StringConcatFactory

# Keep injected classes
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
}

# Keep @Inject annotated members
-keepclassmembers class * {
    @javax.inject.* *;
}

# Keep Resource class and its subtypes
-keep class com.elfeky.domain.util.** { *; }
-keep class com.elfeky.domain.util.Resource { *; }
-keep class com.elfeky.domain.util.Resource$* { *; }