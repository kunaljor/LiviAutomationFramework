package managers;

import pageObjects.Livi;
import pageObjects.LiviInteractions;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.CommonPO;

public class POManager {
	public static Logger log =LogManager.getLogger(POManager.class.getName());
	public static WebDriver driver;
	public Livi livi;
	private CommonPO commonPO;
	public static LiviInteractions liviInteractions;
	protected static WebDriverWait wait;
	protected static long Timeout;
	public POManager() {
		
	}
	
	public POManager(WebDriver driver) {
		log.info("In PO Manager CONSTRUCTOR");
		POManager.driver = driver;
		//log.info("After setting driver value");
		Timeout=FileManager.getInstance().getConfigReader().getImplicitlyWait();
		//log.info("After setting Timeout value");
		wait=new WebDriverWait(driver,15);
		//log.info("After setting wait value");
	}
	
	public Livi getLivi() {
log.info("Value of livi now "+ livi);
		return (livi == null) ? livi = new Livi() : livi;

	}
	  public CommonPO getCommonPO() {
	  
	  return (commonPO == null) ? commonPO = new CommonPO() : commonPO;
	  
	  }
	  public  LiviInteractions perform() {
			 return (liviInteractions == null) ? liviInteractions= new LiviInteractions() : liviInteractions;	 
		}
	

}
