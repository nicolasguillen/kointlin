-dontobfuscate

# Retrofit does reflection on generic parameters and InnerClass is required to use Signature.
-keepattributes Signature, InnerClasses

# Many libraries use JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Many libraries use Animal Sniffer to ensure they're API compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# Kotlin serialization generates sibling serializer classes which are looked up reflectively.
-keep class **.*$serializer { *; }

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# Picasso
-dontwarn com.squareup.okhttp.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase