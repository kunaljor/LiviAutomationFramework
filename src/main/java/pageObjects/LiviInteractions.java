package pageObjects;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import enums.Apostle;
import enums.Country;
import enums.TestScenarioType;
import enums.WebViewButtonsAndLinks;
import managers.POManager;

public class LiviInteractions extends POManager{
	CommonPO commonPO;
	Livi livi;
	Random random= new Random();
	int taskCount=0;
	private static Set<String>urls=new HashSet<String>(); 
	String currentWindowHandle=null;
	int testbotRetriesCount=5;
	private List<String> allRepliesByLivi=new ArrayList<String>();
	private List<String> allMessagesSentByUser=new ArrayList<String>();
	private HashMap<Integer,Map<String,String>> allInteractions=new HashMap<Integer,Map<String,String>>();
	
	public LiviInteractions() {
		log.info("In Livi Interactions CONSTRUCTOR now ");
	}
	public  void click(WebElement button) {
		button.click();
	}
	
	public  void setText(WebElement field,String text) {
		field.clear();
		field.sendKeys(text);
	}
	
    
    public void openLivi() {
    	log.info("Opening Livi");
    	livi=getLivi();
    	//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	wait.until(ExpectedConditions.visibilityOf(livi.welcomeNotificationBox));
    	
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	log.info("Welcome Notification Header - <br /> "+livi.welcomeNotificationMessage.getText());

    	wait.until(ExpectedConditions.elementToBeClickable(livi.logo));
    	
    	log.info("After wait");
    	livi.logo.click();
    	log.info("After click");
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	driver.switchTo().frame(livi.frame);
    	log.info("After switchto livi frame");
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	log.info("Livi's Greeting - "+livi.header.getText());
    	log.info("After livis greeting");
    	
    }
    
    public void interactWithLivi(String message) {
    	log.info("My message to Livi-"+ message);
    	typeMessageAndSend(message);
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void openMainMenu() {
    	log.info("In Main Menu method");
    	livi.menuOptionsButton.click();
    	log.info("After Menu Options Click");
    	try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	livi.mainMenuButton.click();
    	log.info("After Main Menu  Click");
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	log.info("Livi's Response to \"Main Menu\" selection... <br /> "+lastReplyReceived().getText());
    }
     public List<WebElement> fetchLatestTestBot(){
    	 typeMessageAndSend("Test Bot");
    	 try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 while(testbotRetriesCount>0 && lastReplyReceived().getText().contains("Test Bot")) {
    		 try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 testbotRetriesCount--;
    		 typeMessageAndSend("Test Bot");
    	 }
    	 List<WebElement>testScenarios=lastReplyReceived().findElements(By.xpath(livi.testScenarios));
    	 wait.until(ExpectedConditions.visibilityOfAllElements(testScenarios));
    	 return testScenarios;
     }
    public void typeMessageAndSend(String message) {
    	livi.messageBox.sendKeys(message);
    	livi.sendMessageButton.click();
    }
    
    public void selectATestScenario(TestScenarioType scenario,List<WebElement> latestTestScenarios) {
    	for (WebElement testScenario:latestTestScenarios) {
    		String text= testScenario.getText().replaceAll(" ", "_");
    		log.info("The text captured from testScenario after removingspaces is "+ text);
    		if(text.equalsIgnoreCase(scenario.toString())) {
    			log.info("We are in scenario match condition");
    			testScenario.click();
    			break;
    		}
    	}
    	
    }
    
    public void fillInfoForm(HashMap<String,String> userInfo)  {
    	List<WebElement>latestTestScenarios=fetchLatestTestBot();
    	selectATestScenario(TestScenarioType.ALL_INPUT,latestTestScenarios);
   	 wait.until(ExpectedConditions.visibilityOf(livi.enterinfoForm));	
    	livi.infoformEnterName.sendKeys(userInfo.get("Name"));
    	livi.infoformEnterAddress.sendKeys(userInfo.get("Address"));
    	livi.infoformEnterPhoneNumber.sendKeys(userInfo.get("PhoneNumber"));
    	livi.infoformEnterDOB.sendKeys(userInfo.get("DOB"));
    	setGender(userInfo.get("Gender"));
    	setQualification(userInfo.get("Qualification"));
    	//giveRandomRating();
    	giveRating(Integer.parseInt(userInfo.get("Rating")));
    	log.info("Before Info Form Submit");
    	scrollElementIntoView(livi.infoformSubmit);
    	livi.infoformSubmit.click();
    	log.info("After Info Form Submit");
    	
    }
    
    public void setGender(String gender) {
    	switch(gender.toUpperCase()) {
    	case "MALE":
    		livi.infoformGenderMaleCheckMark.click();
    		break;
    	case "FEMALE":
    		livi.infoformGenderFemaleCheckMark.click();
    		break;	
    	}
    }
    
    public void setQualification(String qualification) {
    	switch(qualification.toUpperCase()) {
    	case "BACHELOR":
    		livi.infoformQualificationBachelorCheckMark.click();
    		break;
    	case "MASTERS":
    		livi.infoformQualificationMasterCheckMark.click();
    		break;
    	case "PHD":
    		livi.infoformQualificationPHDCheckMark.click();
    		break;	
    	}
    }
    
    public void giveRating(int rating) {
    	livi.infoformStarRating.get(rating).click();
    }
    public void giveRandomRating() {
    	int randomInt= random.nextInt(5); //Generates random integers between 0(included) to 5(excluded)
    	livi.infoformStarRating.get(randomInt).click();
    }
    
    public boolean verifyInfoFormSubmissionSuccessMessage() {
    	//wait.until(ExpectedConditions.visibilityOf(replyByLiviToLatestMessage()));
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(livi.infoformSubmittedSuccessFully.isEmpty()) {
    		log.info("In Info Form Submitted Successfully EMPTY Condition");
    		return false;
    	}
    	log.info("In Info Form Submitted Successfully NOT EMPTY Condition");
    	return true;
    }
    
    public void scrollElementIntoView(WebElement element) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    }
    
    public void selectYourCountry(Country countryToBeSelected) throws InterruptedException {
    	List<WebElement>latestTestScenarios=fetchLatestTestBot();
    	Thread.sleep(5000);
    	selectATestScenario(TestScenarioType.QUICK_REPLY,latestTestScenarios);
    	wait.until(ExpectedConditions.visibilityOfAllElements(livi.listOfCountries));
    	for (WebElement element:livi.listOfCountries) {
    		String country= element.getText().replaceAll(" ", "_");
    		log.info("The text captured from testScenario after removingspaces is "+ country);
    		if(country.equalsIgnoreCase(countryToBeSelected.toString())) {
    			log.info("We are in scenario match condition");
    			element.click();
    			break;
    		}
    	}
		/*
		 * if(lastReplyReceived().getText().replaceAll(" ",
		 * "_").contains(countryToBeSelected.toString()))return true; return false;
		 */
    }
    
    public WebElement lastReplyReceived() {
    	int totalMessagesSentTillNow=livi.allRepliesByLiviTillNow.size();
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebElement getLastReply=livi.allRepliesByLiviTillNow.get(totalMessagesSentTillNow-1);
    	String lastReply=getLastReply.getText();
    	
    	log.info("The last reply from Livi was "+ lastReply);
    	return getLastReply;
    }
    
    public String getLastReply() {
    	int totalMessagesSentTillNow=livi.allRepliesByLiviTillNow.size();
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebElement getLastReply=livi.allRepliesByLiviTillNow.get(totalMessagesSentTillNow-1);
    	String lastReply=getLastReply.getText();
    	log.info("In getLastReply,  The last reply from Livi was "+ lastReply);
    	return lastReply;
    }
    
    public List<String> getLastReplyList() {
    	//int totalMessagesSentTillNow=livi.allRepliesByLiviTillNow.size();
    	List<String> lastReplyList=new ArrayList<String>();
    	List<WebElement> getLastReplyList=null;
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			getLastReplyList = lastMessageSent1().findElements(By.xpath(livi.allRepliesList));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	getLastReplyList.stream().forEach(lastReply -> {
    		lastReplyList.add(lastReply.getText());	
    	});
    	
    	log.info("In getLastReply,  The last reply from Livi was "+ lastReplyList);
    	return lastReplyList;
    }
    
    public HashMap<String,List<String>> getLastInteraction(){
    		HashMap<String,List<String>> interactions=new HashMap<String,List<String>>();
    		//allRepliesByLivi.add(allReplies.get(i).getText());
    		//allMessagesSentByUser.add(allMessagesSent.get(i).getText());
    		try {
				interactions.put(lastMessageSent(),getLastReplyList());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	return interactions;
 
    }
    
    public HashMap<Integer,Map<String,String>> getAllInteractions(){
    	List<WebElement>allReplies=livi.allRepliesByLiviTillNow;
    	List<WebElement>allMessagesSent=livi.allMessagesSentByUserTillNow;
    	
    	for(int i=0;i<allRepliesByLivi.size()-1;i++) {
    		Map<String,String> interactions=new HashMap<String,String>();
    		//allRepliesByLivi.add(allReplies.get(i).getText());
    		//allMessagesSentByUser.add(allMessagesSent.get(i).getText());
    		interactions.put(allMessagesSent.get(i).getText(),allReplies.get(i).getText());
    		allInteractions.put(i,interactions);
    	}
    	
    	return allInteractions;
 
    }
    
    public String lastMessageSent() throws InterruptedException {
    	int totalMessagesSentTillNow=livi.allMessagesSentByUserTillNow.size();
    	Thread.sleep(10000);
    	WebElement getLastMessageSent=livi.allMessagesSentByUserTillNow.get(totalMessagesSentTillNow-1);
    	String lastMessageSent=getLastMessageSent.getText();
    	log.info("The last message to Livi was "+ getLastMessageSent);
    	return lastMessageSent;
    }
    
    public WebElement lastMessageSent1() throws InterruptedException {
    	int totalMessagesSentTillNow=livi.allMessagesSentByUserTillNow.size();
    	Thread.sleep(10000);
    	WebElement getLastMessageSent1=livi.allMessagesSentByUserTillNow.get(totalMessagesSentTillNow-1);
    	return getLastMessageSent1;
    }
    
    public WebElement getLatestCarousel() {
    	log.info(" In Get latest Carousel");
    	int totalCarouselsDisplayedTillNow=livi.allCarouselsDisplayedTillNow.size();
    	log.info("After Counting Carousels");
    	WebElement latestCarousel=livi.allCarouselsDisplayedTillNow.get(totalCarouselsDisplayedTillNow-1);
    	log.info("After capturing latest Carousel");
    	return latestCarousel;
    }
    
    public boolean selectApostle(Apostle selectApostle) throws InterruptedException {
    	Thread.sleep(5000);
    	String apostleToBeSelected=selectApostle.toString();
    	String chosenApostle=null;
    	List<WebElement>latestTestScenarios=fetchLatestTestBot();
    	Thread.sleep(5000);
    	log.info("The Latest Test Scenario Webelement is "+latestTestScenarios.toString());
    	selectATestScenario(TestScenarioType.CAROUSEL,latestTestScenarios);
    	Thread.sleep(30000);
    	WebElement Carousel=getLatestCarousel();
    	List<WebElement> rightNavigators=Carousel.findElements(By.xpath(livi.navigateRight));
    	int navCount= rightNavigators.size();
    	log.info("Number fo right Navigators found is "+rightNavigators.size());
    	WebElement rightNavigator=rightNavigators.get(navCount-1);
    	List<WebElement> allApostles= Carousel.findElements(By.xpath(livi.allApostleNames));
    	log.info(" After fetching apostle names list");
    	for (WebElement element:allApostles) {
    		String apostle= element.getText();
    		log.info("The Apostle name captured is "+ apostle);
    		if(apostle.equalsIgnoreCase(apostleToBeSelected)) {
    			log.info("We are in scenario match condition");
    			chosenApostle=apostle;
    			String apostleLink=livi.pathBeforeName+chosenApostle+livi.pathAfterName;
    			log.info("Constructed Apostle Link path is "+apostleLink);
    			Thread.sleep(5000);
    			if(element.findElements(By.xpath(apostleLink)).isEmpty()) {
    			log.info("The chosen apostle "+chosenApostle+" doesn't have an associated link" );
    			return false;
    			}
    			else {
    			element.findElement(By.xpath(apostleLink)).click();
    			return true;
    			}
    		}
    		else {
    			rightNavigator.click();
    			driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
    		}
    	}
    	return false;
    }
    
    public boolean openWebViewLinks(EnumSet<WebViewButtonsAndLinks> linksToBeOpened) throws InterruptedException {
    	List<WebElement>latestTestScenarios=fetchLatestTestBot();
    	log.info("The Latest Test Scenario Webelement is "+latestTestScenarios.toString());
    	selectATestScenario(TestScenarioType.WEB_VIEW,latestTestScenarios);
    	Thread.sleep(10000);
    	int countOfLinksToBeOpened=linksToBeOpened.size();
    	linksToBeOpened.stream().forEach(link ->{
    		//String link=linkEnum.toString();
    		log.info("The link to be opened now is "+link);
    		switch(link) {
            case POST_MESSAGE:
               log.info("Not Implemented Yet!");
               break;
            case GOTO:
            	log.info("Not Implemented Yet!");
               break;
            case WEBSITE:
            	log.info("Not Implemented Yet!");
               break;
            case AVAAMO:
            	try {
					openAvaamo();
					switchBackToLiviFrame();
					taskCount++;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
            	
               break;
            case THANKS:
            	log.info("Not Implemented Yet!");
               break;
            case CALL:
            	livi.call.click();
            	currentWindowHandle=driver.getWindowHandle();
            	try {
					Thread.sleep(10000);
					log.info("Current Window Handle is"+currentWindowHandle);
					driver.switchTo().window(currentWindowHandle);
					log.info("Switched to Current Window");
					Thread.sleep(10000);
					Robot keyBoard = new Robot();
					// Since focus is on alert cancel by default, we only need to press enter key to close the alert.
					Thread.sleep(2000);	
					keyBoard.keyPress(KeyEvent.VK_ENTER);
					log.info("Windows Alert Closed");
					switchBackToLiviFrame();
					Thread.sleep(2000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	log.info("Clicked on \"CALL\"");
            	taskCount++;
                break;
             case LOCATION:
				try {
					if(shareLocation()) 
					{
						log.info("Location Successfully Shared");
						taskCount++;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
             case NUMBER:
            	 log.info("Not Implemented Yet!");
                break;
             case TEST:
            	 log.info("Not Implemented Yet!");
                break;
         }
    		
    	}
    	);

    	if(taskCount==countOfLinksToBeOpened) {
    		return true;
    	}
    	return false;
    }
    
    public void openAvaamo() throws InterruptedException {
    	log.info("Clicking on \"AVAAMO\"");
    	currentWindowHandle=driver.getWindowHandle();
    	livi.avaamo.click();
    	Set<String>allWindows=driver.getWindowHandles();
    	log.info("All window handles are "+allWindows);
    	allWindows.stream().forEach(windowHandle ->{
    		if(!windowHandle.equals(currentWindowHandle)) {
    			driver.switchTo().window(windowHandle);
    		}
    	});
    	Thread.sleep(5000);
    	log.info("On Avaamo Site now ,Fetched Title-"+ driver.getTitle());
    	allWindows=driver.getWindowHandles();
    	log.info("All latest window handles are "+allWindows);
    	driver.switchTo().window(currentWindowHandle);
    }
    
    public void switchBackToLiviFrame() throws InterruptedException {
    	
    	driver.switchTo().defaultContent();
    	log.info("Back on Main Page");
    	Thread.sleep(20000);
    	driver.switchTo().frame(livi.frame);
    	Thread.sleep(2000);
    	log.info("Livi's Greeting - "+livi.header.getText());
    }
    
    public boolean shareLocation() throws InterruptedException {
    	livi.location.click();
    	ifAlertPresentAccept();
    	//if(livi.locationShared.isEmpty())return false;
    	String responseToLocationShared=lastReplyReceived().getText();
    	log.info(" Response to Shared Location is "+responseToLocationShared);
    	if(!responseToLocationShared.contains("title")|| !responseToLocationShared.contains("lon") || !responseToLocationShared.contains("lat") || !responseToLocationShared.contains("description")){
    		return false;
    	}
    	return true;
    }
    
    public boolean ifAlertPresentDismiss() 
    { 
        try 
        { 
        	wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().dismiss(); 
            return true; 
        }   // try 
        catch (NoAlertPresentException Ex) 
        { 
            return false; 
        }   // catch 
    }   // isAlertPresent()
    
    public boolean ifAlertPresentAccept() 
    { 
        try 
        { 
        	wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept(); 
            
            return true; 
        }   // try 
        catch (NoAlertPresentException Ex) 
        { 
            return false; 
        }   // catch 
    } 
}
