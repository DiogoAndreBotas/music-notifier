package com.diogoandrebotas.musicnotifierapi.model.database

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Artist(
    @Id
    val id: String,
    val name: String,
    val subscribedUsers: Set<User>
) {
    override fun toString(): String {
        return "Artist(name='$name')"
    }
}
