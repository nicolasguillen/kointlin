package com.nicolasguillen.kointlin

import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nicolasguillen.kointlin.ui.activities.AccountActivity
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tools.fastlane.screengrab.Screengrab
import tools.fastlane.screengrab.UiAutomatorScreenshotStrategy
import tools.fastlane.screengrab.locale.LocaleTestRule

@RunWith(AndroidJUnit4::class)
class AccountActivityTest {

    companion object {

        @ClassRule
        @JvmStatic
        val localeTestRule = LocaleTestRule()

        @ClassRule
        @JvmStatic
        val demoModeRule = DemoModeRule()

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            Screengrab.setDefaultScreenshotStrategy(UiAutomatorScreenshotStrategy())
        }
    }

    @Rule
    val activityRule = ActivityTestRule(AccountActivity::class.java)

    @Test
    fun displayHello() {
        SystemClock.sleep(2000)
        Screengrab.screenshot("01_hello_world_screen")
        onView(withId(R.id.account_toolbar)).check(matches(isDisplayed()))
    }
}