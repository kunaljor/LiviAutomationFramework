package pageObjects;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import managers.POManager;

public class CommonPO extends POManager {
	
	 public CommonPO() { PageFactory.initElements(driver, this); } 
	 
}
