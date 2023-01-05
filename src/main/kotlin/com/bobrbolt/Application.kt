package com.bobrbolt

import com.bobrbolt.features.login.configureLoginRouting
import com.bobrbolt.features.register.configureRegisterRouting
import com.bobrbolt.features.token.configureTokenRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import io.ktor.server.plugins.cors.*
import com.bobrbolt.plugins.*
import io.ktor.http.*
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
    install(CORS){
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Cookie)
        allowCredentials = true
        maxAgeInSeconds = 1
        anyHost()
    }
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureSerialization()
    configureTokenRouting()
}