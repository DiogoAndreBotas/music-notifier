package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.config.SpotifyProperties
import com.diogoandrebotas.musicnotifierapi.model.SpotifyArtistAlbumsResponse
import com.diogoandrebotas.musicnotifierapi.model.SpotifyArtistSearchResponse
import com.diogoandrebotas.musicnotifierapi.model.SpotifyAuthResponse
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SpotifyService {

    @Autowired
    lateinit var spotifyProperties: SpotifyProperties

    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                   ignoreUnknownKeys = true
                }
            )
        }
    }

    fun getArtistData(artistName: String): SpotifyArtistSearchResponse {
        val accessToken = getAccessToken()

        return runBlocking {
            val responseBody: SpotifyArtistSearchResponse = httpClient.get(
                "${spotifyProperties.baseUrl}/search?type=artist&q=${artistName}&limit=1"
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                    append(HttpHeaders.UserAgent, "Music Notifier")
                }
            }.body()

            return@runBlocking responseBody
        }
    }

    fun getAlbumsFromArtist(artistId: String): SpotifyArtistAlbumsResponse {
        val accessToken = getAccessToken()

        return runBlocking {
            val responseBody: SpotifyArtistAlbumsResponse = httpClient.get(
                "${spotifyProperties.baseUrl}/artists/$artistId/albums"
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                    append(HttpHeaders.UserAgent, "Music Notifier")
                }
            }.body()

            return@runBlocking responseBody
        }
    }

    private fun getAccessToken(): String {
        return runBlocking {
            val base64Credentials = Base64
                .getEncoder()
                .encodeToString(
                    "${spotifyProperties.clientId}:${spotifyProperties.clientSecret}"
                        .toByteArray()
                )

            val responseBody: SpotifyAuthResponse = httpClient.submitForm(
                url = spotifyProperties.tokenUrl,
                formParameters = Parameters.build {
                    append("grant_type", "client_credentials")
                },
                block = {
                    headers {
                        append(HttpHeaders.Authorization, "Basic $base64Credentials")
                        append(HttpHeaders.UserAgent, "Music Notifier")
                    }
                }
            ).body()

            println(responseBody)

            return@runBlocking responseBody.accessToken
        }
    }
}
