package com.todo.demo.infrastructure

import com.todo.demo.model.User
import com.todo.demo.model.UserRepository
import org.springframework.stereotype.Repository

@Repository
class InMemoryUserRepository : UserRepository {
    val users =
        listOf<User>(
            User("user", listOf("USER")),
            User("admin", listOf("USER", "ADMIN")),
        )

    override fun findBy(userName: String): User? = users.find { it.username == userName }
}
