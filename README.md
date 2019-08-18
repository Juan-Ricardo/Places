# Places
Es una aplicación móvil para android, *desde la API 21 hasta API 28*.

# Dependencias
**build.gradle (módulo):**
```javascript
	dependencies {
    	implementation fileTree(dir: 'libs', include: ['*.jar'])
        implementation 'androidx.appcompat:appcompat:1.0.2'
        implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
        implementation 'com.google.android.material:material:1.0.0'
        implementation 'com.squareup.picasso:picasso:2.71828'
        implementation 'androidx.legacy:legacy-support-v4:1.0.0'
        implementation 'com.airbnb.android:lottie:3.0.1'
        implementation 'com.karumi:dexter:5.0.0'
        implementation "androidx.room:room-runtime:2.0.0"
        annotationProcessor "androidx.room:room-compiler:2.0.0"
        implementation 'com.facebook.stetho:stetho:1.5.1'
        implementation 'com.tapadoo.android:alerter:2.0.6'
	}
```