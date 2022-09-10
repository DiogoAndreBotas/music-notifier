package com.diogoandrebotas.musicnotifierapi.controller

import com.diogoandrebotas.musicnotifierapi.model.UserRequestBody
import com.diogoandrebotas.musicnotifierapi.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userRequestBody: UserRequestBody) {
        userService.registerUser(userRequestBody)
    }

}
