package uiFunctionalTests;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataProviders.ExcelFileReader;
/**
 * 
 * @author Kunal Jor
 */
//@Listeners(utilities.TestListener.class)
public class Test_InfoForm extends BaseTest {
	Logger log = LogManager.getLogger(Test_InfoForm.class.getName());

	@Test(priority = 3, dataProvider = "InfoForm") // ,retryAnalyzer
	// =RetryAnalyzer.class)
	/**
	 * Verify User Information Form Submission
	 */
	public void verifyInfoFormSubmission(String Name, String Address, String PhoneNumber, String DOB, String Gender,
			String Qualification, String Rating) {
		log.info("In Test method verifyInfoFormSubmission");
		logger.info("Form Input values as received from data provider :");
		logger.info("Address - " + Address);
		logger.info("PhoneNumber - " + PhoneNumber);
		logger.info("DOB - " + DOB);
		logger.info("Gender - " + Gender);
		logger.info("Qualification - " + Qualification);
		logger.info("Rating - " + Rating);
		@SuppressWarnings("serial")
		HashMap<String, String> userInfo = new HashMap<String, String>() {
			{
				put("Name", Name);
				put("Address", Address);
				put("PhoneNumber", PhoneNumber);
				put("DOB", DOB);
				put("Gender", Gender);
				put("Qualification", Qualification);
				put("Rating", Rating);
			}
		};

		James.fillsInfoForm(userInfo, livi);
		boolean isFormSubmissionSuccessful = James.verifiesInfoFormSubmissionSuccessMessage(livi);
		sAssert.assertTrue(isFormSubmissionSuccessful, "Info Form \"Submitted Successfully\" message NOT visible");
		logger.info("Is Form Submission Successful ? " + isFormSubmissionSuccessful);
		if (sAssert != null)
			sAssert.assertAll();
	}

	@DataProvider(name = "InfoForm")
	public Object[][] infoFormData() throws Exception {
		Object[][] testObjArray = ExcelFileReader
				.getTableArray(System.getProperty("user.dir") + "//src//test//resources//TestData.xlsx", "User Info");
		return (testObjArray);

	}
}
