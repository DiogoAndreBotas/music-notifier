package com.diogoandrebotas.musicnotifierapi.controller

import com.diogoandrebotas.musicnotifierapi.service.ArtistService
import com.diogoandrebotas.musicnotifierapi.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ArtistController(val artistService: ArtistService, val userService: UserService) {

    @PostMapping("/subscribe/artist/{artistName}")
    fun subscribeToArtist(@PathVariable artistName: String)
        = artistService.upsertArtist(artistName, userService.getLoggedInUser())

    @DeleteMapping("/unsubscribe/artist/{artistName}")
    fun unsubscribeFromArtist(@PathVariable artistName: String)
        = artistService.unsubscribeUserFromArtist(artistName, userService.getLoggedInUser())

}
