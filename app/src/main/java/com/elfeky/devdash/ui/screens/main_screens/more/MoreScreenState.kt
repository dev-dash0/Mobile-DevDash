package com.elfeky.devdash.ui.screens.main_screens.more

import com.elfeky.domain.model.account.UserProfile

data class MoreScreenState(
    val isLoadingLogout: Boolean = false,
    val isLoggedOut: Boolean = false,

    val userProfile: UserProfile? = null,
    val isProfileLoading: Boolean = true,

    val isLoadingDelete: Boolean = false,
    val isAccountDeleted: Boolean = false,

    val isLoadingChangePassword: Boolean = false,
    val passwordChanged: Boolean = false,

    val logoutError: String = "",
    val profileError: String = "",
    val deleteAccountError: String = "",
    val changePasswordError: String = "",

    )
