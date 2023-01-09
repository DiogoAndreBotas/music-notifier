package com.diogoandrebotas.musicnotifierapi.model.dto

import com.diogoandrebotas.musicnotifierapi.model.database.Artist

data class ArtistRelease (
    val name: String,
    val mainArtist: Artist,
    val featuredArtists: List<Artist>,
    val type: ReleaseType,
    val releaseDate: String,
    val spotifyUrl: String
) {
    fun toEmailContent(): String {
        return "${mainArtist.name}: $name! Available on $spotifyUrl"
    }

    override fun toString(): String {
        return "ArtistRelease(name='$name', mainArtist=$mainArtist, " +
                "featuredArtists=$featuredArtists, type=$type, releaseDate='$releaseDate')"
    }
}
