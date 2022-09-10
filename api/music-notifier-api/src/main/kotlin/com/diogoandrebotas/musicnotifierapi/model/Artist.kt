package com.diogoandrebotas.musicnotifierapi.model

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Artist (
    val id: String,
    val name: String,
    val subscribedUsers: MutableList<User>
)
