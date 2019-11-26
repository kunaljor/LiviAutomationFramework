package uiFunctionalTests;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import dataProviders.ExcelFileReader;


@Listeners(utilities.TestListener.class)
public class Test_InfoForm extends BaseTest{
	Logger log =LogManager.getLogger(Test_InfoForm.class.getName());
	//protected ExtentTest test;
	 String newLine = System.getProperty("line.separator");
	 //ExtentTest logger;
	
	
	@Test(priority = 3,dependsOnGroups= {"Sanity"},dataProvider="InfoForm") // ,retryAnalyzer
	// =RetryAnalyzer.class)
/**
* Test adding Best Sellers to Cart
*/
public void verifyInfoFormSubmission(String Name,String Address,String PhoneNumber,String DOB,String Gender,String Qualification,String	Rating){
	//logger = extent.createTest("Info Form Test <br />  OS:" + envDetails.get("os") + " <br /> Browser Name:"+ envDetails.get("browserName") + " <br /> Browser Version:" + envDetails.get("browserVersion"));
logger.info("Form Input values as received from data provider :");	
logger.info("Address - "+Address);	
logger.info("PhoneNumber - "+PhoneNumber);		
logger.info("DOB - "+DOB);	
logger.info("Gender - "+Gender);	
logger.info("Qualification - "+Qualification);		
logger.info("Rating - "+Rating);	
		@SuppressWarnings("serial")
	HashMap<String, String> userInfo = new HashMap<String,String>() {
		{
		put("Name", Name);put("Address", Address);put("PhoneNumber", PhoneNumber);put("DOB", DOB);put("Gender", Gender);put("Qualification", Qualification);put("Rating", Rating);
		}
	};

livi.perform().fillInfoForm(userInfo);
boolean isFormSubmissionSuccessful=livi.perform().verifyInfoFormSubmissionSuccessMessage();
sAssert.assertTrue(isFormSubmissionSuccessful,"Info Form \"Submitted Successfully\" message NOT visible");
logger.info("Is Form Submission Successful ? "+ isFormSubmissionSuccessful);
if (sAssert != null) sAssert.assertAll();
}
	
	@DataProvider(name="InfoForm") 
	  public Object[][] infoFormData() throws Exception {
	  Object[][] testObjArray = ExcelFileReader.getTableArray(System.getProperty("user.dir") +"//src//test//resources//TestData.xlsx", "User Info");
	  return (testObjArray);
	  
	  }
}
