package uiFunctionalTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataProviders.ExcelFileReader;
import enums.Apostle;

//@Listeners(utilities.TestListener.class)
public class Test_Apostles extends BaseTest {
	//private Logger log = LogManager.getLogger(Test_Apostles.class.getName());

	@Test(priority = 2, dataProvider = "apostles") // ,retryAnalyzer=RetryAnalyzer.class)
	/**
	 * Verify Apostle Links
	 */
	public void verifyApostleSelection(String apostle) {
		logger.info("Apostle passed by data provider " + apostle);
		String pApostle = apostle.toUpperCase().replaceAll(" ", "_");
//log.info("Value of processed string Apostle is "+ pApostle);
		boolean apostleLinkClicked = James.selectsApostle(Apostle.valueOf(pApostle), livi);
		logger.info("The value of apostleLinkClicked is " + apostleLinkClicked);
		sAssert.assertEquals(apostleLinkClicked, true,
				"There is no Link associated with Apostle " + Apostle.valueOf(pApostle) + ". Some issue here");
		if (sAssert != null)
			sAssert.assertAll();
	}

	@DataProvider(name = "apostles")
	public Object[][] apostleData() throws Exception {
		Object[][] testObjArray = ExcelFileReader
				.getTableArray(System.getProperty("user.dir") + "//src//test//resources//TestData.xlsx", "Carousel");
		return (testObjArray);

	}
}
