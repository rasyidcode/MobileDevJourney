package com.rasyidcode.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalculatorTests {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.0"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$10.0"))))
    }

    @Test
    fun calculate_18_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.0"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_eighteen_percent))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$9.0"))))
    }

    @Test
    fun calculate_15_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.0"))
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.option_fifteen_percent))
            .perform(click())
        onView(withId(R.id.calculate_button))
            .perform(click())
        onView(withId(R.id.tip_result))
            .check(matches(withText(containsString("$8.0"))))
    }
}