package com.diogoandrebotas.musicnotifierapi.model.database

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    val email: String,
    var password: String,
    val subscribedArtists: Set<Artist>
) {
    override fun toString(): String {
        return "User(email='$email', subscribedArtists=$subscribedArtists)"
    }
}
