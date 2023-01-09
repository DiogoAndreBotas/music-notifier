package com.diogoandrebotas.musicnotifierapi.controller

import com.diogoandrebotas.musicnotifierapi.cron.ScheduledJobs
import com.diogoandrebotas.musicnotifierapi.service.ArtistService
import com.diogoandrebotas.musicnotifierapi.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class ArtistController(
    val artistService: ArtistService,
    val userService: UserService,
    val scheduledJobs: ScheduledJobs
) {

    @PostMapping("/subscribe/{artistName}")
    fun subscribeToArtist(@PathVariable artistName: String)
        = artistService.subscribeToArtist(artistName, userService.getLoggedInUser())

    @DeleteMapping("/unsubscribe/{artistName}")
    fun unsubscribeFromArtist(@PathVariable artistName: String)
        = artistService.unsubscribeUserFromArtist(artistName, userService.getLoggedInUser())

    // Exists temporarily to test CRON job using REST API calls
    @GetMapping("/releases")
    fun checkForNewArtistReleases() = scheduledJobs.checkForNewArtistReleases()

}
