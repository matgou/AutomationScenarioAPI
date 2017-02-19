package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import org.openqa.selenium.WebDriver;

public class VerifierTexteDansPageAction extends Action {
	protected String text;
	protected String erreur;

	public VerifierTexteDansPageAction(String text, String erreur) {
		this.text = text;
		this.erreur = erreur;
	}

	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Verification Texte prï¿½sent :  " + text);
		if(driver.getPageSource() == null) {
			throw new ScenarioException("ERREUR: Pas de données dans la derniere page");
		}
		if (!driver.getPageSource().contains(text)) {
			throw new ScenarioException("ERREUR: " + erreur);
		}
	}

	@Override
	public String getDescription() {
		return "Verifier que le texte suivant est sur la page : " + this.text;
	}

}
