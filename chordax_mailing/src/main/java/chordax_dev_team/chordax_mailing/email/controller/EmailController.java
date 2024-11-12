// Java Program to Create Rest Controller that
// Defines various API for Sending Mail
package chordax_dev_team.chordax_mailing.email.controller;

//Importing required classes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import chordax_dev_team.chordax_mailing.email.details.EmailDetails;
import chordax_dev_team.chordax_mailing.email.service.EmailService;
import jakarta.mail.MessagingException;

//Annotation
@RestController
//Class
public class EmailController {

	@Autowired
	private EmailService emailService;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details) {
		
		String status = emailService.sendSimpleMail(details);

		return status;
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}

// 
	@PostMapping("/send-html-email")
	public String sendHtmlEmail(@RequestBody EmailDetails details) {

		String status = emailService.sendEmailWithHtmlTemplate(details);
		
		return status;
	}
}
