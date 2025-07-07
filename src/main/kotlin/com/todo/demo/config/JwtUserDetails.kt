package com.todo.demo.config

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class JwtUserDetails(
    private val username: String,
    private val authorities: Collection<GrantedAuthority>,
) : UserDetails {
    override fun getAuthorities() = authorities

    override fun getPassword() = ""

    override fun getUsername() = username
}
