package pageObjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import managers.POManager;

public class Livi extends POManager {

	public Livi() {
		log.info("In Livi CONSTRUCTOR");
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.NAME, using = "avaamoIframe")
	protected WebElement frame;

	@FindBy(how = How.XPATH, using = "//div/img[contains(@class,'avm-bot-icon')]")
	protected WebElement logo;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'avm-bot-welcome-notification')]")
	protected WebElement welcomeNotificationBox;

	@FindBy(how = How.XPATH, using = "//h3")
	protected WebElement welcomeNotificationMessage;

	/*
	 * @FindBy(how = How.CLASS_NAME, using = "avm-welcome-notification-message")
	 * protected WebElement liviWelcomeNotificationMessage;
	 */
	@FindBy(how = How.CLASS_NAME, using = "avm-icon-cross")
	protected WebElement welcomeNotificationClose;

	@FindBy(how = How.CLASS_NAME, using = "title")
	protected WebElement header;

	@FindBy(how = How.XPATH, using = "//textarea[@name='message']")
	protected WebElement messageBox;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'send')]")
	protected WebElement sendMessageButton;

	@FindBy(how = How.XPATH, using = "//div[@class='conversation-item not-mine']//p[contains(@class,'text-content')]")
	protected WebElement replyToRandomMessage;

	@FindBy(how = How.XPATH, using = "//div[contains(@class,'avm-icon-menu')]")
	protected WebElement menuOptionsButton;

	@FindBy(how = How.LINK_TEXT, using = "Main Menu")
	protected WebElement mainMenuButton;
	
	@FindBy(how = How.CLASS_NAME, using = "default_card_description")
	protected WebElement responseToMainMenuClick;
	
protected String testScenarios= "//div[contains(text(),'Test Scenarios')]//following-sibling::div[@class='default_card_link']/a";
	
	//div[text()='Enter Your Info']//parent::div[@class='default_card attachments']
	
	@FindBy(how = How.XPATH, using = "//div[text()='Enter Your Info']//parent::div[@class='default_card attachments']")
	protected WebElement enterinfoForm;
	
	/*Locators for Enter Info Form*/
	//div[contains(text(),'Enter Name')]//parent::div/div[2]/input[@class='textbox']
	//div[contains(text(),'Enter Address')]//parent::div/div[2]/textarea[@class='textbox']
	//div[contains(text(),'Enter Phone Number')]//parent::div/div[2]/input[@class='textbox']
	//div[contains(text(),'Enter DOB')]//parent::div/div[2]/input[@class='textbox']
	//span[text()='Male']//preceding-sibling::span[@class='composer__container__preview__option__circle']
	//span[text()='Female']//preceding-sibling::span[@class='composer__container__preview__option__circle']
	//span[text()='Bachelor']//preceding-sibling::span[@class='composer__container__preview__option__square']
	//span[text()='Master']//preceding-sibling::span[@class='composer__container__preview__option__square']
	//span[text()='PHD']//preceding-sibling::span[@class='composer__container__preview__option__square']
	//span[starts-with(@id,'rating')]/label
	//btn default_card_submit   //button[@class='btn default_card_submit']
	//button[contains(@class,'default_card_submit success')]
	
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter Name')]//parent::div/div[2]/input[@class='textbox']")
	protected WebElement infoformEnterName;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter Address')]//parent::div/div[2]/textarea[@class='textbox']")
	protected WebElement infoformEnterAddress;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter Phone Number')]//parent::div/div[2]/input[@class='textbox']")
	protected WebElement infoformEnterPhoneNumber;
	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Enter DOB')]//parent::div/div[2]/input[@class='textbox']")
	protected WebElement infoformEnterDOB;
	@FindBy(how = How.XPATH, using = "//span[text()='Male']//preceding-sibling::span[@class='composer__container__preview__option__circle']")
	protected WebElement infoformGenderMaleCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='Female']//preceding-sibling::span[@class='composer__container__preview__option__circle']")
	protected WebElement infoformGenderFemaleCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='Bachelor']//preceding-sibling::span[@class='composer__container__preview__option__square']")
	protected WebElement infoformQualificationBachelorCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='Master']//preceding-sibling::span[@class='composer__container__preview__option__square']")
	protected WebElement infoformQualificationMasterCheckMark;
	@FindBy(how = How.XPATH, using = "//span[text()='PHD']//preceding-sibling::span[@class='composer__container__preview__option__square']")
	protected WebElement infoformQualificationPHDCheckMark;
	@FindBy(how = How.XPATH, using = "//span[starts-with(@id,'rating')]/label")
	protected List<WebElement> infoformStarRating;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'default_card_submit')]")
	protected WebElement infoformSubmit;
	@FindBy(how = How.XPATH, using = "//button[contains(@class,'default_card_submit success')]")
	protected List<WebElement> infoformSubmittedSuccessFully;
	
	//div[@class='message-wrap'] /p[text()='Select your Country' ]
	@FindBy(how = How.XPATH, using = "//div[@class='message-wrap']/p[text()='Select your Country' ]")
	protected List<WebElement> quickreplySelectYourCountry;
	//div[@id='quick-reply-container'] /div/a
	@FindBy(how = How.XPATH, using = "//div[@id='quick-reply-container']/div/a")
	protected List<WebElement> listOfCountries;
	@FindBy(how = How.XPATH, using = "//div[@class='message-wrap']/p")
	protected WebElement quickreplyVerifyCountry;
	
	
	//div[contains(@class,'conversation-item mine')][8]
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'conversation-item mine')]")
	protected List<WebElement> allMessagesSentByUserTillNow;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'conversation-item not-mine')]")
	protected List<WebElement> allRepliesByLiviTillNow;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'card_carousel_container scroll__x avm-slider')]")
	protected List<WebElement> allCarouselsDisplayedTillNow;
	
	protected String navigateRight="//parent::div//div[contains(@class,'navigator right')]";
	
	protected String navigateLeft="//parent::div//div[contains(@class,'navigator left')]";
	
	protected String allApostleNames="//div[@class='default_card_title' and text()='Apostles']//parent::div/div[@class='default_card_description']";
	
	protected String pathBeforeName="//div[@class='default_card_description' and text()='";
	
	protected String pathAfterName="']//following-sibling::div/a";
	
	protected String allApostleLinks="//div[@class='default_card_title' and text()='Apostles']//parent::div/div[@class='default_card_link']";
	
	//a[text()='Avaamo']
	@FindBy(how = How.LINK_TEXT, using ="Avaamo")
	protected WebElement avaamo;
	
	//a[text()='Call']
	@FindBy(how = How.LINK_TEXT, using ="Call")
	protected WebElement call;
	//a[text()='Location']
	@FindBy(how = How.LINK_TEXT, using ="Location")
	protected WebElement location;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'location-icon')]")
	protected List<WebElement> locationShared;
	
	protected String responseTextContent="//p[contains(@class,'text-content')]";
	
	protected String allRepliesList="//div[contains(@class,'conversation-item not-mine')]";
	

}
