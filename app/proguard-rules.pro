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

# Keep all DI-related classes (Hilt/Dagger)
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManagerHolder { *; }
-keep class dagger.hilt.** { *; }
-keep class com.elfeky.devdash.DaggerDevDashApplication_HiltComponents_* { *; }

# Keep Hilt/Dagger generated classes
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponentManagerHolder { *; }

# Keep Hilt entry points
-keep @dagger.hilt.InstallIn class * { *; }
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }

# Keep Hilt processors
-keep class * extends dagger.hilt.processor.internal.aggregateddeps.AggregatedDeps { *; }

# Keep injected classes
-keepclassmembers class * {
    @javax.inject.Inject <init>(...);
}

# Keep @Inject annotated members
-keepclassmembers class * {
    @javax.inject.* *;
}

# Keep all repository implementations
-keep class com.elfeky.data.repo.** { *; }

# Keep all use cases
-keep class com.elfeky.domain.usecase.** { *; }

#keep all repositories
-keep class com.elfeky.domain.repo.** { *; }

# Keep all remote/data classes
-keep class com.elfeky.data.remote.** { *; }

# Keep Resource class and its subtypes
-keep class com.elfeky.domain.util.** { *; }
-keep class com.elfeky.domain.util.Resource { *; }
-keep class com.elfeky.domain.util.Resource$* { *; }

# Keep all members (including fields and methods)
-keepclassmembers class com.elfeky.domain.util.Resource { *; }
-keepclassmembers class com.elfeky.domain.util.Resource$* { *; }


# Keep model classes
-keep class com.elfeky.domain.model.** { *; }

# For GSON serialization
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory { *; }

# Prevent duplicate class conflicts
-dontwarn a.a
-ignorewarnings

# Keep ViewModels and Activities
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends android.app.Activity { *; }

# Keep @AndroidEntryPoint classes
-keep @dagger.hilt.android.AndroidEntryPoint class * { *; }
