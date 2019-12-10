package managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import enums.DriverType;
import enums.EnvironmentType;

public class DriverManager {
	 private static DriverManager instance = null;
	public static Logger log =LogManager.getLogger(DriverManager.class.getName());
	private ThreadLocal<WebDriver> driver= new ThreadLocal<WebDriver>();
	 private static DriverType driverType;
	 private  static EnvironmentType environmentType;
	 private  static String autURL;
	 private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	 private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
	 
	 
	 private DriverManager() {
	 driverType = FileManager.getInstance().getConfigReader().getBrowser();
	 //log.info("In DriverManager constructor , value of driverType is "+ driverType);
	 environmentType = FileManager.getInstance().getConfigReader().getEnvironment();
	 //log.info("In DriverManager constructor , value of environmentType is "+ environmentType);
	 autURL = FileManager.getInstance().getConfigReader().getAutURL();
	 //log.info("In DriverManager constructor , value of autURL is "+ autURL);
	 }


	    public static DriverManager getInstance() {
	    	 if(instance==null) { 
				  synchronized(DriverManager.class) {
			  if(instance==null) { 
				  instance = new DriverManager(); 
			  } 
			  } 
				  }
			  return instance;
	    }
	 
	 public ThreadLocal<WebDriver> getDriverAndOpenAUT() {
	 driver = createDriver();
	 //log.info("The url we get from file is "+autURL);
	 get_Url(autURL);
	 return driver;
	 }
	 
	 private ThreadLocal<WebDriver> createDriver() {
	    switch (environmentType) {     
	         case LOCAL_QA :
	        	 driver = createLocalDriver();
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
	 
	 private ThreadLocal<WebDriver> createRemoteDriver(){
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
	 
	 private ThreadLocal<WebDriver> createLocalDriver() {
	        switch (driverType) {     
	        case FIREFOX : 
	        	System.setProperty(FIREFOX_DRIVER_PROPERTY,System.getProperty("user.dir") + FileManager.getInstance().getConfigReader().getDriverPath());
			/*
			 * FirefoxProfile geoDisabled = new FirefoxProfile();
			 * geoDisabled.setPreference("geo.enabled", false);
			 * geoDisabled.setPreference("geo.provider.use_corelocation", false);
			 * geoDisabled.setPreference("geo.prompt.testing", false);
			 * geoDisabled.setPreference("geo.prompt.testing.allow", false); FirefoxOptions
			 * ffOptions= new FirefoxOptions(); ffOptions.setProfile(geoDisabled);
			 */
	        	driver.set(new FirefoxDriver());
	      break;
	        case CHROME : 
	         System.setProperty(CHROME_DRIVER_PROPERTY,System.getProperty("user.dir") + FileManager.getInstance().getConfigReader().getDriverPath());
	         ChromeOptions ccOptions = new ChromeOptions();
	         ccOptions.addArguments("start-maximized");
	         ccOptions.addArguments("test-type");
	         ccOptions.addArguments("enable-strict-powerful-feature-restrictions");
	         ccOptions.addArguments("disable-geolocation");
	         log.info("Before setting ChromeDriver");
	         driver.set(new ChromeDriver(ccOptions));
	         log.info("After setting ChromeDriver");
	     break;
			case INTERNETEXPLORER:
				break;
			default:
				break;
	        }
	 
	        if(FileManager.getInstance().getConfigReader().getBrowserWindowSize()) 
	        	 driver.get().manage().window().maximize();
	        //((WebDriver) driver).manage().timeouts().implicitlyWait(FileManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
	 return driver;
	 } 
	 
	  public void get_Url(String url) {
	    	if(!(url.isBlank() || url.isEmpty() || url==null)) {
	    		driver.get().get(url);	
	    }
	    	else {
	    		log.info("The URL value right now is  "+ url +" Hence no url opened" );	
	    	}
	    	}
	  
	 
}
