package com.diogoandrebotas.musicnotifierapi.service

import com.diogoandrebotas.musicnotifierapi.model.dto.ArtistRelease
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailSenderService(
    private val mailSender: JavaMailSender
) {

    fun sendNewReleaseEmail(emailAddress: String, releases: List<ArtistRelease>) {
        val email = buildEmail(emailAddress, releases)
        mailSender.send(email)
    }

    private fun buildEmail(emailAddress: String, releases: List<ArtistRelease>): SimpleMailMessage {
        val email = SimpleMailMessage()

        email.setTo(emailAddress)
        email.subject = "New releases from your subscribed artists"
        email.text = releases.joinToString(
            "\n",
            "Today's new releases include:\n"
        ) { it.toEmailContent() }

        return email
    }

}
