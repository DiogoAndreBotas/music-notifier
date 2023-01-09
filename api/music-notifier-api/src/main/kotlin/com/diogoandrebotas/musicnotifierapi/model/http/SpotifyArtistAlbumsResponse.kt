package com.diogoandrebotas.musicnotifierapi.model.http

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyArtistAlbumsResponse (
    val items: List<SpotifyReleaseResponse>,
    val offset: Int,
    val total: Int
)

@Serializable
data class SpotifyReleaseResponse (
    val artists: List<SpotifyArtistResponse>,
    val href: String,
    val id: String,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("total_tracks")
    val totalTracks: Int,
    @SerialName("album_type")
    val albumType: String,
    @SerialName("external_urls")
    val externalUrls: SpotifyUrl
)

@Serializable
data class SpotifyArtistResponse (
    val href: String,
    val id: String,
    val name: String
)

@Serializable
data class SpotifyUrl (
    @SerialName("spotify")
    val url: String
)
