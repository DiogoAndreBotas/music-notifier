package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.model.database.User
import com.diogoandrebotas.musicnotifierapi.model.http.UserRequestBody
import com.diogoandrebotas.musicnotifierapi.repository.UserRepository
import com.diogoandrebotas.musicnotifierapi.security.CustomUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder) {

    fun registerUser(userRequestBody: UserRequestBody)
        = userRepository.insert(User(userRequestBody.email, passwordEncoder.encode(userRequestBody.password)))

    fun getLoggedInUser() = userRepository.findById(getCurrentUserEmail()).get()

    private fun getCurrentUserEmail()
        = (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).username

}
