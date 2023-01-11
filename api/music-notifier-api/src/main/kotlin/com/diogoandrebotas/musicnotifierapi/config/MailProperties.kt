package com.diogoandrebotas.musicnotifierapi.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "api.mail")
data class MailProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val transportProtocol: String,
    val smtpAuth: String,
    val smtpStartTlsEnable: String,
    val debug: String
)
