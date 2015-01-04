package sondes.scenarios.action;

import org.openqa.selenium.WebDriver;

import sondes.scenarios.ScenarioException;

public class AllerSurLanceurAction extends Action {
	protected String lanceur_url = "file:///P:/Echanges/Chorégie/mgoulin/public_html/index.html";
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Get : " + this.lanceur_url);
	    driver.get(this.lanceur_url);
	}

}
