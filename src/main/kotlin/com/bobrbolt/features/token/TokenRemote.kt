package com.bobrbolt.features.token

import kotlinx.serialization.Serializable

@Serializable
data class TokenReceiveRemote(
    val login: String,
    val token: String
)

@Serializable
data class TokenResponseRemote(
    val success: Boolean
)