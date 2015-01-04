package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiverFenetreSuivanteAction extends Action {
	
	@Override
	public void executeAction(WebDriver driver) {
		this.logEvent("Firefox", "Changement de fenetre");
		Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
        Iterator<String> itererator = windowId.iterator();   

        itererator.next();
        String  newAdwinID = itererator.next();

        driver.switchTo().window(newAdwinID);
	}
}
