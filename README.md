# Kointlin [![CircleCI](https://circleci.com/gh/nicolasguillen10/kointlin.svg?style=svg)](https://circleci.com/gh/nicolasguillen10/kointlin)

<p align="center">
    <img src="app/src/main/res/drawable-xxxhdpi/ic_launcher.png" width="200">
</p>

Android project written in [Kotlin](https://kotlinlang.org/). A sample application that allows you to control your assets.

## Libraries
* [Kotlin Android Extensions](https://kotlinlang.org/docs/tutorials/android-plugin.html)
* [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Retrofit 2](http://square.github.io/retrofit/)
* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Dagger 2](http://google.github.io/dagger/)
* [Picasso](http://square.github.io/picasso/)
* [Google Support Libraries](http://developer.android.com/tools/support-library/index.html)

## Testing Libraries
* [JUnit](http://junit.org/junit4/)
* [Mockito](http://mockito.org/)
* [Espresso](https://developer.android.com/training/testing/espresso/index.html)

Deploying
------------------------------
All deploy builds require that the `kointlin.keystore` is present (see "Release" above)

### Beta

 1. Ensure all tests pass and the app can be built:

        ./gradlew clean build connectedCheck

 2. Update the version information in `build.gradle`.

 3. Update the information in `src/main/play` to reflect changes in style, app description, or
    what's new in the release.

 4. Generate screenshots using [fastlane](https://fastlane.tools/)

        fastlane screenshot

 5. Install the release APK onto a phone or emulator.

        ./gradlew :app:installRelease

 6. Upload everything to Google Playstore

        ./gradlew publishRelease

### Keystore

Release builds will use the debug signing key unless an upload.keystore file is present. This allows local builds to work without any setup to test things like ProGuard.

When an upload.keystore is present, a keystore password and key password are required. Place the following in `~/.gradle/gradle.properties`:

```
KOINTLIN_UPLOAD_STORE_PASSWORD=<password>
KOINTLIN_UPLOAD_KEY_PASSWORD=<password>
```

### Publishing

You must have an `upload.p12` and your actual email in `~/.gradle/gradle.properties` together with :

```
KOINTLIN_UPLOAD_PLAY_EMAIL=<email>
```

## About The Author

### Nicolás Guillén

Android Developer

<a href="https://play.google.com/store/apps/developer?id=Nicol%C3%A1s+Guill%C3%A9n" target="_blank"><img src="https://github.com/nicolasguillen10/social-icons/blob/master/play-store-icon.png?raw=true" width="60"></a>
<a href="https://instagram.com/nic0guillen" target="_blank"><img src="https://github.com/nicolasguillen10/social-icons/blob/master/instagram-icon.png?raw=true" width="60"></a>
<a href="http://linkedin.com/in/nicolasguillen10"><img src="https://github.com/nicolasguillen10/social-icons/blob/master/linkedin-icon.png?raw=true" width="60"></a>


## License

    Copyright 2017 Nicolás Guillén

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.