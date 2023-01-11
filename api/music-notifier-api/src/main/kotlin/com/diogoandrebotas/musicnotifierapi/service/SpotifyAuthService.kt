package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.SPOTIFY_USER_AGENT
import com.diogoandrebotas.musicnotifierapi.config.HttpClientConfig
import com.diogoandrebotas.musicnotifierapi.config.SpotifyProperties
import com.diogoandrebotas.musicnotifierapi.model.http.SpotifyAuthResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.util.*

// TODO: try to use auth token, if not valid, request again
@Service
class SpotifyAuthService (
    val spotifyProperties: SpotifyProperties,
    val httpClientConfig: HttpClientConfig
) {
    fun getAccessToken(): String {
        val authToken = "${spotifyProperties.clientId}:${spotifyProperties.clientSecret}"
        val base64Token = Base64.getEncoder().encodeToString(authToken.toByteArray())

        return runBlocking {
            httpClientConfig.getHttpClient()
                .submitForm(
                    url = spotifyProperties.tokenUrl,
                    formParameters = Parameters.build {
                        append("grant_type", "client_credentials")
                    },
                    block = {
                        headers {
                            append(HttpHeaders.Authorization, "Basic $base64Token")
                            append(HttpHeaders.UserAgent, SPOTIFY_USER_AGENT)
                        }
                    }
                )
                .body<SpotifyAuthResponse>()
                .accessToken
        }
    }

}
