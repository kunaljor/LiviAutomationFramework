package uiFunctionalTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import dataProviders.ExcelFileReader;
import edu.emory.mathcs.backport.java.util.Collections;
import enums.WebViewButtonsAndLinks;


@Listeners(utilities.TestListener.class)
public class Test_WebView extends BaseTest{
	Logger log =LogManager.getLogger(Test_InfoForm.class.getName());
	//protected ExtentTest test;
	EnumSet<WebViewButtonsAndLinks> openTheseLinks=EnumSet.noneOf(WebViewButtonsAndLinks.class);
	ArrayList<String> links;
	//ExtentTest logger;
	
	@Test( priority = 6,dataProvider="links") // ,retryAnalyzer=RetryAnalyzer.class)
/**
* Test adding Best Sellers to Cart
*/
public void verifyButtonsAndLinksSelection(String linksToBeOpened) throws InterruptedException {
		//logger = extent.createTest("Buttons and Links on Web View Test <br />  OS:" + envDetails.get("os") + " <br /> Browser Name:"+ envDetails.get("browserName") + " <br /> Browser Version:" + envDetails.get("browserVersion"));
String[] linksSplit=linksToBeOpened.split(",");
logger.info("linksIoBeOpened list as received from data provider "+linksToBeOpened);
Arrays.asList(linksSplit).stream().forEach(link ->{
	String pLink=link.toUpperCase().replaceAll(" ", "_");
	log.info("Value of processed string Link is "+ pLink);
//links.add(pLink);
	openTheseLinks.add(WebViewButtonsAndLinks.valueOf(pLink));
}
);
log.info("Value of EnumSet to be Passed is "+ openTheseLinks);
boolean allLinksOpenedSuccessfully = livi.perform().openWebViewLinks(openTheseLinks);
sAssert.assertTrue(allLinksOpenedSuccessfully,"Some or All links out of  " + linksToBeOpened + "didn't open successfully. Some issue here");
if (sAssert != null) sAssert.assertAll();
}
	@DataProvider(name="links") 
	  public Object[][] linksToBeOpenedData() throws Exception {
	  Object[][] testObjArray = ExcelFileReader.getTableArray(System.getProperty("user.dir") +"//src//test//resources//TestData.xlsx", "Web View");
	  return (testObjArray);
	  
	  }
}
