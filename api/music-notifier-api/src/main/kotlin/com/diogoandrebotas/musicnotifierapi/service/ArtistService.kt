package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.model.database.Artist
import com.diogoandrebotas.musicnotifierapi.model.database.User
import com.diogoandrebotas.musicnotifierapi.repository.ArtistRepository
import org.springframework.stereotype.Service

@Service
class ArtistService(
    val artistRepository: ArtistRepository,
    val spotifyService: SpotifyService
) {

    fun addOrUpdateArtist(artistName: String, user: User): Artist {
        val artist = artistRepository.findArtistByName(artistName)

        return if (artist.isEmpty) {
            val spotifyArtist = spotifyService.getArtistData(artistName).artists.items.first()

            artistRepository.insert(Artist(spotifyArtist.id, spotifyArtist.name, mutableListOf(user)))
        } else {
            val subscribedUsers = artist.get().subscribedUsers
            subscribedUsers.add(user)

            artistRepository.insert(Artist(artist.get().id, artist.get().name, subscribedUsers))
        }
    }

    fun unsubscribeUserFromArtist(artistName: String, user: User) {
        val artist = artistRepository.findArtistByName(artistName)
        val subscribedUsers = artist.get().subscribedUsers

        subscribedUsers.remove(user)
        artistRepository.insert(Artist(artist.get().id, artist.get().name, subscribedUsers))
    }

    fun getAllArtists(): MutableList<Artist> = artistRepository.findAll()

}
