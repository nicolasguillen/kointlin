package com.nicolasguillen.kointlin.utils

import androidx.test.InstrumentationRegistry
import tools.fastlane.screengrab.*
import java.util.regex.Pattern

object Screengrab {
    private val TAG_PATTERN = Pattern.compile("[a-zA-Z0-9_-]+")
    var defaultScreenshotStrategy: ScreenshotStrategy = DecorViewScreenshotStrategy()

    @JvmOverloads
    fun screenshot(screenshotName: String, strategy: ScreenshotStrategy = defaultScreenshotStrategy) {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        screenshot(screenshotName, strategy, FileWritingScreenshotCallback(appContext))
    }

    fun screenshot(screenshotName: String, strategy: ScreenshotStrategy, callback: ScreenshotCallback) {
        if (!TAG_PATTERN.matcher(screenshotName).matches()) {
            throw IllegalArgumentException("screenshotName may only contain the letters a-z,  A-Z, the numbers 0-9, underscores, and hyphens")
        } else {
            strategy.takeScreenshot(screenshotName, callback)
        }
    }
}