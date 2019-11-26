package dataProviders;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import enums.DriverType;
import enums.EnvironmentType;
import managers.POManager;

public class ConfigFileReader {
	public static Logger log =LogManager.getLogger(ConfigFileReader.class.getName());
	private Properties properties;
	 private final String propertyFilePath= System.getProperty("user.dir")+"//src//test//resources//Configuration.properties";
	 String browserName;
	 String os;
	 
	 public ConfigFileReader(){
		
	 BufferedReader reader;
	 try {
	 reader = new BufferedReader(new FileReader(propertyFilePath));
	 properties = new Properties();
	 try {
	 properties.load(reader);
	 reader.close();
	 } catch (IOException e) {
	 e.printStackTrace();
	 }
	 } catch (FileNotFoundException e) {
	 e.printStackTrace();
	 throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
	 } 
	 }
	 
	 public String getDriverPath(){
     os= System.getProperty("os.name");
	 String driverPath=null ;
	 DriverType driver=getBrowser();
	 if(driver==null) {
		 throw new RuntimeException("driverPath not specified in the Configuration.properties file.");
	 }
	 else {
	 switch(driver) {
	 case FIREFOX : 
		 if(os.contains("Windows")) {
		 driverPath = properties.getProperty("windowsFirefoxPath");
		 }
		 if(os.contains("Mac")) {
			 driverPath = properties.getProperty("macFirefoxPath");
			 }
     break;
     case CHROME : 
		 if(os.contains("Windows")) {
		 driverPath = properties.getProperty("windowsChromePath");
		 }
		 if(os.contains("Mac")) {
			 driverPath = properties.getProperty("macChromePath");
			 }
    break;
	case INTERNETEXPLORER:
		break;
	default:
		break;
	 }
	}
return driverPath ;
	 }
	 
	 public String getAutURL() {
		 String aut=properties.getProperty("autURL");
		 log.info("The url fetched from properties file is"+aut);
		 if(aut!=null)
			 return aut;
		 else
			 throw new RuntimeException("AUT URL not specified in the Configuration.properties file.");	 
	 }
	 
	 public long getImplicitlyWait() { 
	 String implicitlyWait = properties.getProperty("implicitlyWait");
	 if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
	 else throw new RuntimeException("implicitlyWait not specified in the Configuration.properties file."); 
	 }
	 
	 
	 public DriverType getBrowser() {
		 if(!System.getProperty("browser"," ").isBlank()) {
			 browserName=System.getProperty("browser");
		 }
		 else {
		 browserName= properties.getProperty("browser");
		 }
		 if(browserName == null || browserName.equalsIgnoreCase("chrome")) return DriverType.CHROME;
		 else if(browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
		 else if(browserName.equalsIgnoreCase("iexplorer")) return DriverType.INTERNETEXPLORER;
		 else throw new RuntimeException("Browser Name Key value in Configuration.properties is not matched : " + browserName);
		 }
		 
		 public EnvironmentType getEnvironment() {
		 String environmentName = properties.getProperty("environment");
		 if(environmentName == null || environmentName.equalsIgnoreCase("local")) return EnvironmentType.LOCAL_QA;
		 else if(environmentName.equals("remote")) return EnvironmentType.REMOTE_QA;
		 else throw new RuntimeException("Environment Type Key value in Configuration.properties is not matched : " + environmentName);
		 }
		 
		 public Boolean getBrowserWindowSize() {
		 String windowSize = properties.getProperty("windowMaximize");
		 if(windowSize != null) return Boolean.valueOf(windowSize);
		 return true;
		 }
	 
}
