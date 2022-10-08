package com.diogoandrebotas.musicnotifierapi.model.http

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SpotifyAuthResponse (
    @SerialName("access_token")
    val accessToken: String
)
