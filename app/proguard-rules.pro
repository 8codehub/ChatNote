# === Preserve core Android components ===
-keep public class * extends android.app.Application { *; }
-keep public class * extends androidx.lifecycle.ViewModel { *; }
-keep public class * extends android.content.BroadcastReceiver { *; }
-keep public class * extends android.app.Service { *; }
-keep public class * extends android.content.ContentProvider { *; }

# === Keep Hilt-generated classes (Dependency Injection) ===
-keep class dagger.hilt.** { *; }
-keep class com.google.dagger.hilt.** { *; }
-keep @dagger.hilt.EntryPoint class *

# === Keep Room Database classes (Entity, DAO, Database) ===
-keep class androidx.room.Entity { *; }
-keep class androidx.room.Dao { *; }
-keep class androidx.room.Database { *; }
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-keep class com.chatnote.coredata.database.** { *; } # ✅ Adjust based on your database package
-keep class com.chatnote.coredomain.models.** { *; } # ✅ Keep all data models
-keep class com.chatnote.common.** { *; } # ✅ Keep all data models

# === Keep all ViewModel & StatefulEventHandler (State Management) ===
-keep class com.chatnote.coreui.arch.StatefulEventHandler { *; }
-keep class com.chatnote.**.viewmodel.** { *; } # ✅ Keep all ViewModels
-keep class com.chatnote.**.event.** { *; } # ✅ Keep all event/state handlers

# === Keep Sealed Classes & Enums for Processing ===
-keepclassmembers,allowoptimization class * extends kotlin.sealed { *; }
-keepclassmembers enum * { *; }

-keepattributes Signature
-keepattributes *Annotation*

# === Prevent Obfuscation of Flow, StateFlow, and SharedFlow (Coroutines) ===
-keepclassmembers class kotlinx.coroutines.flow.** { *; }
-keepclassmembers class kotlinx.coroutines.channels.** { *; }

# === Remove logs from release build (Optimize APK) ===
-assumenosideeffects class android.util.Log { *; }

# === Keep certain UI elements that might be used via reflection ===
-keep class com.chatnote.coreui.components.** { *; }
-keep class com.chatnote.directnotesui.** { *; } # ✅ Adjust based on your UI packages

# Keep Firebase Analytics classes
-keep class com.google.firebase.analytics.** { *; }
-keep class com.google.android.gms.measurement.** { *; }

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
