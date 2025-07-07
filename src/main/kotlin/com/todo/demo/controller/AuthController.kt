package com.todo.demo.controller

import com.todo.demo.infrastructure.InMemoryUserRepository
import com.todo.demo.service.JwtTokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val userRepository: InMemoryUserRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    data class LoginRequest(
        val username: String,
    )

    data class AuthResponse(
        val token: String,
    )

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): ResponseEntity<AuthResponse> {
        val user = userRepository.findBy(request.username) ?: return ResponseEntity.badRequest().build()
        val token = jwtTokenProvider.createToken(user.username, user.roles)
        return ResponseEntity.ok(AuthResponse(token))
    }
}
