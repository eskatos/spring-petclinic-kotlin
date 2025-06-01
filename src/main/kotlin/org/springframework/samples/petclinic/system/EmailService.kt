package org.springframework.samples.petclinic.system

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.samples.petclinic.owner.Owner
import org.springframework.samples.petclinic.owner.Pet
import org.springframework.samples.petclinic.visit.Visit
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

/**
 * Service for sending emails.
 *
 * @author Your Name
 */
@Service
class EmailService(private val mailSender: JavaMailSender) {

    /**
     * Send a notification email to a pet owner about a new visit.
     *
     * @param owner The pet owner
     * @param pet The pet
     * @param visit The scheduled visit
     */
    fun sendVisitNotification(owner: Owner, pet: Pet, visit: Visit) {
        if (owner.email.isBlank()) {
            return // Skip if no email is provided
        }

        val message = SimpleMailMessage()
        message.setTo(owner.email)
        message.setSubject("New Visit Scheduled for ${pet.name}")

        val dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
        val formattedDate = visit.date.format(dateFormatter)

        val emailText = """
            Dear ${owner.firstName} ${owner.lastName},
            
            A new visit has been scheduled for your pet ${pet.name} on $formattedDate.
            
            Visit details:
            - Date: $formattedDate
            - Description: ${visit.description}
            
            If you need to reschedule or have any questions, please contact us.
            
            Thank you,
            Pet Clinic Team
        """.trimIndent()

        message.setText(emailText)

        mailSender.send(message)
    }
}
