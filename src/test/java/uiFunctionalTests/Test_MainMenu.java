package uiFunctionalTests;

import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import dataProviders.ExcelFileReader;
import managers.FileManager;
import pageObjects.Livi;
import utilities.ExtentTestManager;
import utilities.RetryAnalyzer;
/** 
* 
* @author Kunal Jor 
* 
* 
*/
@Listeners(utilities.TestListener.class)
public class Test_MainMenu extends BaseTest {
	public Logger log =LogManager.getLogger(Test_MainMenu.class.getName());
	//ExtentReports extentTest;
	//ExtentTest logger;
	
/**
* Test adding Best Sellers to Cart
*/
@Test(priority=2,dependsOnGroups= {"Sanity"}) // ,retryAnalyzer=RetryAnalyzer.class)
public void verifyMainMenu() {
	//logger = extent.createTest("Main Menu Test <br />  OS:" + envDetails.get("os") + " <br /> Browser Name:"+ envDetails.get("browserName") + " <br /> Browser Version:" + envDetails.get("browserVersion"));
livi.perform().openMainMenu();
logger.info("In Main Menu Test");
sAssert.assertEquals(livi.perform().getLastReply(), "Main Menu", "The expected response wan't returned to the request \"Main Menu\"");
if (sAssert != null) sAssert.assertAll();
}

}
