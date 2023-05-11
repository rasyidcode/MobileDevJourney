package com.rasyidcode.androiddaggerbasic.main

import com.rasyidcode.androiddaggerbasic.user.UserDataRepository
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainViewModelTest {

    private lateinit var userDataRepository: UserDataRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        userDataRepository = mock(UserDataRepository::class.java)
        viewModel = MainViewModel(userDataRepository)
    }

    @Test
    fun `Welcome text returns right text`() {
        `when`(userDataRepository.username).thenReturn("username")

        assertEquals("Hello username!", viewModel.welcomeText)
    }

    @Test
    fun `Notifications text returns right text`() {
        `when`(userDataRepository.unreadNotification).thenReturn(5)

        assertEquals("You have 5 unread notifications", viewModel.notificationText)
    }

}