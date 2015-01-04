package sondes.scenarios.action;

import org.openqa.selenium.WebDriver;

import sondes.scenarios.ScenarioException;

public class VerifierTexteDansPageAction extends Action {
	protected String text;
	protected String erreur;
	
	public VerifierTexteDansPageAction(String text, String erreur) {
		this.text = text;
		this.erreur = erreur;
	}
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Verification Texte présent :  "+text);
		if(!driver.getPageSource().contains(text))
	    {
	    	throw new ScenarioException("ERREUR: " + erreur);
	    }
	}

}
