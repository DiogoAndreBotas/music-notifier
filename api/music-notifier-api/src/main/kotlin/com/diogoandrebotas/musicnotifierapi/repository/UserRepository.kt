package com.diogoandrebotas.musicnotifierapi.repository

import com.diogoandrebotas.musicnotifierapi.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>
