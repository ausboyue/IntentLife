# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retain generated child class which implement IBinder.
# But these rules are not required,now.Close it to improve security.
#-keep class cn.icheny.intentlife.IBinder{*;}
#-keep class * implements cn.icheny.intentlife.IBinder{*;}

# Only retain members of IntentLife.
-keepclassmembers class cn.icheny.intentlife.IntentLife
# Retain proxy class name and members.
-keep class cn.icheny.intentlife.BinderProxy{*;}
