package com.diogoandrebotas.musicnotifierapi.model.http

import com.diogoandrebotas.musicnotifierapi.model.database.Artist

data class UserResponse (
    val email: String,
    val subscribedArtists: Set<Artist>
)
