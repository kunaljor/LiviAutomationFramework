package uiFunctionalTests;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import actors.Actor;
import managers.DriverManager;
import pageActions.LiviInteractions;
import pageObjects.Livi;

/**
 * 
 * @author Kunal Jor
 */
public class BaseTest {
	//public Logger log = LogManager.getLogger(BaseTest.class.getName());
	ExtentTest logger; 
	protected static ThreadLocal<WebDriver> driver;
	private static DriverManager driverManager;
	protected SoftAssert sAssert = new SoftAssert();
	HashMap<String, String> envDetails;
	public Livi livi;
	protected LiviInteractions James;
	private boolean firstRun;
	private static Logger log;
	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports extent;
	protected static ExtentTest test;
	protected static ExtentTest childTest;
	protected static String reportFileName = "Test-Automation-Report" + ".html";
	protected static String fileSeperator = System.getProperty("file.separator");
	protected static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
	protected static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;
	protected Actor actorJames = new Actor();

	public ITestContext context;

	@BeforeTest
	/**
	 * Instantiate Driver Manager, Driver and Page Object Manager and Page Objects
	 */
	public void setUp(ITestContext iTestContext) {
		System.out.println("Before Logger");
		log = LogManager.getLogger(BaseTest.class.getName());
		System.out.println("After Logger");
		driverManager = DriverManager.getInstance();
		if (driver == null)
			driver = driverManager.getDriverAndOpenAUT();
		log.info("The value of driver set in Before Test is " + driver);
		firstRun = true;

		this.context = setContext(iTestContext, driver);

		String fileLocation = getReportPath(reportFileLocation);
		htmlReporter = new ExtentHtmlReporter(fileLocation);
		htmlReporter.config().setCSS(".r-img { width: 50%; }");
		extent = new ExtentReports();
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");
		extent.attachReporter(htmlReporter);

		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(reportFileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(reportFileName);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	}

	@BeforeClass
	public synchronized void essentialsForTest() {
		log.info("In BEFORE CLASS");
		livi = new Livi(driver);
		James = actorJames.wakesUpLivi(driver);
		if (firstRun) {
			log.info("In FIRST RUN");
			String classname = getClass().getSimpleName();
			log.info("Current Thread " + Thread.currentThread().getId() + " is associated with " + classname);
			James.opensLivi(livi);
			firstRun = false;
		}
		envDetails = getBrowserAndOS();

	}

	@BeforeMethod
	public void initializeExtent(Method method) {
		String classname = getClass().getSimpleName();
		String methodName = method.getName();
		logger = extent.createTest("Test : " + classname + " <br /> Sub-Test: " + methodName + " <br />  OS:"
				+ envDetails.get("os") + " <br /> Browser Name:" + envDetails.get("browserName")
				+ " <br /> Browser Version:" + envDetails.get("browserVersion"));
	}

	// Create the report path
	private static String getReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!");
				return reportFileLocation;
			} else {
				System.out.println("Failed to create directory: " + path);
				return System.getProperty("user.dir");
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
		return reportFileLocation;
	}

	public HashMap<String, String> getBrowserAndOS() {
		HashMap<String, String> map = new HashMap<String, String>();
		Capabilities caps = ((HasCapabilities) driver.get()).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		String os = System.getProperty("os.name");
		map.put("browserName", browserName);
		map.put("browserVersion", browserVersion);
		map.put("os", os);
		return map;
	}

	public static ITestContext setContext(ITestContext iTestContext, ThreadLocal<WebDriver> driver) {
		iTestContext.setAttribute("driver", driver);
		return iTestContext;
	}

	@AfterMethod
	public void endExtent(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			context = result.getTestContext();
			driver = (ThreadLocal<WebDriver>) context.getAttribute("driver");

			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String timeStamp = timestamp.toString().replaceAll("[^\\d\\\\sA-Za-z]", ""); // get timestamp
			log.info("The value of timestamp converted to string is" + timeStamp);

			String screenShotName = result.getName() + timeStamp + ".png";
			File SrcFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
			File DestFile = new File(System.getProperty("user.dir") + "\\TestReport\\" + screenShotName);
			try {
				FileUtils.copyFile(SrcFile, DestFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				logger.fail(result.getThrowable().getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(DestFile.getPath()).build());
			} catch (IOException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		extent.flush();
	}

	@AfterClass
	public void assertLastRunTest() {
		extent.flush();
	}

	/**
	 * Close ExtentTestManager and WebDriver instance after execution of all tests
	 */
	@AfterTest
	public void tearDown() {

		log.info("In tearDown");
		driver.get().quit();
		log.info("Closed Driver");
		// driver=null;

	}
	/*
	 * @AfterSuite public void sendMail() throws MalformedURLException,
	 * EmailException { SendMail.createMailPayloadAndSend(); }
	 */
}
