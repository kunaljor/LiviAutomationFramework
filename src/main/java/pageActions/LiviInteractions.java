package pageActions;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dataProviders.ConfigFileReader;
import enums.Apostle;
import enums.Country;
import enums.TestScenarioType;
import enums.WebViewButtonsAndLinks;
import pageObjects.Livi;

public class LiviInteractions {
	

	private Random random = new Random();
	int taskCount = 0;
	private static String mainWindowHandle = null;
	private int testbotRetriesCount = 5;
	private List<String> allRepliesByLivi = new ArrayList<String>();
	private HashMap<Integer, Map<String, String>> allInteractions = new HashMap<Integer, Map<String, String>>();
	protected static ThreadLocal<WebDriver> driver;
	private static WebDriverWait wait;
	private static Logger log;
	private HashMap<String, List<String>> interactions;

	public LiviInteractions(ThreadLocal<WebDriver> driver2) {
		log =LogManager.getLogger(LiviInteractions.class.getName());
		driver=driver2;
		wait=new WebDriverWait(driver.get(),30);
	}

	public void click(WebElement button) {
		button.click();
	}

	public void setText(WebElement field, String text) {
		field.clear();
		field.sendKeys(text);
	}

	public synchronized void opensLivi(Livi livi) {
		log.info("Opening Livi");
		wait.until(ExpectedConditions.visibilityOf(livi.welcomeNotificationMessage));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		wait.until(ExpectedConditions.elementToBeClickable(livi.logo));
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		livi.logo.click();
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(livi.frame));
		driver.get().switchTo().frame(livi.frame);
		log.info("Livi Opened and Switched To Frame");

	}

	public void chatsWithLivi(String message,Livi livi) {
		typeMessageAndSend(message,livi);
		storeLastInteraction(livi);
	}

	public void opensMainMenu(Livi livi) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		livi.menuOptionsButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(livi.mainMenuButton));
		livi.mainMenuButton.click();
	}

	public List<WebElement> fetchLatestTestBot(Livi livi) {
		//log.info("In fetchLatestTestBot");
		typeMessageAndSend("Test Bot",livi);
		while (testbotRetriesCount > 0 && fetchesLastReply(livi).contains("Test Bot")) {
			testbotRetriesCount--;
			typeMessageAndSend("Test Bot",livi);
			wait.until(ExpectedConditions.visibilityOfAllElements(fetchLastReplyReceivedElement(livi).findElements(By.xpath(livi.testScenarios))));
		}
		List<WebElement> testScenarios=fetchLastReplyReceivedElement(livi).findElements(By.xpath(livi.testScenarios));

		return testScenarios;
	}

	public  void typeMessageAndSend(String message,Livi livi) {
		wait.until(ExpectedConditions.elementToBeClickable(livi.messageBox));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		livi.messageBox.sendKeys(message);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		livi.sendMessageButton.click();
	}

	public void selectATestScenario(TestScenarioType scenario, List<WebElement> latestTestScenarios,Livi livi) {
		//wait.until(ExpectedConditions.visibilityOfAllElements(latestTestScenarios));
		for (WebElement testScenario : latestTestScenarios) {
			String text = testScenario.getText().replaceAll(" ", "_");
			log.info("The text captured from testScenario after removingspaces is " + text);
			if (text.equalsIgnoreCase(scenario.toString())) {
				log.info("SCENARIO matched is "+scenario.toString());
				testScenario.click();
				break;
			}
		}

	}
	
	public void selectATestScenario(TestScenarioType scenario,Livi livi) {
		//wait.until(ExpectedConditions.visibilityOfAllElements(latestTestScenarios));
		List<WebElement> latestTestScenarios = fetchLatestTestBot(livi);
		for (WebElement testScenario : latestTestScenarios) {
			String text = testScenario.getText().replaceAll(" ", "_");
			log.info("The text captured from testScenario after removingspaces is " + text);
			if (text.equalsIgnoreCase(scenario.toString())) {
				log.info("SCENARIO matched is "+scenario.toString());
				testScenario.click();
				break;
			}
		}

	}

	public void fillsInfoForm(HashMap<String, String> userInfo,Livi livi) {
		//List<WebElement> latestTestScenarios = fetchLatestTestBot(livi);
		//selectATestScenario(TestScenarioType.ALL_INPUT, latestTestScenarios,livi);
		selectATestScenario(TestScenarioType.ALL_INPUT,livi);
		//scrollElementIntoViewJs(livi.enterinfoForm);
		wait.until(ExpectedConditions.visibilityOf(livi.enterinfoForm));
		livi.infoformEnterName.sendKeys(userInfo.get("Name"));
		livi.infoformEnterAddress.sendKeys(userInfo.get("Address"));
		livi.infoformEnterPhoneNumber.sendKeys(userInfo.get("PhoneNumber"));
		livi.infoformEnterDOB.sendKeys(userInfo.get("DOB"));
		setGender(userInfo.get("Gender"),livi);
		setQualification(userInfo.get("Qualification"),livi);
		giveRating(Integer.parseInt(userInfo.get("Rating")),livi);
		scrollToBottomAction();
		livi.infoformSubmit.click();

	}

	public String convertDateToString(Date date) { 
	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    String strDate = formatter.format(date);  
	    log.info("Converted date to string is "+ strDate);
	    return strDate;
	}
	
	public void setGender(String gender,Livi livi) {
		switch (gender.toUpperCase()) {
		case "MALE":
			livi.infoformGenderMaleCheckMark.click();
			break;
		case "FEMALE":
			livi.infoformGenderFemaleCheckMark.click();
			break;
		}
	}

	public void setQualification(String qualification,Livi livi) {
		switch (qualification.toUpperCase()) {
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

	public void giveRating(int rating,Livi livi) {
		livi.infoformStarRating.get(rating).click();
	}

	public void giveRandomRating(Livi livi) {
		int randomInt = random.nextInt(5); // Generates random integers between 0(included) to 5(excluded)
		livi.infoformStarRating.get(randomInt).click();
	}

	public boolean verifiesInfoFormSubmissionSuccessMessage(Livi livi) {
		wait.until(ExpectedConditions.visibilityOfAllElements(fetchLastReplyReceivedListElement(livi)));
		if (livi.infoformSubmittedSuccessFully.isEmpty()) {
			return false;
		}
		return true;
	}

	public void scrollElementIntoViewJs(WebElement element) {
		((JavascriptExecutor) driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void scrollToBottomAction() {
		Actions actions = new Actions(driver.get());
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
	}

	public void selectsACountry(Country countryToBeSelected,Livi livi){
		//List<WebElement> latestTestScenarios = fetchLatestTestBot(livi);
		//selectATestScenario(TestScenarioType.QUICK_REPLY, latestTestScenarios,livi);
		selectATestScenario(TestScenarioType.QUICK_REPLY,livi);
		wait.until(ExpectedConditions.visibilityOfAllElements(livi.listOfCountries));
		for (WebElement element : livi.listOfCountries) {
			String country = element.getText().replaceAll(" ", "_");
			if (country.equalsIgnoreCase(countryToBeSelected.toString())) {
				log.info("We are in scenario match condition for Country");
				element.click();
				break;
			}
		}
	}

	public WebElement fetchLastReplyReceivedElement(Livi livi) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalRepliesTillNow = livi.allRepliesByLiviTillNow.size();
		WebElement getLastReply = livi.allRepliesByLiviTillNow.get(totalRepliesTillNow - 1);
		return getLastReply;
	}

	public String fetchesLastReply(Livi livi) {
		int size = fetchesLastReplyList(livi).size();
		String getLastReply = fetchesLastReplyList(livi).get(size - 1);
		//log.info("The reply being fetched is " + getLastReply);
		return getLastReply;
	}

	public List<String> fetchesLastReplyList(Livi livi) {
		List<String> lastReplyList = new ArrayList<String>();
		List<WebElement> lastMessageSentEle=fetchLastReplyReceivedListElement(livi);
		//wait.until(ExpectedConditions.visibilityOfAllElements(lastMessageSentEle));

		lastMessageSentEle.stream().forEach(lastReply -> {
			lastReplyList.add(lastReply.getText());
		});

		//log.info("In getLastReplyList,  The last reply from Livi was " + lastReplyList);
		return lastReplyList;
	}

	
	  public List<WebElement> fetchLastReplyReceivedListElement(Livi livi) { // int
	   List<WebElement> getLastReplyList =null; //log.info("After setting variable getLastReplyListElement");
	  getLastReplyList =lastMessageSentElement(livi).findElements(By.xpath(livi.allRepliesList));
	  return getLastReplyList; 
	  }
	 

	public HashMap<String, List<String>> fetchesLastInteraction(Livi livi) {
		return interactions;
	}
	
	public void storeLastInteraction(Livi livi) {
		interactions = new HashMap<String, List<String>>();
			wait.until(ExpectedConditions.visibilityOfAllElements(fetchLastReplyReceivedListElement(livi)));
		interactions.put(lastMessageSent(livi), fetchesLastReplyList(livi));
	}

	public HashMap<Integer, Map<String, String>> getAllInteractions(Livi livi) {
		List<WebElement> allReplies = livi.allRepliesByLiviTillNow;
		List<WebElement> allMessagesSent = livi.allMessagesSentByUserTillNow;

		for (int i = 0; i < allRepliesByLivi.size() - 1; i++) {
			Map<String, String> interactions = new HashMap<String, String>();

			interactions.put(allMessagesSent.get(i).getText(), allReplies.get(i).getText());
			allInteractions.put(i, interactions);
		}

		return allInteractions;

	}

	public String lastMessageSent(Livi livi) {
		//log.info("In lastMessageSent method");
		/*
		 * int totalMessagesSentTillNow = livi.allMessagesSentByUserTillNow.size();
		 * wait.until(
		 * ExpectedConditions.visibilityOf(livi.allMessagesSentByUserTillNow.get(
		 * totalMessagesSentTillNow - 1))); WebElement getLastMessageSent =
		 * livi.allMessagesSentByUserTillNow.get(totalMessagesSentTillNow - 1); String
		 * lastMessageSent = getLastMessageSent.getText();
		 */
		String lastMessageSent = lastMessageSentElement(livi).getText();
		return lastMessageSent;
	}
	

	public WebElement lastMessageSentElement(Livi livi) {
		//log.info("In lastMessageSentElement method");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalMessagesSentTillNow = livi.allMessagesSentByUserTillNow.size();
		wait.until(
				ExpectedConditions.visibilityOf(livi.allMessagesSentByUserTillNow.get(totalMessagesSentTillNow - 1)));
		//log.info("In lastMessageSentElement method,after wait until ");
		WebElement getLastMessageSentElement = livi.allMessagesSentByUserTillNow.get(totalMessagesSentTillNow - 1);
		return getLastMessageSentElement;
	}

	public WebElement getLatestCarousel(Livi livi) {
		wait.until(ExpectedConditions.visibilityOfAllElements(fetchLastReplyReceivedListElement(livi)));
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalCarouselsDisplayedTillNow = livi.allCarouselsDisplayedTillNow.size();
		WebElement latestCarousel = livi.allCarouselsDisplayedTillNow.get(totalCarouselsDisplayedTillNow - 1);
		return latestCarousel;
	}

	public boolean selectsApostle(Apostle selectApostle,Livi livi) {
		String apostleToBeSelected = selectApostle.toString();
		String chosenApostle = null;
		//List<WebElement> latestTestScenarios = fetchLatestTestBot(livi);
		//selectATestScenario(TestScenarioType.CAROUSEL, latestTestScenarios,livi);
		selectATestScenario(TestScenarioType.CAROUSEL,livi);
		WebElement Carousel = getLatestCarousel(livi);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(livi.navigateRight)));
		//log.info(" In Select Apostle, after wait for right navigator");
		List<WebElement> rightNavigators = Carousel.findElements(By.xpath(livi.navigateRight));
		int navCount = rightNavigators.size();
		WebElement rightNavigator = rightNavigators.get(navCount - 1);
		List<WebElement> allApostles = Carousel.findElements(By.xpath(livi.allApostleNames));
		for (WebElement element : allApostles) {
			String apostle = element.getText();
			if (apostle.equalsIgnoreCase(apostleToBeSelected)) {
				chosenApostle = apostle;
				String apostleLink = livi.pathBeforeName + chosenApostle + livi.pathAfterName;
				//log.info("Constructed Apostle Link path is " + apostleLink);
				if (element.findElements(By.xpath(apostleLink)).isEmpty()) {
					log.info("The chosen apostle " + chosenApostle + " doesn't have an associated link");
					return false;
				} else {
					element.findElement(By.xpath(apostleLink)).click();
					return true;
				}
			} else {
				wait.until(ExpectedConditions.elementToBeClickable(rightNavigator));
				rightNavigator.click();

			}
		}
		return false;
	}

	public HashSet<String> opensWebViewLinks(EnumSet<WebViewButtonsAndLinks> linksToBeOpened,Livi livi) {
		//List<WebElement> latestTestScenarios = fetchLatestTestBot(livi);
		HashSet<String> linksThatDidntOpenProperly = new HashSet<String>();
		//log.info("The Latest Test Scenario Webelement is " + latestTestScenarios.toString());
		//selectATestScenario(TestScenarioType.WEB_VIEW, latestTestScenarios,livi);
		selectATestScenario(TestScenarioType.WEB_VIEW,livi);
		wait.until(ExpectedConditions.visibilityOfAllElements(fetchLastReplyReceivedListElement(livi)));
		linksToBeOpened.stream().forEach(link -> {
			// String link=linkEnum.toString();
			log.info("The LINK to be opened now is " + link);
			switch (link) {
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
					mainWindowHandle = driver.get().getWindowHandle();
					String avaamoTitle=openAvaamo(livi);
			if(avaamoTitle.isBlank()) {
					linksThatDidntOpenProperly.add("Avaamo");
				}

				break;
			case THANKS:
				log.info("Not Implemented Yet!");
				break;
			case CALL:
				String browser=ConfigFileReader.browserName;
				//log.info("Current BROWSER is "+browser);
				if(browser.equalsIgnoreCase("firefox")) {
				driver.get().switchTo().window(mainWindowHandle);
				}
				else {
					switchBackToLiviFrame(livi);
				}
				livi.call.click();	
				Robot keyBoard;
				try {
					keyBoard = new Robot();
  if (isNewWindowOpen(driver, 20)) { 
				  Set<String> allWindows = driver.get().getWindowHandles();
					log.info("In CALL, All window handles are " + allWindows);
					allWindows.stream().forEach(windowHandle -> {
						if (!windowHandle.equals(mainWindowHandle)) {
							driver.get().switchTo().window(windowHandle);
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							driver.get().close();
						}
					});
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					keyBoard.delay(1000);
					keyBoard.keyPress(KeyEvent.VK_ALT);
					keyBoard.keyPress(KeyEvent.VK_TAB);
					keyBoard.keyPress(KeyEvent.VK_ENTER);
					driver.get().switchTo().window(mainWindowHandle);
					driver.get().switchTo().defaultContent();
					driver.get().switchTo().frame(livi.frame);
  log.info("Clicked on \"CALL\"");}
  else {
				  switchBackToLiviFrame(livi);
wait.until(ExpectedConditions.titleContains("Demo Web Channel"));
  log.info("Before Robot Keyboard");
  
// Since focus is on alert cancel by default, we only need to press enter key to
// close the alert.
keyBoard.delay(1000);
keyBoard.keyPress(KeyEvent.VK_ENTER);
//log.info("After Robot Keyboard Enter");
  }} catch (AWTException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
//log.info("Windows Alert Closed");
switchBackToLiviFrame(livi);
log.info("Clicked on \"CALL\"");
				
				break;
			case LOCATION:
				try {
					if (shareLocation(livi)) {
						log.info("Location Successfully Shared");
						// taskCount++;
					} else {
						linksThatDidntOpenProperly.add("Location");
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

		});
		return linksThatDidntOpenProperly;
	}

	/**
	 * @param livi
	 */
	public String openAvaamo(Livi livi) {
		log.info("Clicking on \"AVAAMO\"");
		livi.avaamo.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<String> allWindows = driver.get().getWindowHandles();
		allWindows.stream().forEach(windowHandle -> {
			if (!windowHandle.equals(mainWindowHandle)) {
				driver.get().switchTo().window(windowHandle);
			}
		});
		String title=driver.get().getTitle();
		allWindows = driver.get().getWindowHandles();
		return title;
	}

	public boolean isNewWindowOpen(ThreadLocal<WebDriver> driver, int timeout) {
		boolean flag = false;
		int counter = 0;
		while (!flag) {
			try {
				Set<String> winId = driver.get().getWindowHandles();
				if (winId.size() > 1) {
					flag = true;
					return flag;
				}
				Thread.sleep(1000);
				counter++;
				if (counter > timeout) {
					return flag;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return flag;
	}

	public void switchBackToLiviFrame(Livi livi) {
		driver.get().switchTo().window(mainWindowHandle);
		wait.pollingEvery(Duration.ofSeconds(2));
		try {
		driver.get().switchTo().frame(livi.frame);
		//log.info("In switchBackToLiviFrame, after switching Back to Frame");
		}
		catch(org.openqa.selenium.TimeoutException e)
		{
			//log.info("In switchBackToLiviFrame, catch of TimeoutException"  );
			//wait.until(ExpectedConditions.presenceOfElementLocated(By.name("avaamoIframe")));
			driver.get().switchTo().frame(livi.frame);
		}
	}

	public boolean shareLocation(Livi livi) throws InterruptedException {
		livi.location.click();
		wait.until(ExpectedConditions.visibilityOfAllElements(fetchLastReplyReceivedListElement(livi)));
		String responseToLocationShared = fetchesLastReply(livi);

		if (!responseToLocationShared.contains("title") || !responseToLocationShared.contains("lon")
				|| !responseToLocationShared.contains("lat") || !responseToLocationShared.contains("description")) {
			return false;
		}
		return true;
	}

	public boolean ifAlertPresentThenDismiss() {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.get().switchTo().alert().dismiss();
			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	} // isAlertPresent()

	public boolean ifAlertPresentThenAccept() {
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			driver.get().switchTo().alert().accept();

			return true;
		} // try
		catch (NoAlertPresentException Ex) {
			return false;
		} // catch
	}
}
