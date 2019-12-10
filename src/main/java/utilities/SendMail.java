package utilities;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class SendMail {

	private static String reportFileName = "Test-Automation-Report" + ".html";
	private static String fileSeperator = System.getProperty("file.separator");
	private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
	private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

	public static void createMailPayloadAndSend() throws EmailException, MalformedURLException {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(reportFileLocation);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Description- Livi Automation Test Report");
		attachment.setName("Kunal");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.mail.yahoo.com");
		email.addTo("jayanth.babu@avaamo.com", "Jayanth Babu");
		email.setFrom("kunaljor@yahoo.co.in", "Kunal Jor");
		email.setSubject("Livi Automation Test Report");
		email.setMsg("Here is the picture you wanted");
		// email.setSmtpPort(465);
		email.setBoolHasAttachments(true);

		// add the attachment
		email.attach(attachment);

		// send the email
		email.send();
	}

}
