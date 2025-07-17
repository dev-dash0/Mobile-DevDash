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

# Prevent obfuscation of model classes
-keepclassmembers class com.elfeky.domain.model.** {
    *;
}

# Alternative: Keep all classes with @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Prevent obfuscation of types used as GSON generic type tokens
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.** { *; }

# Keep classes annotated with @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
# GSON needs these for parsing JSON into objects
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


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

# Keep all repository implementations
-keep class com.elfeky.data.repo.** { *; }

# Keep all remote/data classes
-keep class com.elfeky.data.remote.** { *; }

-dontwarn com.elfeky.domain.usecase.local_storage.AccessTokenUseCase

# Keep injected classes
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
}

# Keep @Inject annotated members
-keepclassmembers class * {
    @javax.inject.* *;
}