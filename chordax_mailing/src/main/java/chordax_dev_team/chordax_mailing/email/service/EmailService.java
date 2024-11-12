//Java Program to Illustrate Creation Of
//Service Interface
package chordax_dev_team.chordax_mailing.email.service;

import org.thymeleaf.context.Context;

//Importing required classes
import chordax_dev_team.chordax_mailing.email.details.EmailDetails;
import jakarta.mail.MessagingException;

//Interface
public interface EmailService {

	// Method
	// To send a simple email
	String sendSimpleMail(EmailDetails details);

	// Method
	// To send an email with attachment
	String sendMailWithAttachment(EmailDetails details);

// Method
// To send an email with thymeleaf
	String sendEmailWithHtmlTemplate(EmailDetails details);
}