package com.rasyidcode.androiddaggerbasic.registration.enterdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rasyidcode.androiddaggerbasic.LiveDataTestUtil
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EnterDetailsViewModelTest {

    // Executes each task synchronously using Architecture Components
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EnterDetailsViewModel

    @Before
    fun setup() {
        viewModel = EnterDetailsViewModel()
    }

    @Test
    fun `ValidateInput gives error when username is invalid`() {
        viewModel.validateInput("user", "password")

        assertTrue(LiveDataTestUtil.getValue(viewModel.enterDetailsViewState) is EnterDetailsViewState.EnterDetailsError)
    }

    @Test
    fun `ValidateInput gives error when password is invalid`() {
        viewModel.validateInput("username", "pass")

        assertTrue(LiveDataTestUtil.getValue(viewModel.enterDetailsViewState) is EnterDetailsViewState.EnterDetailsError)
    }

    @Test
    fun `ValidateInput succeeds when input is valid`() {
        viewModel.validateInput("username", "password")

        assertTrue(LiveDataTestUtil.getValue(viewModel.enterDetailsViewState) is EnterDetailsViewState.EnterDetailsSuccess)
    }

}