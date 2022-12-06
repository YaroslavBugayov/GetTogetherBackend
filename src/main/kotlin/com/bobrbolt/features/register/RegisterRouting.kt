package com.bobrbolt.features.register

import com.bobrbolt.cache.InMemoryCache
import com.bobrbolt.cache.TokenCache
import com.bobrbolt.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRegisterRouting() {

    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
        }
    }
}