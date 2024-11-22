//Java Program to Illustrate EmailDetails Class
package chordax_dev_team.chordax_mailing.model;

//Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Annotations
@Data
@AllArgsConstructor
@Getter @Setter @NoArgsConstructor
//Class
public class Email {

	// Class data members

	private String recipient;
	
	private String msgBody;
	
	private String subject;
	
	private String attachment;

}