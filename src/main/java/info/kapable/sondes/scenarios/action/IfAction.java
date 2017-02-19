package info.kapable.sondes.scenarios.action;

import java.util.List;

import org.openqa.selenium.WebDriver;

import info.kapable.sondes.scenarios.ScenarioException;

public class IfAction extends Action {
	private List<Action> thenAction;
	private List<Action> elseAction;
	private String textAttendu;

	public IfAction(String textAttendu, List<Action> thenAction, List<Action> elseAction) {
		this.elseAction = elseAction;
		this.thenAction = thenAction;
		this.textAttendu = textAttendu;
	}

	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Firefox", "Verification Texte prï¿½sent :  " + this.textAttendu);
		if (driver.getPageSource().contains(this.textAttendu)) {
			this.logEvent("Firefox", "Found");
			for (Action action : this.thenAction) {
				action.executeAction(driver);
			}
		} else {
			this.logEvent("Firefox", "Not found");
			for (Action action : this.elseAction) {
				action.executeAction(driver);
			}
		}
	}

	@Override
	public String getDescription() {
		return "Execution conditionelle si " + this.textAttendu;
	}

}
