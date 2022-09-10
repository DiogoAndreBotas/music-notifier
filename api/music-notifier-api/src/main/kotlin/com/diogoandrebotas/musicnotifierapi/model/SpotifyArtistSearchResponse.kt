package com.diogoandrebotas.musicnotifierapi.model

@kotlinx.serialization.Serializable
data class SpotifyArtistSearchResponse(
    val artists: SpotifyItemsResponse
)

@kotlinx.serialization.Serializable
data class SpotifyItemsResponse(
    val items: List<SpotifyArtistResponse>
)
