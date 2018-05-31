# Kointlin [![CircleCI](https://circleci.com/gh/nicolasguillen/kointlin.svg?style=svg)](https://circleci.com/gh/nicolasguillen/kointlin) [![codebeat badge](https://codebeat.co/badges/6406a91e-12aa-4e98-9ee1-2c482d79e963)](https://codebeat.co/projects/github-com-nicolasguillen-kointlin-master)

<p align="center">
    <img src="app/src/main/ic_launcher-web.png" width="200">
</p>

Android project written in [Kotlin](https://kotlinlang.org/). A sample application that allows you to control your assets.

## Libraries
* [Android Jetpack](https://developer.android.com/jetpack/)
    * [AppCompat](http://developer.android.com/tools/support-library/index.html)
    * [Android KTX](https://github.com/android/android-ktx)
    * [JUnit](http://junit.org/junit4/)
    * [Room](https://developer.android.com/topic/libraries/architecture/room.html)
* [Retrofit 2](http://square.github.io/retrofit/)
* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Dagger 2](http://google.github.io/dagger/)
* [Picasso](http://square.github.io/picasso/)
* [Mockito](http://mockito.org/)

Deploying
------------------------------
### Requirements

#### Keystore

Release builds will use the debug signing key unless a `kointlin.keystore` file is present. This allows local builds to work without any setup to test things like ProGuard.

When an kointlin.keystore is present, a keystore password and key password are required. Place the following in `~/.gradle/gradle.properties`:

```
KOINTLIN_UPLOAD_STORE_PASSWORD=<password>
KOINTLIN_UPLOAD_KEY_PASSWORD=<password>
```

#### GooglePlay credentials
Get your `upload.json` from the Google Developer Console

### Steps

 1. Update the version information in `build.gradle`.

 2. Run all tests and generate screenshots using [fastlane](https://fastlane.tools/)

    ```
    fastlane screenshot
    ```

 3. Install the release APK onto a phone or emulator.

    ```
    ./gradlew :app:installRelease
    ```

 4. Upload everything to Google Playstore

    ```
    fastlane apk_release
    ```
    or

    ```
    fastlane aab_release
    ```


About The Author
------------------------------

### Nicolás Guillén

Android Developer

<a href="https://play.google.com/store/apps/developer?id=Nicol%C3%A1s+Guill%C3%A9n" target="_blank"><img src="https://github.com/nicolasguillen10/social-icons/blob/master/play-store-icon.png?raw=true" width="60"></a>
<a href="https://instagram.com/nic0guillen" target="_blank"><img src="https://github.com/nicolasguillen10/social-icons/blob/master/instagram-icon.png?raw=true" width="60"></a>
<a href="http://linkedin.com/in/nicolasguillen10"><img src="https://github.com/nicolasguillen10/social-icons/blob/master/linkedin-icon.png?raw=true" width="60"></a>

License
------------------------------

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