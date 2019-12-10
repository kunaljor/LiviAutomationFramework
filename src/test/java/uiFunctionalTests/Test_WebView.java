package uiFunctionalTests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import dataProviders.ExcelFileReader;
import enums.WebViewButtonsAndLinks;
/**
 * 
 * @author Kunal Jor
 */
//@Listeners(utilities.TestListener.class)
public class Test_WebView extends BaseTest {
	Logger log = LogManager.getLogger(Test_WebView.class.getName());
	EnumSet<WebViewButtonsAndLinks> openTheseLinks = EnumSet.noneOf(WebViewButtonsAndLinks.class);
	ArrayList<String> links;

	@Test(priority = 6, dataProvider = "links") // ,retryAnalyzer=RetryAnalyzer.class)
	/**
	 * Verify whether the combination of links to be opened is working properly
	 */
	public void verifyButtonsAndLinksSelection(String linksToBeOpened) {
		String[] linksSplit = linksToBeOpened.split(",");
		logger.info("linksToBeOpened list as received from data provider " + linksToBeOpened);
		Arrays.asList(linksSplit).stream().forEach(link -> {
			String pLink = link.toUpperCase().replaceAll(" ", "_");
			// log.info("Value of processed string Link is " + pLink);
			openTheseLinks.add(WebViewButtonsAndLinks.valueOf(pLink));
		});
		// log.info("Value of EnumSet to be Passed is " + openTheseLinks);
		HashSet<String> linksDidntOpenProperly = James.opensWebViewLinks(openTheseLinks, livi);
		sAssert.assertTrue(linksDidntOpenProperly.isEmpty(),
				"The following links  " + linksDidntOpenProperly + "didn't open successfully. Some issue here");
		if (linksDidntOpenProperly.isEmpty()) {
			logger.log(Status.PASS, "All links opened successfully");
		} else {
			logger.log(Status.FAIL,
					"The following links  " + linksDidntOpenProperly + "didn't open successfully. Some issue here");
		}
		if (sAssert != null)
			sAssert.assertAll();
	}

	@DataProvider(name = "links")
	public Object[][] linksToBeOpenedData() throws Exception {
		Object[][] testObjArray = ExcelFileReader
				.getTableArray(System.getProperty("user.dir") + "//src//test//resources//TestData.xlsx", "Web View");
		return (testObjArray);

	}
}
