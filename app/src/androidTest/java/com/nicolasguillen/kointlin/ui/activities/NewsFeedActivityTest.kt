package com.nicolasguillen.kointlin.ui.activities

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.utils.DemoModeRule
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.view.ViewGroup
import com.nicolasguillen.kointlin.utils.LocaleTestRule
import com.nicolasguillen.kointlin.utils.Screengrab
import com.nicolasguillen.kointlin.utils.UiAutomatorScreenshotStrategy
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


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
            Screengrab.defaultScreenshotStrategy = UiAutomatorScreenshotStrategy()
        }
    }

    @get:Rule
    val activityRule = ActivityTestRule(NewsFeedActivity::class.java)

    @Test
    fun testTakeScreenshot() {

        SystemClock.sleep(1500)

        onView(nthChildOf(withId(R.id.set_currency_list), 0))
                .check(matches(isDisplayed()))

        Screengrab.screenshot("03")
    }

    private fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with $childPosition child view of type parentMatcher")
            }

            override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) {
                    return parentMatcher.matches(view.parent)
                }

                val group = view.parent as ViewGroup
                return parentMatcher.matches(view.parent) && group.getChildAt(childPosition) == view
            }
        }
    }
}