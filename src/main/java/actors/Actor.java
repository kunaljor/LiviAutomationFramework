package actors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pageActions.LiviInteractions;
/**
 * 
 * @author Kunal Jor
 */
public class Actor{
	public static Logger log = LogManager.getLogger(Actor.class.getName());
	protected static ThreadLocal<WebDriver> driver;
	public static LiviInteractions liviInteractions;

	public LiviInteractions wakesUpLivi(ThreadLocal<WebDriver> driver) {
		log.info("In  clicksOnLiviButton");
		// return (liviInteractions == null) ? liviInteractions= new
		// LiviInteractions(driver,livi) : liviInteractions;
		/*
		 * if (liviInteractions == null) { synchronized (Actor.class) { if
		 * (liviInteractions == null) { liviInteractions = new LiviInteractions(driver);
		 * } } }
		 */
		return new LiviInteractions(driver);
	}
	}
