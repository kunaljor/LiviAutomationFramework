package uiFunctionalTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import dataProviders.ExcelFileReader;
import enums.Apostle;


@Listeners(utilities.TestListener.class)
public class Test_Apostles extends BaseTest{
	Logger log =LogManager.getLogger(Test_InfoForm.class.getName());
	//protected ExtentTest test;
	//ExtentTest logger;

@Test( priority = 5,dataProvider="apostles") // ,retryAnalyzer=RetryAnalyzer.class)
/**
* Test adding Best Sellers to Cart
*/
public void verifyApostleSelection(String apostle) throws InterruptedException {
//logger = extent.createTest("Apostle Selection Test <br />  OS:" + envDetails.get("os") + " <br /> Browser Name:"+ envDetails.get("browserName") + " <br /> Browser Version:" + envDetails.get("browserVersion"));
	logger.info("Apostle passed by data provider "+apostle);
	String pApostle=apostle.toUpperCase().replaceAll(" ", "_");
log.info("Value of processed string Apostle is "+ pApostle);
boolean apostleLinkClicked = livi.perform().selectApostle(Apostle.valueOf(pApostle));
log.info("The value of apostleLinkClicked is "+apostleLinkClicked);
logger.info("The value of apostleLinkClicked is "+apostleLinkClicked);
sAssert.assertTrue(apostleLinkClicked,"There is no Link associated  with Apostle " + Apostle.valueOf(pApostle) + ". Some issue here");
if (sAssert != null) sAssert.assertAll();
}



@DataProvider(name="apostles") 
public Object[][] apostleData() throws Exception {
Object[][] testObjArray = ExcelFileReader.getTableArray(System.getProperty("user.dir") +"//src//test//resources//TestData.xlsx", "Carousel");
return (testObjArray);

}
}
