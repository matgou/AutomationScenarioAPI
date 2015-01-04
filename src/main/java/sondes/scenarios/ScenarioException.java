package sondes.scenarios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScenarioException extends Exception {

	private String erreur;
	private static final Logger logger = LoggerFactory.getLogger(ScenarioException.class);
	
	public ScenarioException(String erreur) {
		// TODO Auto-generated constructor stub
		super();
		this.erreur = erreur;
		logger.trace("Scenario : ERREUR: " + erreur);
	}

	public String getErreur() {
		return erreur;
	}

	public void setErreur(String erreur) {
		this.erreur = erreur;
	}

}
