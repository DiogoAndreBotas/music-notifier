package com.diogoandrebotas.musicnotifierapi.security

import com.diogoandrebotas.musicnotifierapi.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(email: String)
        = CustomUserDetails(userRepository.findById(email).get())

}
