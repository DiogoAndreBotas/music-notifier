package com.diogoandrebotas.musicnotifierapi.model.http

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtistSearchResponse(
    val artists: SpotifyArtistItemsResponse
)

@Serializable
data class SpotifyArtistItemsResponse(
    val items: List<SpotifyArtistResponse>
)
