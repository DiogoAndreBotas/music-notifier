package com.diogoandrebotas.musicnotifierapi.repository

import com.diogoandrebotas.musicnotifierapi.model.Artist
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ArtistRepository : MongoRepository<Artist, String> {

    fun findArtistByName(name: String): Optional<Artist>

}
