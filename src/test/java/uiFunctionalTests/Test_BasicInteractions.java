package uiFunctionalTests;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataProviders.ExcelFileReader;

/**
 * 
 * @author Kunal Jor
 * 
 * 
 */
//@Listeners(utilities.TestListener.class)
public class Test_BasicInteractions extends BaseTest {
	//private Logger log = LogManager.getLogger(Test_BasicInteractions.class.getName());
	HashMap<String, List<String>> lastInteraction;
	Method method;
	List<String> liviReplies=null;
	/**
	 * Test for verifying that two way interactions with Livi are working
	 */
	@Test(priority=1,dataProvider = "messagesForLivi") // ,retryAnalyzer =RetryAnalyzer.class)
	public void interactWithLivi(String message) {
		James.chatsWithLivi(message,livi);
		lastInteraction = James.fetchesLastInteraction(livi);
		lastInteraction.entrySet().forEach(interaction -> {
			logger.info("User message to Livi - " + interaction.getKey());
			liviReplies=interaction.getValue();
			logger.info("Livi's reply to message - ");
			interaction.getValue().stream().forEach(reply -> {
				logger.info(reply);
			});
			
			//log.info("User message to Livi - " + interaction.getKey());
			//log.info("Livi's reply to message - " + interaction.getValue());
		});
		sAssert.assertFalse(noReplyByLiviToMessagesSentByJames(liviReplies) ,"No reply by Livi to user message. Some issue here");
		if (sAssert != null) sAssert.assertAll();
	}


	@DataProvider(name = "messagesForLivi")
	public Object[][] messagesForLivi() throws Exception {
		Object[][] testObjArray = ExcelFileReader
				.getTableArray(System.getProperty("user.dir") + "//src//test//resources//TestData.xlsx", "Messages");
		return (testObjArray);

	}
	
	private boolean noReplyByLiviToMessagesSentByJames(List<String> replies) {
		if(java.util.Objects.equals(replies, null)) return true;
		return false;	
	}

}
