package sondes.scenarios.action;

import org.openqa.selenium.WebDriver;

import sondes.scenarios.ScenarioException;

public class VerifierTitreAction extends Action {
	protected String text;
	protected String erreur;
	
	public VerifierTitreAction(String text, String erreur) {
		this.text = text;
		this.erreur = erreur;
	}
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Verification Titre présent :  "+text);
        if(!driver.getTitle().contains(text)) {
        	throw new ScenarioException(erreur);
        }
	}
}
