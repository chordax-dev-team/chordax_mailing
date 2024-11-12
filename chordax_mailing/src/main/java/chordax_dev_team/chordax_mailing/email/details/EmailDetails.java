//Java Program to Illustrate EmailDetails Class
package chordax_dev_team.chordax_mailing.email.details;

//Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
//Class
public class EmailDetails {

	// Class data members
	@Getter @Setter 
	private String recipient;
	
	@Getter @Setter 
	private String msgBody;
	
	@Getter @Setter 
	private String subject;
	
	@Getter @Setter 
	private String attachment;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}