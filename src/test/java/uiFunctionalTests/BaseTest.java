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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import managers.DriverManager;
import managers.FileManager;
import managers.POManager;
import pageObjects.Livi;
import pageObjects.LiviInteractions;
import utilities.ExtentTestManager;

/**
 * 
 * @author Kunal Jor
 */
public class BaseTest {
	public Logger log = LogManager.getLogger(BaseTest.class.getName());
	ExtentTest logger;
	protected static WebDriver driver;

	protected static POManager po;
	protected DriverManager driverManager;
	// protected static LiviInteractions pageAction;
	protected SoftAssert sAssert = new SoftAssert();
	// ExtentTestManager extentTest;
	HashMap<String, String> envDetails;
	public Livi livi;
	protected boolean firstRun;

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static ExtentTest childTest;
	public static String reportFileName = "Test-Automation-Report" + ".html";
	public static String fileSeperator = System.getProperty("file.separator");
	public static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
	public static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

	protected ITestContext context;

	@BeforeTest
	/**
	 * Instantiate Driver Manager, Driver and Page Object Manager and Page Objects
	 */
	public void setUp(ITestContext iTestContext) {

		log.info("In BASE TEST Setup");
		log.info("Value of reportFileName " + reportFileName);
		log.info("Value of fileSeperator " + fileSeperator);
		log.info("Value of reportFilepath " + reportFilepath);
		log.info("Value of reportFileLocation " + reportFileLocation);

		if (driverManager == null)
			driverManager = new DriverManager();
		if (driver == null)
			driver = driverManager.getDriverAndOpenAUT();
		log.info("The value of driver set in BeforeSuite is " + driver);
		if (po == null)
			po = new POManager(driver);
		log.info("After POManager intialize");
		firstRun = true;
		this.context = setContext(iTestContext, driver);

		String fileLocation = getReportPath(reportFileLocation);

		htmlReporter = new ExtentHtmlReporter(fileLocation);
		htmlReporter.config().setCSS(".r-img { width: 50%; }");
		extent = new ExtentReports();
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");

extent.attachReporter(htmlReporter);
		
		htmlReporter.config().setCSS(".r-img { width: 30%; }");
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(reportFileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(reportFileName);
		htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
	}

	@BeforeClass
	public void essentialsForTest() {
		log.info("In BEFORE CLASS");
		livi = po.getLivi();
		if (firstRun) {
			livi.perform().openLivi();
			firstRun = false;
			log.info("In verifyLiviHealth FIRST RUN ");
		}
		envDetails = getBrowserAndOS();

	}
	
	  @BeforeMethod public void initializeExtent(Method method) { 
		//if you want to get the class name in before method
	      String classname = getClass().getSimpleName();
	//IF you want to get the method name in the before method 
	      String methodName = method.getName();
		  
		  logger=extent.createTest("Test : "+classname+" <br /> Sub-Test: "+methodName+" <br />  OS:" + envDetails.get("os") + " <br /> Browser Name:"+ envDetails.get("browserName") +" <br /> Browser Version:" + envDetails.get("browserVersion"));
	  //logger=extent.createTest("Sanity Test");
	  
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
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		String os = System.getProperty("os.name");
		map.put("browserName", browserName);
		map.put("browserVersion", browserVersion);
		map.put("os", os);
		return map;
	}

	public static ITestContext setContext(ITestContext iTestContext, WebDriver driver) {
		iTestContext.setAttribute("driver", driver);
		return iTestContext;
	}

	
	  @AfterMethod public void endExtent(ITestResult result) {
	  if(result.getStatus()==ITestResult.FAILURE) {
context =result.getTestContext(); 
	  WebDriver driver =(WebDriver)context.getAttribute("driver");
	  
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String timeStamp = timestamp.toString().replaceAll("[^\\d\\\\sA-Za-z]", ""); // get timestamp
		log.info("The value of timestamp converted to string is" + timeStamp);
		
		String screenShotName = result.getName() + timeStamp + ".png";
		File SrcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File DestFile = new File(System.getProperty("user.dir") + "\\TestReport\\" + screenShotName);
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	  try {
	  logger.fail(result.getThrowable().getMessage(),MediaEntityBuilder.createScreenCaptureFromPath(DestFile.getPath()).build());
	  } catch (IOException e) { // TODO Auto-generated catch block
	  e.printStackTrace(); }
	  
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
	@AfterSuite
	public void tearDown() {

		log.info("In tearDown");
		// extentTest.endTest();
		driverManager.closeDriver();

	}
}
