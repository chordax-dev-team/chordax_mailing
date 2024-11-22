// Java Program to Create Rest Controller that
// Defines various API for Sending Mail
package chordax_dev_team.chordax_mailing.controller;

//Importing required classes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import chordax_dev_team.chordax_mailing.model.Email;
import chordax_dev_team.chordax_mailing.service.EmailService;

//Annotation
@RestController
//Class
public class EmailController {

	@Autowired
	private EmailService emailService;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String sendMail(@RequestBody Email details) {
		
		String status = emailService.sendSimpleMail(details);

		return status;
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody Email details) {
		
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}

	// Sending email as formatted html 
	@PostMapping("/sendHtmlEmail")
	public String sendHtmlEmail(@RequestBody Email details) {

		String status = emailService.sendEmailWithHtmlTemplate(details);
		
		return status;
	}
}
