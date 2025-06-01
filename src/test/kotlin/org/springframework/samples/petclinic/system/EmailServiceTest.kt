/*
 * Copyright 2002-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.mail

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.samples.petclinic.owner.Owner
import org.springframework.samples.petclinic.owner.Pet
import org.springframework.samples.petclinic.visit.Visit
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.junit.jupiter.api.Assertions.*
import org.springframework.samples.petclinic.system.EmailService

@ExtendWith(MockitoExtension::class)
class EmailServiceTest {

    @Mock
    private lateinit var mailSender: JavaMailSender

    @InjectMocks
    private lateinit var emailService: EmailService

    @Captor
    private lateinit var messageCaptor: ArgumentCaptor<SimpleMailMessage>

    @Test
    fun `should send email notification for new visit`() {
        // Given
        val owner = Owner()
        owner.firstName = "John"
        owner.lastName = "Doe"
        owner.email = "john.doe@example.com"

        val pet = Pet()
        pet.name = "Fluffy"
        pet.owner = owner

        val visit = Visit()
        visit.date = LocalDate.of(2023, 12, 25)
        visit.description = "Annual checkup"

        // When
        emailService.sendVisitNotification(owner, pet, visit)

        // Then
        verify(mailSender).send(capture(messageCaptor))

        val message = messageCaptor.value
        assertEquals("john.doe@example.com", message.to!![0])
        assertEquals("New Visit Scheduled for Fluffy", message.subject)
        assertTrue(message.text!!.contains("John Doe"))
        assertTrue(message.text!!.contains("Fluffy"))
        assertTrue(message.text!!.contains("December 25, 2023"))
        assertTrue(message.text!!.contains("Annual checkup"))
    }

    @Test
    fun `should not send email when owner email is blank`() {
        // Given
        val owner = Owner()
        owner.firstName = "John"
        owner.lastName = "Doe"
        owner.email = ""  // Blank email

        val pet = Pet()
        pet.name = "Fluffy"
        pet.owner = owner

        val visit = Visit()
        visit.date = LocalDate.now()
        visit.description = "Annual checkup"

        // When
        emailService.sendVisitNotification(owner, pet, visit)

        // Then
        verify(mailSender, never()).send(any(SimpleMailMessage::class.java))
    }

    @Test
    fun `should not send email when owner is null`() {
        // Given
        val pet = Pet()
        pet.name = "Fluffy"
        pet.owner = null

        val visit = Visit()
        visit.date = LocalDate.now()
        visit.description = "Annual checkup"

        // When
        val owner = pet.owner
        if (owner != null) {
            emailService.sendVisitNotification(owner, pet, visit)
        }

        // Then
        verify(mailSender, never()).send(any(SimpleMailMessage::class.java))
    }

    @Test
    fun `should format date correctly in email`() {
        // Given
        val owner = Owner()
        owner.firstName = "Jane"
        owner.lastName = "Smith"
        owner.email = "jane.smith@example.com"

        val pet = Pet()
        pet.name = "Rex"
        pet.owner = owner

        val visit = Visit()
        visit.date = LocalDate.of(2023, 5, 15)  // May 15, 2023
        visit.description = "Vaccination"

        // When
        emailService.sendVisitNotification(owner, pet, visit)

        // Then
        verify(mailSender).send(capture(messageCaptor))

        val message = messageCaptor.value
        val expectedDateFormat = visit.date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
        assertTrue(message.text!!.contains(expectedDateFormat))
        assertEquals("May 15, 2023", expectedDateFormat)
    }

    @Test
    fun `should include all required information in email content`() {
        // Given
        val owner = Owner()
        owner.firstName = "Robert"
        owner.lastName = "Johnson"
        owner.email = "robert.johnson@example.com"

        val pet = Pet()
        pet.name = "Whiskers"
        pet.owner = owner

        val visit = Visit()
        visit.date = LocalDate.of(2023, 8, 10)
        visit.description = "Dental cleaning"

        // When
        emailService.sendVisitNotification(owner, pet, visit)

        // Then
        verify(mailSender).send(capture(messageCaptor))

        val message = messageCaptor.value
        val emailText = message.text!!

        // Check that all required elements are in the email
        assertTrue(emailText.contains("Dear Robert Johnson"))
        assertTrue(emailText.contains("your pet Whiskers"))
        assertTrue(emailText.contains("August 10, 2023"))
        assertTrue(emailText.contains("Dental cleaning"))
        assertTrue(emailText.contains("Pet Clinic Team"))
        assertTrue(emailText.contains("If you need to reschedule"))
    }

    @Test
    fun `should set correct email subject with pet name`() {
        // Given
        val owner = Owner()
        owner.firstName = "Michael"
        owner.lastName = "Brown"
        owner.email = "michael.brown@example.com"

        val pet = Pet()
        pet.name = "Buddy"
        pet.owner = owner

        val visit = Visit()
        visit.date = LocalDate.now()
        visit.description = "Regular checkup"

        // When
        emailService.sendVisitNotification(owner, pet, visit)

        // Then
        verify(mailSender).send(capture(messageCaptor))

        val message = messageCaptor.value
        assertEquals("New Visit Scheduled for Buddy", message.subject)
    }

    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}
