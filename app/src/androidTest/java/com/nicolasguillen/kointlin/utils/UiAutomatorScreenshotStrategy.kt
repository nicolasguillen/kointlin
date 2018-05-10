package com.nicolasguillen.kointlin.utils

import android.annotation.TargetApi
import android.os.Build
import androidx.test.InstrumentationRegistry
import tools.fastlane.screengrab.ScreenshotCallback
import tools.fastlane.screengrab.ScreenshotStrategy

class UiAutomatorScreenshotStrategy : ScreenshotStrategy {

    @TargetApi(18)
    override fun takeScreenshot(screenshotName: String, screenshotCallback: ScreenshotCallback) {
        if (Build.VERSION.SDK_INT < 18) {
            throw RuntimeException("UiAutomatorScreenshotStrategy requires API level >= 18")
        } else {
            val uiAutomation = InstrumentationRegistry.getInstrumentation().getUiAutomation()
            screenshotCallback.screenshotCaptured(screenshotName, uiAutomation.takeScreenshot())
        }
    }
}
