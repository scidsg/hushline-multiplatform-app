package org.scidsg.hushline.data.network

import kotlinx.serialization.Serializable

/*@Serializable
data class RegisterResponseData(
    val token: String
)*/

@Serializable
data class RegisterResponse(
    val message: String,
    //val data: RegisterResponseData
)
