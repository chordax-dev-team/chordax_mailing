//Java Program to Illustrate Creation Of
//Service Interface
package chordax_dev_team.chordax_mailing.service;

import chordax_dev_team.chordax_mailing.model.PDFDto;
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.util.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	private final DiscoveryClient discoveryClient;
	private RestTemplate restTemplate = new RestTemplate();

	private final JavaMailSender mailSender;

	public EmailService(DiscoveryClient discoveryClient, JavaMailSender mailSender) {
		this.discoveryClient = discoveryClient;
		this.mailSender = mailSender;
	}

	public void sendEmail(String to, String subject, String body) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("info@chordax.ie"); // Can be anything
		message.setTo("piotr.a.bar@gmail.com");
		message.setSubject("Mailtrap Test");
		message.setText("Hello! This is a test email from Spring Boot using Mailtrap.");
		mailSender.send(message);
	}


	public void sendHtmlEmailWithAttachment(Long userId, Long songId) {

		// calling controller of chordax_pdf_creation
		List<ServiceInstance> instances = discoveryClient.getInstances("chordax_pdf_creation");

		if (instances.isEmpty()) {
			logger.error("No instances found for service: chordax_pdf_creation");
			throw new IllegalStateException("PDF creation service unavailable");
		}

		String serviceURI = String.format("%s/api/v1/pdfs/%d/%d", instances.get(0).getUri(), userId, songId);
		logger.info("Fetching song from URI: {}", serviceURI);

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom("info@chordax.com"); // Replace with your sender
			helper.setTo("piotr.a.bar@gmail.com");
			// Load image from classpath
			Resource image = new ClassPathResource("static/img/chordax.png");
			helper.addInline("chordaxLogo", image);

			PDFDto attachment = restTemplate.getForObject(serviceURI, PDFDto.class);
			if (attachment.data() == null) {
				throw new IllegalStateException("Failed to retrieve PDF attachment");
			}

			String contentType = "application/pdf";
			DataSource dataSource = new ByteArrayDataSource(attachment.data(), contentType);
			String originalTitle = attachment.title();
			// For attachment filename (preserves characters with diacritics safely)
			String encodedTitle = MimeUtility.encodeText(attachment.title(), "UTF-8", null);
			helper.addAttachment(encodedTitle + ".pdf", dataSource);

			helper.setSubject("Song \"" + encodedTitle + "\" from chordax");
			// Embed image using CID
			// For email body (use plain UTF-8 string)
			String htmlWithImage = String.format(
					"<html style='font-family: Arial, sans-serif;'>" +
							"<h3>Hello!</h3>" +
							"<p>Please, find attached a song <strong>\"%s\"</strong> from us, created as you wanted.</p>" +
							"<hr>" +
							"<div style='width:fit-content; text-align:center;'>" +
								"<p style='margin-bottom:10px;'>" +
									"<small>Being there for your disposal</small>" +
								"</p>" +
								"<img src='cid:chordaxLogo' width='123' height='39'>" +
							"</div>" +
					"</html>",
					attachment.title()
			);
			helper.setText(htmlWithImage, true); // true = HTML
			logger.info("Sending email to userId={}, songId={}, recipient={}", userId, songId, "piotr.a.bar@gmail.com");

			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
		}
	}
}