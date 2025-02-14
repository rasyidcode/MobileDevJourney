package com.rasyidcode.lemonadeapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.rasyidcode.lemonadeapp.DrawableMatcher.withDrawable
import org.hamcrest.Description
import org.hamcrest.Matcher

open class BaseTest {

    /**
     * Test to ensure the app is in the correct state.
     * @param textActionResource Integer for the expected text resource.
     * @param drawableResource Integer for the expected drawable resource.
     */
    fun testState(textActionResource: Int, drawableResource: Int) {
        onView(withId(R.id.text_action))
            .check(matches(ViewMatchers.withText(textActionResource)))
        onView(withId(R.id.image_lemon_state)).check(
            matches(withDrawable(drawableResource)))
    }

    /**
     * Clicks the lemon tree image.
     */
    fun pickLemon() {
        onView(withDrawable(R.drawable.lemon_tree))
            .perform(click())
    }

    /**
     * Squeeze the lemon until the lemon image is gone.
     * The number of clicks required is determined by a random number that the test is not
     * aware of, so we loop and click until the image changes.
     */
    fun juiceLemon() {
        while (onView(withDrawable(R.drawable.lemon_squeeze)).isPresent()) {
            onView(withId(R.id.image_lemon_state)).perform(click())
        }
    }

    /**
     * Click the lemonade image.
     */
    fun drinkJuice() {
        onView(withDrawable(R.drawable.lemon_drink))
            .perform(click())
    }

    /**
     * Click the empty glass image to restart.
     */
    fun restart() {
        onView(withDrawable(R.drawable.lemon_restart))
            .perform(click())
    }

    /**
     * Extension function to determine if element is present.
     * This is used to click the lemon image until it changes because the number of clicks
     * required is determined by a random number that the test doesn't know about.
     */
    private fun ViewInteraction.isPresent(): Boolean {
        return try {
            check(matches(isDisplayed()))
            true
        } catch (e: NoMatchingViewException) {
            false
        }
    }
}

/**
 * Custom matcher to find drawable.
 */
object DrawableMatcher {

    fun withDrawable(@DrawableRes resourceId: Int): Matcher<View> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description?) {
                description!!.appendText("has drawable resource $resourceId")
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                return isSameBitmap(imageView, imageView.drawable, resourceId)
            }
        }
    }

    private fun isSameBitmap(item: View, drawable: Drawable?, expectedResId: Int): Boolean {
        val image = item as ImageView
        if (expectedResId < 0) {
            return image.drawable == null
        }
        val expectedDrawable: Drawable? = ContextCompat.getDrawable(item.context, expectedResId)
        if (drawable == null || expectedDrawable == null) {
            return false
        }
        // Make tint consistent just in case they differ
        val bitmap = getBitmap(drawable)
        val expectedBitmap = getBitmap(expectedDrawable)
        return bitmap.sameAs(expectedBitmap)
    }

    /**
     * Convert vector drawable to bitmap
     * @param drawable vector drawable
     */
    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

}