package com.rasyidcode.dogglresapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ButtonTests {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun vertical_list_button_is_displayed() {
        onView(withId(R.id.btn_vertical)).check(matches(isDisplayed()))
    }

    @Test
    fun horizontal_list_button_is_displayed() {
        onView(withId(R.id.btn_horizontal)).check(matches(isDisplayed()))
    }

    @Test
    fun grid_list_button_is_displayed() {
        onView(withId(R.id.btn_grid)).check(matches(isDisplayed()))
    }

    @Test
    fun clicking_vertical_list_button_displays_vertical_list() {
        onView(withId(R.id.btn_vertical)).perform(click())
        onView(withId(R.id.vertical_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun clicking_horizontal_list_button_displays_horizontal_list() {
        onView(withId(R.id.btn_horizontal)).perform(click())
        onView(withId(R.id.horizontal_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun clicking_grid_list_button_displays_grid_list() {
        onView(withId(R.id.btn_grid)).perform(click())
        onView(withId(R.id.grid_recycler_view)).check(matches(isDisplayed()))
    }
}