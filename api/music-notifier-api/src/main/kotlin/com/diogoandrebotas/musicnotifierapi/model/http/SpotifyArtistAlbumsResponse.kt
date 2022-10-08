package com.diogoandrebotas.musicnotifierapi.model.http

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class SpotifyArtistAlbumsResponse (
    val items: List<SpotifyAlbumResponse>,
    val offset: Int,
    val total: Int
)

@kotlinx.serialization.Serializable
data class SpotifyAlbumResponse (
    val artists: List<SpotifyArtistResponse>,
    val href: String,
    val id: String,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("total_tracks")
    val totalTracks: Int,
    @SerialName("album_type")
    val albumType: String
)

@kotlinx.serialization.Serializable
data class SpotifyArtistResponse (
    val href: String,
    val id: String,
    val name: String
)
