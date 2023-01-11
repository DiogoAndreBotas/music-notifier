package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.model.database.User
import com.diogoandrebotas.musicnotifierapi.model.http.UserRequestBody
import com.diogoandrebotas.musicnotifierapi.repository.UserRepository
import com.diogoandrebotas.musicnotifierapi.security.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder
) {

    fun registerUser(userRequestBody: UserRequestBody) = userRepository.insert(
        User(userRequestBody.email, passwordEncoder.encode(userRequestBody.password), setOf())
    )

    fun getLoggedInUser() = userRepository.findById(getCurrentUserEmail()).get()

    fun getAllUsers(): List<User> = userRepository.findAll()

    private fun getCurrentUserEmail()
        = (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).username

}
