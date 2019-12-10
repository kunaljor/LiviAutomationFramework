package pageObjects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Livi {
	public static Logger log;
	protected static ThreadLocal<WebDriver> driver;
	public static WebDriverWait wait;

	public Livi() {
		log = LogManager.getLogger(Livi.class.getName());
		log.info("In Livi CONSTRUCTOR");
		PageFactory.initElements(driver.get(), this);
	}

	public Livi(ThreadLocal<WebDriver> driver2) {
		log = LogManager.getLogger(Livi.class.getName());
		log.info("In Livi CONSTRUCTOR");
		// driver=new ThreadLocal<WebDriver>();
		driver = driver2;
		PageFactory.initElements(driver.get(), this);
	}

	public void setDriver(ThreadLocal<WebDriver> driver2) {
		driver = driver2;
		PageFactory.initElements(driver.get(), this);
	}

	@FindBy(how = How.XPATH, using = "//head/title")
	public WebElement pageTitle;

	@FindBy(how = How.NAME, using = "avaamoIframe")
	public WebElement frame;

	@FindBy(how = How.XPATH, using = "//div/img[contains(@class,'avm-bot-icon')]")
	public WebElement logo;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'avm-bot-welcome-notification')]")
	public WebElement welcomeNotificationBox;

	@FindBy(how = How.XPATH, using = "//h3")
	public WebElement welcomeNotificationMessage;

	@FindBy(how = How.CLASS_NAME, using = "avm-icon-cross")
	public WebElement welcomeNotificationClose;

	@FindBy(how = How.CLASS_NAME, using = "messages_nav-bar_title")
	public WebElement frameHeaderBar;

	@FindBy(how = How.CLASS_NAME, using = "title")
	public WebElement header;

	@FindBy(how = How.XPATH, using = "//textarea[@name='message']")
	public WebElement messageBox;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'send')]")
	public WebElement sendMessageButton;

	@FindBy(how = How.XPATH, using = "//div[@class='conversation-item not-mine']//p[contains(@class,'text-content')]")
	public WebElement replyToRandomMessage;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'avm-icon-menu')]")
	public WebElement menuOptionsButton;

	@FindBy(how = How.LINK_TEXT, using = "Main Menu")
	public WebElement mainMenuButton;

	@FindBy(how = How.CLASS_NAME, using = "default_card_description")
	public WebElement responseToMainMenuClick;

	public String testScenarios = "//div[contains(text(),'Test Scenarios')]//following-sibling::div[@class='default_card_link']/a";

	// div[text()='Enter Your Info']//parent::div[@class='default_card attachments']

	@FindBy(how = How.XPATH, using = "//div[text()='Enter Your Info']//parent::div[@class='default_card attachments']")
	public WebElement enterinfoForm;

	/* Locators for Enter Info Form */
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter Name')]//parent::div/div[2]/input[@class='textbox']")
	public WebElement infoformEnterName;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter Address')]//parent::div/div[2]/textarea[@class='textbox']")
	public WebElement infoformEnterAddress;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter Phone Number')]//parent::div/div[2]/input[@class='textbox']")
	public WebElement infoformEnterPhoneNumber;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter DOB')]//parent::div/div[2]/input[@class='textbox']")
	public WebElement infoformEnterDOB;
	@FindBy(how = How.XPATH, using = "//span[text()='Male']//preceding-sibling::span[@class='composer__container__preview__option__circle']")
	public WebElement infoformGenderMaleCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='Female']//preceding-sibling::span[@class='composer__container__preview__option__circle']")
	public WebElement infoformGenderFemaleCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='Bachelor']//preceding-sibling::span[@class='composer__container__preview__option__square']")
	public WebElement infoformQualificationBachelorCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='Master']//preceding-sibling::span[@class='composer__container__preview__option__square']")
	public WebElement infoformQualificationMasterCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='PHD']//preceding-sibling::span[@class='composer__container__preview__option__square']")
	public WebElement infoformQualificationPHDCheckMark;
	@FindBy(how = How.XPATH, using = "//span[starts-with(@id,'rating')]/label")
	public List<WebElement> infoformStarRating;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'default_card_submit')]")
	public WebElement infoformSubmit;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'default_card_submit success')]")
	public List<WebElement> infoformSubmittedSuccessFully;

	// div[@class='message-wrap'] /p[text()='Select your Country' ]
	@FindBy(how = How.XPATH, using = "//div[@class='message-wrap']/p[text()='Select your Country' ]")
	public List<WebElement> quickreplySelectYourCountry;
	// div[@id='quick-reply-container'] /div/a
	@FindBy(how = How.XPATH, using = "//div[@id='quick-reply-container']/div/a")
	public List<WebElement> listOfCountries;
	@FindBy(how = How.XPATH, using = "//div[@class='message-wrap']/p")
	public WebElement quickreplyVerifyCountry;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'conversation-item mine')]")
	public List<WebElement> allMessagesSentByUserTillNow;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'conversation-item not-mine')]")
	public List<WebElement> allRepliesByLiviTillNow;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'card_carousel_container scroll__x avm-slider')]")
	public List<WebElement> allCarouselsDisplayedTillNow;

	public String navigateRight = "//parent::div//div[contains(@class,'navigator right')]";

	public String navigateLeft = "//parent::div//div[contains(@class,'navigator left')]";

	public String allApostleNames = "//div[@class='default_card_title' and text()='Apostles']//parent::div/div[@class='default_card_description']";

	public String pathBeforeName = "//div[@class='default_card_description' and text()='";

	public String pathAfterName = "']//following-sibling::div/a";

	public String allApostleLinks = "//div[@class='default_card_title' and text()='Apostles']//parent::div/div[@class='default_card_link']";

	@FindBy(how = How.LINK_TEXT, using = "Avaamo")
	public WebElement avaamo;

	// a[text()='Call']
	@FindBy(how = How.LINK_TEXT, using = "Call")
	public WebElement call;

	@FindBy(how = How.LINK_TEXT, using = "Location")
	public WebElement location;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'location-icon')]")
	public List<WebElement> locationShared;

	public String responseTextContent = "//p[contains(@class,'text-content')]";

	public String allRepliesList = "//div[contains(@class,'conversation-item not-mine')]";

}
