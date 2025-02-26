package com.elfeky.devdash.navigation.auth_nav

import kotlinx.serialization.Serializable

@Serializable
object Auth

@Serializable
object LoginRoute

@Serializable
object SignUpRoute

@Serializable
data class VerifyEmailRoute(val email: String, val resetPassword: Boolean)

@Serializable
object ResetPasswordRoute