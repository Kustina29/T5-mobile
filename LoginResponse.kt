package com.example.tugaspraktikum.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: LoginData?
)

data class LoginData(
    val token: String,
    val user: UserData
)

data class UserData(
    val name: String,
    val email: String
)