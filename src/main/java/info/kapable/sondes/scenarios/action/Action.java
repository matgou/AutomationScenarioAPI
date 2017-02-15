package info.kapable.sondes.scenarios.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import info.kapable.sondes.scenarios.ScenarioException;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Action {
	private final Logger logger = LoggerFactory
			.getLogger(ScenarioException.class);

	protected void logEvent(String type, String trace) {
		this.logger.info(type + " : " + trace);
	}

	public abstract void executeAction(WebDriver driver)
			throws ScenarioException;

	/*
	 * Execute action and put time in file
	 */
	public void executeAction(WebDriver driver, File outputStatisticFile)
			throws ScenarioException {
		if (outputStatisticFile == null) {
			this.executeAction(driver);
		} else {
			try {
				FileWriter fstream = new FileWriter(outputStatisticFile, true);
				BufferedWriter out = new BufferedWriter(fstream);
				long startTime = System.currentTimeMillis();
				this.executeAction(driver);
				long estimatedTime = System.currentTimeMillis() - startTime;

				out.write(this.getClass().getName() + ":" + estimatedTime + "\n");
				out.flush();
				fstream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // true tells to append data.
		}
	}
}
