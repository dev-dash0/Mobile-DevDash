package com.elfeky.domain.model.join

data class UpdateRoleRequest(
    val newRole: String,
    val userId: Int
)
