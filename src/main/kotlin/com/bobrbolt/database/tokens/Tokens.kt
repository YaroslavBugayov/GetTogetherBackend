package com.bobrbolt.database.tokens

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens: Table() {
    private val login = Tokens.varchar("login", 25)
    private val token = Tokens.varchar("token", 75)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun delete(login: String) {
        transaction {
            Tokens.deleteWhere { Tokens.login.eq(login) }
        }
    }

}