package com.diogoandrebotas.musicnotifierapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MusicNotifierApiApplication

fun main(args: Array<String>) {
    runApplication<MusicNotifierApiApplication>(*args)
}
