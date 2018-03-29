package com.nicolasguillen.kointlin.ui.activities

import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.utils.DemoModeRule
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.locale.LocaleTestRule

@Suppress("unused")
@RunWith(AndroidJUnit4::class)
class NewsFeedActivityTest {

    companion object {

        @get:ClassRule
        @JvmStatic
        val localeTestRule = LocaleTestRule()

        @get:ClassRule
        @JvmStatic
        val demoModeRule = DemoModeRule()

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        }
    }

    @get:Rule
    val activityRule = ActivityTestRule(NewsFeedActivity::class.java)

    @Test
    fun testTakeScreenshot() {

        SystemClock.sleep(1500)

        onView(withId(R.id.news_feed_image)).check(matches(isDisplayed()))

        Screengrab.screenshot("03")
    }


}