package org.scidsg.hushline.data.network

import kotlinx.serialization.Serializable

@Serializable
data class MessageRequest(
    val token: String
)
