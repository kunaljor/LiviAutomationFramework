package uiFunctionalTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import dataProviders.ExcelFileReader;
import enums.Country;
/**
 * 
 * @author Kunal Jor
 */

//@Listeners(utilities.TestListener.class)
public class Test_Country extends BaseTest {
	Logger log = LogManager.getLogger(Test_Country.class.getName());

	@Test(priority = 5, dataProvider = "country") // ,retryAnalyzer=RetryAnalyzer.class)
	/**
	 * Verify Country Selection
	 */
	public void verifyCountrySelection(String country) {
		logger.info("Value of country received from data provider " + country);
		String expectedCountry = country.replaceAll(" ", "_");
log.info("In Test Country,after setting expectedCountry "+expectedCountry  );
		James.selectsACountry(Country.valueOf(expectedCountry), livi);
		String actualCountry = James.fetchesLastReply(livi).replaceAll(" ", "_").replaceAll("\"", "");
		sAssert.assertEquals(actualCountry, expectedCountry,
				"Selected  \"Country\" " + Country.India + " was not returned by Livi.Some issue here");
		if (sAssert != null)
			sAssert.assertAll();
	}

	@DataProvider(name = "country")
	public Object[][] countryData() throws Exception {
		Object[][] testObjArray = ExcelFileReader
				.getTableArray(System.getProperty("user.dir") + "//src//test//resources//TestData.xlsx", "Country");
		return (testObjArray);

	}

}
