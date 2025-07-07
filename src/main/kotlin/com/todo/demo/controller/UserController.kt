package com.todo.demo.controller

import com.todo.demo.config.JwtUserDetails
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @GetMapping("/admin")
    fun admin(
        @AuthenticationPrincipal userDetails: JwtUserDetails,
    ): String = "Hello ${userDetails.username} from /admin"

    @GetMapping("/user")
    fun user(
        @AuthenticationPrincipal userDetails: JwtUserDetails,
    ): String = "Hello ${userDetails.username} from /user"
}
