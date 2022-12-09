package com.bobrbolt.database.tokens

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens: Table() {
    val id = Tokens.integer("id").uniqueIndex()
    val userId = Tokens.integer("userid")
    val token = Tokens.varchar("token", 75)

    fun insert(userIdInput: Int, tokenInput: String) {
        transaction {
            Tokens.insert {
                it[userId] = userIdInput
                it[token] = tokenInput
            }
        }
    }

    fun delete(userId: Int) {
        transaction {
            Tokens.deleteWhere { Tokens.userId.eq(userId) }
        }
    }

    fun getToken(userId: Int): TokenDTO? {
        return try {
            transaction {
                val tokenModel = Tokens.select { Tokens.userId.eq(userId) }.single()
                TokenDTO(
                    id = tokenModel[Tokens.id],
                    userId = tokenModel[Tokens.userId],
                    token = tokenModel[token]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

}