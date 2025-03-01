package com.elfeky.devdash.ui.screens.main_screens.more

import com.elfeky.domain.model.UserProfile

data class MoreScreenState(
    val isLoadingLogout: Boolean = false,
    val isLoggedOut: Boolean = false,
    val userProfile: UserProfile? = null,
    val isProfileLoading: Boolean = true,
    val logoutError: String = "",
    val profileError: String = ""

)
