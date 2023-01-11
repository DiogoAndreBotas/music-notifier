package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.model.database.Artist
import com.diogoandrebotas.musicnotifierapi.model.database.User
import com.diogoandrebotas.musicnotifierapi.model.http.UserResponse
import com.diogoandrebotas.musicnotifierapi.repository.ArtistRepository
import com.diogoandrebotas.musicnotifierapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ArtistService(
    val artistRepository: ArtistRepository,
    val userRepository: UserRepository,
    val spotifyService: SpotifyService
) {

    fun subscribeToArtist(artistName: String, user: User): UserResponse {
        val artist = artistRepository.findArtistByName(artistName).orElseGet {
            val spotifyArtist = spotifyService.getArtist(artistName)
                .artists
                .items
                .first()
            Artist(spotifyArtist.id, spotifyArtist.name)
        }

        val updatedArtist = artistRepository.save(artist)

        val updatedUser = User(user.email, user.password, user.subscribedArtists + updatedArtist)
        userRepository.save(updatedUser)

        return UserResponse(updatedUser.email, updatedUser.subscribedArtists)
    }

    // TODO: exception handling in case artist isn't subscribed to by user
    fun unsubscribeUserFromArtist(artistName: String, user: User): UserResponse {
        val artist = artistRepository.findArtistByName(artistName).get()

        val updatedUser = User(user.email, user.password, user.subscribedArtists - artist)
        userRepository.save(updatedUser)

        return UserResponse(updatedUser.email, updatedUser.subscribedArtists)
    }

    fun getAllArtists(): MutableList<Artist> = artistRepository.findAll()

}
