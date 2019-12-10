package utilities;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import managers.ExtentTestManager;
/**
 * 
 * @author Kunal Jor
 */
public class TestListener implements  ITestListener,ISuiteListener{
	public static Logger log =LogManager.getLogger(ITestListener.class.getName());
	ITestContext context ;
	public static String reportFileName = "Test-Automation-Report"+".html";
	public static String fileSeperator = System.getProperty("file.separator");
	public static String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "TestReport";
	public static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
	ExtentTestManager extentTest=new ExtentTestManager();
	ExtentTest logger;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	ThreadLocal<WebDriver> driver ;
	//String fileLocation = getReportPath(reportFileLocation);
	
	public void onStart(ITestContext context) {
		log.info("*** Started Test ***"+ context.getName());
	}

	public void onFinish(ITestContext context) {
		log.info("*** Ending Test ***"+context.getName());
	}

	public void onTestStart(ITestResult result) {
		log.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		  context =result.getTestContext(); 
		  driver = (ThreadLocal<WebDriver>) context.getAttribute("driver");
		 Capabilities caps = ((RemoteWebDriver)driver.get()).getCapabilities();
		  String browserName = caps.getBrowserName(); 
		  String browserVersion = caps.getVersion(); 
		  String os =System.getProperty("os.name").toLowerCase(); 
		  log.info(" OS = " + os +", Browser = " + browserName + " "+ browserVersion);
		  logger=extentTest.startTest(result.getMethod().getMethodName() + "TEST ");
		  logger.log(Status.INFO, "TESTNG Test Started");
		 
	}

	public void onTestSuccess(ITestResult result) {
		log.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		logger.log(Status.PASS, "TESTNG Test Passed");
		extentTest.endTest();
	}

	public void onTestFailure(ITestResult result) {
		log.error("*** Executed " + result.getMethod().getMethodName() + " test failed...");
		context = result.getTestContext();
		driver=(ThreadLocal<WebDriver>) context.getAttribute("driver");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String timeStamp= timestamp.toString().replaceAll("[^\\d\\\\sA-Za-z]", ""); // get timestamp
		log.info("The value of timestamp converted to string is"+timeStamp);
		String testMethodName = result.getMethod().getMethodName();
		String screenShotName = testMethodName +timeStamp+".png";
		log.info("In Test Failure method, the value of driver here is "+ driver);
		TakesScreenshot scrShot =(TakesScreenshot) driver.get();
    	File SrcFile =(File) scrShot.getScreenshotAs(OutputType.FILE);
    	 File DestFile=new File(System.getProperty("user.dir")+"\\TestReport\\"+screenShotName);
    	 try {
    	 FileUtils.copyFile(SrcFile, DestFile);
    	 }
    	 catch (IOException e) {
				e.printStackTrace();
			}
    	 extentTest.endTest();
	}

	public void onTestSkipped(ITestResult result) {
		log.error("*** Test " + result.getMethod().getMethodName() + " skipped...");
		logger.log(Status.INFO, "TESTNG Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.error("*** Test failed but within percentage % " + result.getMethod().getMethodName());
		logger.log(Status.INFO, "TESTNG Test Failed bu within success percentage");
	}

}
