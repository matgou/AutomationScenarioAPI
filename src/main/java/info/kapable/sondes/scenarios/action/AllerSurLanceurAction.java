package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import org.openqa.selenium.WebDriver;

public class AllerSurLanceurAction extends Action {
	protected String lanceur_url = "file:///P:/Echanges/Chor�gie/mgoulin/public_html/index.html";
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Get : " + this.lanceur_url);
	    driver.get(this.lanceur_url);
	}

}
