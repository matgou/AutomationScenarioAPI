package sondes.scenarios.action;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sondes.scenarios.ScenarioException;

public abstract class Action {
	private final Logger logger = LoggerFactory.getLogger(ScenarioException.class);
	
	protected void logEvent(String type, String trace) {
		this.logger.trace(type + " : " + trace);
	}
	public abstract void executeAction(WebDriver driver) throws ScenarioException ;
}
