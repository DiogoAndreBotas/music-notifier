package com.diogoandrebotas.musicnotifierapi.cron

import com.diogoandrebotas.musicnotifierapi.service.ArtistService
import com.diogoandrebotas.musicnotifierapi.service.MailSenderService
import com.diogoandrebotas.musicnotifierapi.service.SpotifyService
import com.diogoandrebotas.musicnotifierapi.service.UserService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@Component
class ScheduledJobs(
    val artistService: ArtistService,
    val mailSenderService: MailSenderService,
    val spotifyService: SpotifyService,
    val userService: UserService
) {

    // TODO: time to be defined in properties
    @Scheduled(cron = "0 0 9 * * *")
    fun checkForNewArtistReleases() {
        val today: String = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        val artistReleasesMap = artistService.getAllArtists()
            .associateWith { spotifyService.getArtistReleases(it, today) }

        userService.getAllUsers()
            .forEach { user ->
                val relevantReleases = artistReleasesMap
                    .filter { entry -> user.subscribedArtists.contains(entry.key) }
                    .flatMap { it.value }

                if (relevantReleases.isNotEmpty())
                    mailSenderService.sendNewReleaseEmail(user.email, relevantReleases)
            }
    }
}

