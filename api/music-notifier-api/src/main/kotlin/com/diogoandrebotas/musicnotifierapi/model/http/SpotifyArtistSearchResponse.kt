package com.diogoandrebotas.musicnotifierapi.model.http

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtistSearchResponse(
    val artists: SpotifyItemsResponse
)

@Serializable
data class SpotifyItemsResponse(
    val items: List<SpotifyArtistResponse>
)
