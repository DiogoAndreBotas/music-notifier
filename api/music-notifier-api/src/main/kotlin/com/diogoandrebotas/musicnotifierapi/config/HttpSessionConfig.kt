package com.diogoandrebotas.musicnotifierapi.config

import org.springframework.context.annotation.Bean
import org.springframework.session.data.mongo.JacksonMongoSessionConverter
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession

@EnableMongoHttpSession
class HttpSessionConfig {
    @Bean
    fun mongoSessionConverter() = JacksonMongoSessionConverter()
}
