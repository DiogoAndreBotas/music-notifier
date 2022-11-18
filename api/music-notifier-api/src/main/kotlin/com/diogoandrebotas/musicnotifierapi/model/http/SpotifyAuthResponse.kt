package com.diogoandrebotas.musicnotifierapi.model.http

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyAuthResponse (
    @SerialName("access_token")
    val accessToken: String
)
