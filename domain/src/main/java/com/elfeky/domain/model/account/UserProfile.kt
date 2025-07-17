package com.elfeky.domain.model.account

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("Id", alternate = ["id"]) val id: Int,
    @SerializedName("ImageUrl", alternate = ["imageUrl"]) val imageUrl: String?,
    @SerializedName("Email", alternate = ["email"]) val email: String,
    @SerializedName("FirstName", alternate = ["firstName"]) val firstName: String,
    @SerializedName("LastName", alternate = ["lastName"]) val lastName: String,
    @SerializedName("UserName", alternate = ["userName"]) val userName: String,
    @SerializedName("PhoneNumber", alternate = ["phoneNumber"]) val phoneNumber: String,
    @SerializedName("Birthday", alternate = ["birthday"]) val birthday: String,
    @SerializedName("PersonaltenantId", alternate = ["personaltenantId"]) val personalTenantId: Int,
)