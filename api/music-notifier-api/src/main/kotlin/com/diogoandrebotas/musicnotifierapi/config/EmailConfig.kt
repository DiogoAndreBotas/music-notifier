package com.diogoandrebotas.musicnotifierapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class EmailConfig (
    val mailProperties: MailProperties
) {

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailProperties.host
        mailSender.port = mailProperties.port
        mailSender.username = mailProperties.username
        mailSender.password = mailProperties.password

        val properties = mailSender.javaMailProperties
        properties["mail.transport.protocol"] = mailProperties.transportProtocol
        properties["mail.smtp.auth"] = mailProperties.smtpAuth
        properties["mail.smtp.starttls.enable"] = mailProperties.smtpStartTlsEnable
        properties["mail.debug"] = mailProperties.debug

        return mailSender
    }

}
