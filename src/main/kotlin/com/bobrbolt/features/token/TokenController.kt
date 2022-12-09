package com.bobrbolt.features.token

import com.bobrbolt.database.tokens.Tokens
import com.bobrbolt.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TokenController(private val call: ApplicationCall) {

    suspend fun getToken() {
        val tokenReceiveRemote = call.receive<TokenReceiveRemote>()
        val userDTO = Users.fetchUser(tokenReceiveRemote.login)
        if (userDTO != null) {
            val tokenDTO = Tokens.getToken(userDTO.id)

            if (tokenDTO == null) {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {
                if (tokenDTO.token == tokenReceiveRemote.token) {
                    call.respond(TokenResponseRemote(success = true))
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Tokens don't match")
                }
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }



    }
}