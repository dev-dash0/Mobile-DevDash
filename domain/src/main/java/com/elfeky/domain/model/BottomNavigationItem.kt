package com.elfeky.domain.model

import androidx.annotation.DrawableRes

data class BottomNavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val route: String
)