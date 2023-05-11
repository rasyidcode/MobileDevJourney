package com.rasyidcode.androiddaggerbasic.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rasyidcode.androiddaggerbasic.LiveDataTestUtil
import com.rasyidcode.androiddaggerbasic.user.UserManager
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class LoginViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var userManager: UserManager

    @Before
    fun setup() {
        userManager = mock(UserManager::class.java)
        viewModel = LoginViewModel(userManager)
    }

    @Test
    fun `Get username`() {
        `when`(userManager.username).thenReturn("Username")

        val username = viewModel.getUsername()

        assertEquals("Username", username)
    }

    @Test
    fun `Login emits success`() {
        `when`(userManager.loginUser(anyString(), anyString())).thenReturn(true)

        viewModel.login("username", "login")

        assertEquals(LiveDataTestUtil.getValue(viewModel.loginState), LoginViewState.LoginSuccess)
    }

    @Test
    fun `Login emits error`() {
        `when`(userManager.loginUser(anyString(), anyString())).thenReturn(false)

        viewModel.login("username", "login")

        assertEquals(LiveDataTestUtil.getValue(viewModel.loginState), LoginViewState.LoginError)
    }

    @Test
    fun `Login unregisters`() {
        viewModel.unregister()

        verify(userManager).unregister()
    }

}