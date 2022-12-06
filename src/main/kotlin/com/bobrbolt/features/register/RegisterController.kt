package com.bobrbolt.features.register

import com.bobrbolt.database.tokens.TokenDTO
import com.bobrbolt.database.tokens.Tokens
import com.bobrbolt.database.users.UserDTO
import com.bobrbolt.database.users.Users
import com.bobrbolt.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.postgresql.util.PSQLException
import java.util.*

class RegisterController(private val call: ApplicationCall) {
    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest)
        }

        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = registerReceiveRemote.password,
                        username = registerReceiveRemote.login,
                        email = registerReceiveRemote.email
                    )
                )
            } catch (e: PSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }


            Tokens.insert(
                TokenDTO(
                    id = UUID.randomUUID().toString(),
                    login = registerReceiveRemote.login,
                    token = token
                )
            )

            call.respond(RegisterResponseRemote(token = token))
        }
    }
}