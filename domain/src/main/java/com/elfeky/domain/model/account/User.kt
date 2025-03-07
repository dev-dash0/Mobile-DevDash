package com.elfeky.domain.model.account

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber:String,
    val password: String,
    val birthday:String,
)
