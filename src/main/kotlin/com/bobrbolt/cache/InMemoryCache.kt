package com.bobrbolt.cache

import com.bobrbolt.features.register.RegisterReceiveRemote
import kotlin.collections.*

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {

    val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}