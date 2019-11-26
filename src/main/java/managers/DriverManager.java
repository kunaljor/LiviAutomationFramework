package managers;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import enums.DriverType;
import enums.EnvironmentType;

public class DriverManager {
	public static Logger log =LogManager.getLogger(DriverManager.class.getName());
	private WebDriver driver;
	 private static DriverType driverType;
	 private  static EnvironmentType environmentType;
	 private  static String autURL;
	 private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	 private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
	 
	 
	 public DriverManager() {
	 driverType = FileManager.getInstance().getConfigReader().getBrowser();
	 environmentType = FileManager.getInstance().getConfigReader().getEnvironment();
	 autURL = FileManager.getInstance().getConfigReader().getAutURL();
	 }

	 public WebDriver getDriverAndOpenAUT() {
	 if(driver == null) driver = createDriver();
	 get_Url(autURL);
	 return driver;
	 }
	 
	 private WebDriver createDriver() {
	    switch (environmentType) {     
	         case LOCAL_QA : driver = createLocalDriver();
	          break;
	         case REMOTE_QA : driver = createRemoteDriver();
	          break;
		case LOCAL_PROD:
			break;
		case LOCAL_STAGE:
			break;
		case REMOTE_PROD:
			break;
		case REMOTE_STAGE:
			break;
		default:
			break;
	    }
	    return driver;
	 }
	 
	 private WebDriver createRemoteDriver(){
		 //IF SAUCELABS is used for Cross Browser Testing then following properties need to be set.
		 /*String sauceUserName = System.getenv("SAUCE_USERNAME");
	        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
	        String sauceURL = "https://ondemand.saucelabs.com/wd/hub";
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability("username", sauceUserName);
	        capabilities.setCapability("accessKey", sauceAccessKey);
	        capabilities.setCapability("browserName", "Safari");
	        capabilities.setCapability("platform", "macOS 10.13");
	        capabilities.setCapability("version", "11.1");
	        capabilities.setCapability("build", "Sleek Automation Framework");
	        capabilities.setCapability("name", "2-user-site");
	        driver = new RemoteWebDriver(new URL(sauceURL), capabilities);*/
	 throw new RuntimeException("RemoteWebDriver is not yet implemented");
	 }
	 
	 private WebDriver createLocalDriver() {
	        switch (driverType) {     
	        case FIREFOX : 
	        	System.setProperty(FIREFOX_DRIVER_PROPERTY,System.getProperty("user.dir") + FileManager.getInstance().getConfigReader().getDriverPath());
	        	driver = new FirefoxDriver();
	      break;
	        case CHROME : 
	         System.setProperty(CHROME_DRIVER_PROPERTY,System.getProperty("user.dir") + FileManager.getInstance().getConfigReader().getDriverPath());
	         driver = new ChromeDriver();
	     break;
			case INTERNETEXPLORER:
				break;
			default:
				break;
	        }
	 
	        if(FileManager.getInstance().getConfigReader().getBrowserWindowSize()) driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(FileManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
	 return driver;
	 } 
	 
	  public void get_Url(String url) {
	    	if(!(url.isBlank() || url.isEmpty() || url==null)) {
	    		log.info("Url to be opened "+ url );
	    		//ERROR here
	    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    		driver.get(url);
	    		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
	    }
	    	else {
	    		log.info("The URL value right now is  "+ url +" Hence no url opened" );	
	    	}
	    	}
	  
	 public void closeDriver() {
	 driver.quit();
	 if (null != driver)
			driver = null;
	 }
}
