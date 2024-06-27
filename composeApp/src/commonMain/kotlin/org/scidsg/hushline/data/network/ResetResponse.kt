package org.scidsg.hushline.data.network

import kotlinx.serialization.Serializable


@Serializable
data class ResetResponseData(
    val link: String
)

@Serializable
data class ResetResponse(
    val message: String,
    val data: ResetResponseData
)
