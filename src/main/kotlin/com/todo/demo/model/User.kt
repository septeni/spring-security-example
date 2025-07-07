package com.todo.demo.model

data class User(
    val username: String,
    val roles: List<String>,
)

interface UserRepository {
    fun findBy(userName: String): User?
}
