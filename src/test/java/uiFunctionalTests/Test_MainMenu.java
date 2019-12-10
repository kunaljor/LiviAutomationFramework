package uiFunctionalTests;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

/**
 * 
 * @author Kunal Jor
 * 
 * 
 */
//@Listeners(utilities.TestListener.class)
public class Test_MainMenu extends BaseTest {
	//private Logger log = LogManager.getLogger(Test_MainMenu.class.getName());
	String replies = "";

	/**
	 * Verify Main Menu Selection
	 */
	@Test(priority = 4) // ,retryAnalyzer=RetryAnalyzer.class)
	public void verifyMainMenu() {
		James.opensMainMenu(livi);

		List<String> lastReplyList = James.fetchesLastReplyList(livi);
		// log.info("The reply list fetched is "+ lastReplyList);
		lastReplyList.stream().forEach(reply -> {
			replies += reply;
		});
		sAssert.assertTrue(replies.contains(
				"I can help you search for healthcare providers and locations in a specialty, look for specific doctors or help you discover interesting information about UCHealth."),
				"The expected response wan't returned to the request \"Main Menu\"");
		if (sAssert != null)
			sAssert.assertAll();
	}

}
