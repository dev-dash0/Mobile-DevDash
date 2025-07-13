package com.elfeky.devdash.ui.screens.main_screens.profile

import com.elfeky.domain.model.account.UserProfile

data class ProfileScreenState(
    val isLoadingLogout: Boolean = false,
    val isLoggedOut: Boolean = false,
    val logoutError: String? = null,

    val isProfileLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val profileError: String? = null,

    val isLoadingDelete: Boolean = false,
    val isAccountDeleted: Boolean = false,
    val deleteAccountError: String? = null,

    val isLoadingChangePassword: Boolean = false,
    val passwordChanged: Boolean = false,
    val changePasswordError: String? = null,

    val updateProfileError: String? = null,

    val isReloading: Boolean = false
)
