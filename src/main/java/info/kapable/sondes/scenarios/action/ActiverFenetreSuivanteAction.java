package info.kapable.sondes.scenarios.action;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class ActiverFenetreSuivanteAction extends Action {

	@Override
	public void executeAction(WebDriver driver) {
		this.logEvent("Firefox", "Changement de fenetre");
		Set<String> windowId = driver.getWindowHandles(); // get window id of
															// current window
		Iterator<String> itererator = windowId.iterator();

		itererator.next();
		String newAdwinID = itererator.next();

		driver.switchTo().window(newAdwinID);
	}

	@Override
	public String getDescription() {
		return "Changer de fenetre active";
	}
}
