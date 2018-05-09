package com.nicolasguillen.kointlin.ui.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.utils.DemoModeRule
import com.nicolasguillen.kointlin.utils.waitId
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.locale.LocaleTestRule
import java.util.concurrent.TimeUnit

@Suppress("unused")
@RunWith(AndroidJUnit4::class)
class NewAssetActivityTest {

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
    val activityRule = ActivityTestRule(NewAssetActivity::class.java)

    @Test
    fun testTakeScreenshot() {

        onView(withId(R.id.new_asset_amount)).perform(click())
        onView(withId(R.id.new_asset_input)).perform(typeText("Bitcoin"))
        onView(isRoot()).perform(waitId(R.id.new_asset_input, TimeUnit.MILLISECONDS.toMillis(200)))

        onView(withId(R.id.new_asset_amount)).perform(click())
        onView(withId(R.id.new_asset_amount)).perform(typeText("1"))
        onView(isRoot()).perform(waitId(R.id.new_asset_amount, TimeUnit.MILLISECONDS.toMillis(200)))

        onView(withId(R.id.new_asset_save)).check(matches(isEnabled()))

        Screengrab.screenshot("02")
    }


}