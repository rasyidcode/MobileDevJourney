package com.rasyidcode.androiddaggerbasic.settings

import com.rasyidcode.androiddaggerbasic.user.UserDataRepository
import com.rasyidcode.androiddaggerbasic.user.UserManager

class SettingsViewModel(
    private val userDataRepository: UserDataRepository,
    private val userManager: UserManager
) {

    fun refreshNotifications() {
        userDataRepository.refreshUnreadNotification()
    }

    fun logout() {
        userManager.logout()
    }

}