package info.kapable.sondes.scenarios.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SwitchToIFrame extends Action {
	protected By by;

	public SwitchToIFrame(By by) {
		this.by = by;
	}

	@Override
	public void executeAction(WebDriver driver) {
		this.logEvent("Firefox", "Switch to Iframe : ");
		WebElement iframeElem = driver.findElement(this.by);
		driver.switchTo().frame(iframeElem);
	}
}
