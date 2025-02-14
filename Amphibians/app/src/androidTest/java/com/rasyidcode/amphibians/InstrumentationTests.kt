package com.rasyidcode.amphibians

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rasyidcode.amphibians.ui.AmphibianListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentationTests : BaseTest() {

    @Test
    fun recycler_view_content() {
        launchFragmentInContainer<AmphibianListFragment>(themeResId = R.style.Theme_Amphibians)
        waitForView(withText("Great Basin Spadefoot")).check(matches(isDisplayed()))
        waitForView(withText("Tiger Salamander")).check(matches(isDisplayed()))
    }

    @Test
    fun detail_content() {
        launchActivity<MainActivity>()
        waitForView(withText("Blue Jeans Frog")).perform(click())
        waitForView(withText("Blue Jeans Frog")).check(matches(isDisplayed()))
        waitForView(
            withText(
                "Sometimes called the Strawberry Poison-Dart Frog, this little " +
                        "amphibian is identifiable by its bright red body and blueish-purple arms and " +
                        "legs. The Blue Jeans Frog is not toxic to humans like some of its close " +
                        "relatives, but it can be harmful to some predators."
            )
        )
            .check(matches(isDisplayed()))
    }
}