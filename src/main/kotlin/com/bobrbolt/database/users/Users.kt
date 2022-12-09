package com.bobrbolt.database.users

import com.bobrbolt.features.register.RegisterReceiveRemote
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {
    val id = Users.integer("id").uniqueIndex()
    val login = Users.varchar("login", 25)
    val password = Users.varchar("password", 50)
    val email = Users.varchar("email", 50)

    fun insert(registerReceiveRemote: RegisterReceiveRemote) {
        transaction {
            Users.insert {
                it[login] = registerReceiveRemote.login
                it[password] = registerReceiveRemote.password
                it[email] = registerReceiveRemote.email ?: ""
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    id = userModel[Users.id],
                    login = userModel[Users.login],
                    password = userModel[password],
                    email = userModel[email]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}