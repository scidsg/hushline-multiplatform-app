package org.scidsg.hushline.data.network

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseData(
    val token: String
)

@Serializable
data class LoginResponse(
    val message: String,
    val data: LoginResponseData
)
