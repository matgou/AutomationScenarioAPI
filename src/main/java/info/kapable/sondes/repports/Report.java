package info.kapable.sondes.repports;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import info.kapable.sondes.scenarios.ScenarioException;

public abstract class Report {
	/**
	 * When Action is finish add an result to a repport
	 * 
	 * @param result
	 */
	public abstract void addActionResult(ActionResult result);

	/**
	 * When error occurs durring the scenario execute this method to close the repport
	 * 
	 * @param driver
	 * @param e
	 * @param result
	 */
	public abstract void finishError(WebDriver driver, ScenarioException e, ActionResult result);

	/**
	 * When scenario is finish without error execute this method to close the repport 
	 */
	public abstract void finishOK();

	/**
	 * 
	 * @param testDescription
	 */
	public abstract void setTestInfo(Map<String, String> testDescription);
}
