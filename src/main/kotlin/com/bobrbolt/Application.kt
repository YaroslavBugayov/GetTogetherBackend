package com.bobrbolt

import com.bobrbolt.features.login.configureLoginRouting
import com.bobrbolt.features.register.configureRegisterRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import com.bobrbolt.plugins.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/GetTogether",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "jkCNbC9PGaxGqGcqXUVk")

    embeddedServer(CIO, port = 8080, host = "127.0.0.1", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureSerialization()
}
