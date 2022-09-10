package com.diogoandrebotas.musicnotifierapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User (
    @Id
    val email: String,
    var password: String
)
