package info.kapable.sondes.repports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import info.kapable.sondes.scenarios.ScenarioException;

public class MultiOutputReport extends Report {
	
	private List<Report> reports;
	
	/**
	 * Constructor init reports
	 */
	public MultiOutputReport() {
		super();
		this.setReports(new ArrayList<Report>());
	}
	
	/**
	 * When Action is finish add an result to a repport
	 * 
	 * @param result
	 */
	public void addActionResult(ActionResult result) {
		for(Report r: this.reports) {
			r.addActionResult(result);
		}
	}

	/**
	 * When error occurs durring the scenario execute this method to close the repport
	 * 
	 * @param driver
	 * @param e
	 * @param result
	 */
	public void finishError(WebDriver driver, ScenarioException e, ActionResult result) {
		for(Report r: this.reports) {
			r.finishError(driver, e, result);
		}
	}

	/**
	 * When scenario is finish without error execute this method to close the repport 
	 */
	public void finishOK() {
		for(Report r: this.reports) {
			r.finishOK();
		}
	}

	/**
	 * 
	 * @param testDescription
	 */
	public void setTestInfo(Map<String, String> testDescription) {
		for(Report r: this.reports) {
			r.setTestInfo(testDescription);
		}
	}
	
	/**
	 * 
	 * 
	 */
	public void setEnvVars(Map<String, String> envVars) {
		for(Report r: this.reports) {
			r.setEnvVars(envVars);
		}
	}

	public void addOutputReport(Report outputReport) {
		this.reports.add(outputReport);
	}

	/**
	 * @return the reports
	 */
	public List<Report> getReports() {
		return reports;
	}

	/**
	 * @param reports the reports to set
	 */
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
}