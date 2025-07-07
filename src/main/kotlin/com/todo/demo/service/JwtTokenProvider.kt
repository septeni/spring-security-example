package com.todo.demo.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import com.todo.demo.config.JwtUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

@Component
class JwtTokenProvider {
    private val secret = "secret"

    private val issuer = "SpringSecurityDemo"

    fun createToken(
        username: String,
        roles: List<String>,
    ): String {
        val algorithm = Algorithm.HMAC512(secret)

        val now = LocalDateTime.now()
        val expiresAt = now.plusHours(1)

        val jwt =
            JWT
                .create()
                .withIssuedAt(Date())
                .withIssuer(issuer)
                .withArrayClaim("roles", roles.toTypedArray())
                .withSubject(username)
                .withExpiresAt(expiresAt.toInstant(ZoneOffset.UTC))

        return jwt.sign(algorithm)
    }

    fun validateToken(token: String) {
        val algorithm = Algorithm.HMAC512(secret)
        val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()
        verifier.verify(token)
    }

    fun getAuthentication(token: String): Authentication {
        validateToken(token)
        val decodedJwt = JWT.decode(token)

        val username = decodedJwt.subject
        val roles = decodedJwt.getClaim("roles").asList(String::class.java)
        val authorities = roles.map { SimpleGrantedAuthority(it.toString()) }

        val user = JwtUserDetails(username = username, authorities = authorities)
        return UsernamePasswordAuthenticationToken(user, null, authorities)
    }
}
