package com.bobrbolt.features.login

import com.bobrbolt.database.tokens.Tokens
import com.bobrbolt.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val loginReceiveRemote = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(loginReceiveRemote.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == loginReceiveRemote.password) {
                if (userDTO.login == loginReceiveRemote.login) {
                    Tokens.delete(userDTO.id)
                }
                val token = UUID.randomUUID().toString()
                Tokens.insert(userDTO.id, token)
                call.respond(LoginResponseRemote(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}