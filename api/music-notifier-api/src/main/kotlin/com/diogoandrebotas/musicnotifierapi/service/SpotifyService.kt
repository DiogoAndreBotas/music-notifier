package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.SPOTIFY_USER_AGENT
import com.diogoandrebotas.musicnotifierapi.config.HttpClientConfig
import com.diogoandrebotas.musicnotifierapi.config.SpotifyProperties
import com.diogoandrebotas.musicnotifierapi.model.database.Artist
import com.diogoandrebotas.musicnotifierapi.model.dto.ArtistRelease
import com.diogoandrebotas.musicnotifierapi.model.dto.ReleaseType
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyArtistAlbumsResponse
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyArtistSearchResponse
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyReleaseResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class SpotifyService(
    val spotifyProperties: SpotifyProperties,
    val spotifyAuthService: SpotifyAuthService,
    val httpClientConfig: HttpClientConfig
) {

    // TODO: Find a way to better cache access token
    private val accessToken = spotifyAuthService.getAccessToken()
    private val maxOffset = 50
    private val limit = 50

    fun getArtist(artistName: String): SpotifyArtistSearchResponse {
        val artistNameEncoded = artistName.replace(" ", "%20")
        val httpClient = httpClientConfig.getHttpClient()

        return runBlocking {
            val responseBody: SpotifyArtistSearchResponse = httpClient.get(
                "${spotifyProperties.baseUrl}/search?type=artist&q=${artistNameEncoded}&limit=1"
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                    append(HttpHeaders.UserAgent, SPOTIFY_USER_AGENT)
                }
            }.body()

            responseBody
        }
    }

    fun getArtistReleases(artist: Artist, date: String): List<ArtistRelease> {
        val httpClient = httpClientConfig.getHttpClient()
        val releases = mutableListOf<SpotifyReleaseResponse>()
        var offset = 0

        runBlocking {
            do {
                val responseBody: SpotifyArtistAlbumsResponse = httpClient.get(
                    "${spotifyProperties.baseUrl}/artists/${artist.id}/albums"
                ) {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                        append(HttpHeaders.UserAgent, SPOTIFY_USER_AGENT)
                    }
                    url {
                        parameters.append("type", "album")
                        parameters.append("limit", limit.toString())
                        parameters.append("offset", offset.toString())
                    }
                }.body()

                releases.addAll(
                    responseBody.items.filter {
                        it.artists.any { foundArtist -> foundArtist.name == artist.name } && it.releaseDate == date
                    }
                )

                offset += maxOffset
            } while (responseBody.items.isNotEmpty())
        }

        return releases.map {
            ArtistRelease(
                it.name,
                artist,
                listOf(),
                ReleaseType.valueOf(it.albumType.uppercase()),
                it.releaseDate,
                it.externalUrls.url
            )
        }.distinctBy { it.name }
    }
}
