package com.diogoandrebotas.musicnotifierapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class EmailConfig {
    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587
        mailSender.username = "music.notifier.service@gmail.com"
        mailSender.password = "tsrndtspsbfnurpj"

        val properties = mailSender.javaMailProperties
        properties["mail.transport.protocol"] = "smtp"
        properties["mail.smtp.auth"] = "true"
        properties["mail.smtp.starttls.enable"] = "true"
        properties["mail.debug"] = "true"

        return mailSender
    }

}
