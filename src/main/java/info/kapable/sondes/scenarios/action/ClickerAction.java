package info.kapable.sondes.scenarios.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickerAction extends Action {

	protected By by;

	public ClickerAction(By by) {
		this.by = by;
	}

	@Override
	public void executeAction(WebDriver driver) {
		this.logEvent("Firefox", "Click ");
		WebElement btn = driver.findElement(by);
		btn.click();
	}

	@Override
	public String getDescription() {
		return "Cliquer sur l'element : " + this.by.toString();
	}

}
