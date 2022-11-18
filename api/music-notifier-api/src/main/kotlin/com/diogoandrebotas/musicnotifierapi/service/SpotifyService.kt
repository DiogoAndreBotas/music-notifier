package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.SPOTIFY_USER_AGENT
import com.diogoandrebotas.musicnotifierapi.config.SpotifyProperties
import com.diogoandrebotas.musicnotifierapi.model.database.Artist
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyAlbumResponse
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyArtistAlbumsResponse
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyArtistSearchResponse
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyAuthResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class SpotifyService(
    val spotifyProperties: SpotifyProperties
) {

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                   ignoreUnknownKeys = true
                }
            )
        }
    }

    private val maxOffset = 20

    fun checkForNewArtistReleases(artists: List<Artist>)
        = artists.map { Pair(it, getSinglesAndAlbumsFromArtistForToday(it)) }

    fun getArtistData(artistName: String): SpotifyArtistSearchResponse {
        val accessToken = getAccessToken()

        return runBlocking {
            val responseBody: SpotifyArtistSearchResponse = httpClient.get(
                "${spotifyProperties.baseUrl}/search?type=artist&q=${artistName}&limit=1"
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                    append(HttpHeaders.UserAgent, SPOTIFY_USER_AGENT)
                }
            }.body()

            return@runBlocking responseBody
        }
    }

    private fun getSinglesAndAlbumsFromArtistForToday(
        artist: Artist,
        accessToken: String = getAccessToken(),
        albums: MutableList<SpotifyAlbumResponse> = mutableListOf(),
        offset: Int = 0
    ): MutableList<SpotifyAlbumResponse> {
        return runBlocking {
            val responseBody: SpotifyArtistAlbumsResponse = httpClient.get(
                "${spotifyProperties.baseUrl}/artists/${artist.id}/albums"
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                    append(HttpHeaders.UserAgent, SPOTIFY_USER_AGENT)
                }
                url {
                    parameters.append("offset", offset.toString())
                }
            }.body()

            if (responseBody.items.isEmpty()) {
                return@runBlocking albums
            }
            else {
                val today = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

                albums.addAll(responseBody.items.filter {
                    it.artists.map { artist -> artist.name }.contains(artist.name)
                            && (it.albumType == "album" || it.albumType == "single")
                            && it.releaseDate == today
                })

                return@runBlocking getSinglesAndAlbumsFromArtistForToday(
                    artist,
                    accessToken,
                    albums,
                    offset + maxOffset
                )
            }
        }
    }

    private fun getAccessToken(): String {
        return runBlocking {
            val token = "${spotifyProperties.clientId}:${spotifyProperties.clientSecret}"

            val base64Credentials = Base64
                .getEncoder()
                .encodeToString(token.toByteArray())

            val responseBody: SpotifyAuthResponse = httpClient.submitForm(
                url = spotifyProperties.tokenUrl,
                formParameters = Parameters.build {
                    append("grant_type", "client_credentials")
                },
                block = {
                    headers {
                        append(HttpHeaders.Authorization, "Basic $base64Credentials")
                        append(HttpHeaders.UserAgent, SPOTIFY_USER_AGENT)
                    }
                }
            ).body()

            return@runBlocking responseBody.accessToken
        }
    }
}
