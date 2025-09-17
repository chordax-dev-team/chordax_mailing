// Java Program to Create Rest Controller that
// Defines various API for Sending Mail
package chordax_dev_team.chordax_mailing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import chordax_dev_team.chordax_mailing.service.EmailService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/send-email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@RequestParam String recipient,
											@RequestParam String subject,
											@RequestParam String htmlBody) {
		try {
			emailService.sendEmail(recipient, subject, htmlBody);
			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to send email: " + e.getMessage());
		}
	}

	@PostMapping("/html")
	public ResponseEntity<String> sendHtmlEmail(
			@RequestParam String recipient,
			@RequestParam String subject,
			@RequestParam String htmlBody,
			@RequestParam(required = false) MultipartFile attachment) {

		try {
			emailService.sendHtmlEmailWithAttachment(recipient, subject, htmlBody, attachment);
			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed to send email: " + e.getMessage());
		}
	}
}