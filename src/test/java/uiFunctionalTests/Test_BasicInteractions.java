package uiFunctionalTests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import dataProviders.ExcelFileReader;
import utilities.ExtentReportManager;
import utilities.ExtentTestManager;

/**
 * 
 * @author Kunal Jor
 * 
 * 
 */
@Listeners(utilities.TestListener.class)
public class Test_BasicInteractions extends BaseTest {
	Logger log = LogManager.getLogger(Test_BasicInteractions.class.getName());
	// static ExtentReports report = ExtentReportManager.createInstance();
	// public static ExtentTest logger;
	// ExtentTest test;
	//ExtentTest logger;
	int interactionCount = 0;
	HashMap<String, List<String>> lastInteraction;
	// ExtentReports extentTest;
	// ExtentHtmlReporter htmlReporter;
	// String fileLocation;
	Method method;
	List<String> liviReplies=null;
	/*
	 * @BeforeMethod public void initializeExtent() { //test =
	 * extent.createTest("Livi Sanity Check <br />  OS:" + envDetails.get("os") +
	 * " <br /> Browser Name:"+ envDetails.get("browserName") +
	 * " <br /> Browser Version:" + envDetails.get("browserVersion"));
	 * logger=extent.createTest("Sanity Test");
	 * 
	 * }
	 */

	@Test(priority = 1, groups = { "Sanity" }, dataProvider = "messagesForLivi") // ,retryAnalyzer =RetryAnalyzer.class)
	/**
	 * Test for verifying that basic features of Livi are working as usual
	 */
	public void verifyLiviHealth(String message) {
		livi.perform().interactWithLivi(message);

		lastInteraction = livi.perform().getLastInteraction();
		lastInteraction.entrySet().forEach(interaction -> {
			logger.info("User message to Livi - " + interaction.getKey());
			liviReplies=interaction.getValue();
			logger.info("Livi's reply to message - ");
					interaction.getValue().stream().forEach(reply -> {
				logger.info(reply);
			});
			
			log.info("User message to Livi - " + interaction.getKey());
			log.info("Livi's reply to message - " + interaction.getValue());
		});
		sAssert.assertFalse(java.util.Objects.equals(liviReplies, null) ,"No reply by Livi to user message. Some issue here");
		if (sAssert != null) sAssert.assertAll();
	}


	@DataProvider(name = "messagesForLivi")
	public Object[][] messagesForLivi() throws Exception {
		Object[][] testObjArray = ExcelFileReader
				.getTableArray(System.getProperty("user.dir") + "//src//test//resources//TestData.xlsx", "Messages");
		return (testObjArray);

	}

}
