package uiFunctionalTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import dataProviders.ExcelFileReader;
import enums.Country;

@Listeners(utilities.TestListener.class)
public class Test_Country extends BaseTest{
	Logger log =LogManager.getLogger(Test_InfoForm.class.getName());
	//protected ExtentTest test;
	//ExtentTest logger;
	@Test( priority = 4,dependsOnGroups= {"Sanity"},dataProvider="country") // ,retryAnalyzer=RetryAnalyzer.class)
/**
* Test adding Best Sellers to Cart
*/
public void verifyCountrySelection(String country) throws InterruptedException {
		//logger = extent.createTest("Apostle Selection Test <br />  OS:" + envDetails.get("os") + " <br /> Browser Name:"+ envDetails.get("browserName") + " <br /> Browser Version:" + envDetails.get("browserVersion"));
logger.info("Value of country received from data provider "+country);
String expectedCountry=country.replaceAll(" ","_");
		livi.perform().selectYourCountry(Country.valueOf(expectedCountry));
		String actualCountry=livi.perform().getLastReply().replaceAll(" ","_").replaceAll("\"", "");
sAssert.assertEquals(actualCountry, expectedCountry, "Selected  \"Country\" "+Country.India +" was not returned by Livi.Some issue here");
if (sAssert != null) sAssert.assertAll();
	}
	@DataProvider(name="country") 
	  public Object[][] countryData() throws Exception {
	  Object[][] testObjArray = ExcelFileReader.getTableArray(System.getProperty("user.dir") +"//src//test//resources//TestData.xlsx", "Country");
	  return (testObjArray);
	  
	  }
	
}
