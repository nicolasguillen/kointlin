package com.nicolasguillen.kointlin.ui.activities

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.utils.DemoModeRule
import com.nicolasguillen.kointlin.utils.LocaleTestRule
import com.nicolasguillen.kointlin.utils.Screengrab
import com.nicolasguillen.kointlin.utils.UiAutomatorScreenshotStrategy
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("unused")
@RunWith(AndroidJUnit4::class)
class AccountActivityTest {

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
            Screengrab.defaultScreenshotStrategy = UiAutomatorScreenshotStrategy()
        }
    }

    @get:Rule
    val activityRule = ActivityTestRule(AccountActivity::class.java)

    @Test
    fun testTakeScreenshot() {

        SystemClock.sleep(1500)

        onView(withId(R.id.account_toolbar)).check(matches(isDisplayed()))

        Screengrab.screenshot("01")
    }
}