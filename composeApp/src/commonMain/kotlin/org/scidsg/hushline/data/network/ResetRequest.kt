package org.scidsg.hushline.data.network

import kotlinx.serialization.Serializable

@Serializable
data class ResetRequest(
    val emailAddress: String
)
