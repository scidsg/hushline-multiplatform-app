package org.scidsg.hushline.data.network

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponseData(
    val message: String
)

@Serializable
data class MessageResponse(
    val message: String,
    val data: List<MessageResponseData>
)
