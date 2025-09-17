//Java Program to Illustrate Creation Of
//Service Interface
package chordax_dev_team.chordax_mailing.service;

import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String to, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("info@chordax.ie"); // Can be anything
		message.setTo("piotr.a.bar@gmail.com");
		message.setSubject("Mailtrap Test");
		message.setText("Hello! This is a test email from Spring Boot using Mailtrap.");
		mailSender.send(message);
	}


	public void sendHtmlEmailWithAttachment(String to,
											String subject,
											String htmlBody,
											MultipartFile attachment) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			boolean hasAttachment = attachment != null && !attachment.isEmpty();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, hasAttachment, "UTF-8");

			helper.setFrom("info@chordax.com"); // Replace with your sender
			helper.setTo(to);
			helper.setSubject(subject);
			// Embed image using CID
			String htmlWithImage = htmlBody + "<br><img src='cid:chordaxLogo' with='123' height='39'>";
			helper.setText(htmlWithImage, true); // true = HTML
			// Load image from classpath
			Resource image = new ClassPathResource("static/img/chordax.png");
			helper.addInline("chordaxLogo", image);

			if (hasAttachment) {
				byte[] bytes = attachment.getBytes();
				String contentType = attachment.getContentType() != null
						? attachment.getContentType()
						: "application/octet-stream";
				DataSource dataSource = new ByteArrayDataSource(bytes, contentType);
				helper.addAttachment(attachment.getOriginalFilename(), dataSource);
			}

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
		}
	}
}