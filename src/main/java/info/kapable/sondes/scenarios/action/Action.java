package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Action {
	private final Logger logger = LoggerFactory.getLogger(ScenarioException.class);

	protected void logEvent(String type, String trace) {
		this.logger.info(type + " : " + trace);
	}

	public abstract void executeAction(WebDriver driver) throws ScenarioException;
}
