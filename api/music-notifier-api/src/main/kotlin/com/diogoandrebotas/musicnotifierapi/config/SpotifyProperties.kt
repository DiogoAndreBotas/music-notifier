package com.diogoandrebotas.musicnotifierapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "api.spotify")
data class SpotifyProperties(
    val clientId: String,
    val clientSecret: String,
    val baseUrl: String,
    val tokenUrl: String
)
