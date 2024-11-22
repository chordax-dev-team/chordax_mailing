//Java Program to Illustrate Creation Of
//Service Interface
package chordax_dev_team.chordax_mailing.service;

import org.springframework.stereotype.Service;

import chordax_dev_team.chordax_mailing.model.Email;

//Interface
@Service
public interface EmailService {

	// Method
	// To send a simple email
	String sendSimpleMail(Email details);

	// Method
	// To send an email with attachment
	String sendMailWithAttachment(Email details);

// Method
// To send an email with thymeleaf
	String sendEmailWithHtmlTemplate(Email details);
}