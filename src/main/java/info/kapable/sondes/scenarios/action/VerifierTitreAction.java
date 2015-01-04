package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import org.openqa.selenium.WebDriver;

public class VerifierTitreAction extends Action {
	protected String text;
	protected String erreur;
	
	public VerifierTitreAction(String text, String erreur) {
		this.text = text;
		this.erreur = erreur;
	}
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Verification Titre pr�sent :  "+text);
        if(!driver.getTitle().contains(text)) {
        	throw new ScenarioException(erreur);
        }
	}
}
