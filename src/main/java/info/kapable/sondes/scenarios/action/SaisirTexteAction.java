package info.kapable.sondes.scenarios.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SaisirTexteAction extends Action {
	protected String text;
	protected By by;
	
	public SaisirTexteAction(By by, String text) {
		this.text=text;
		this.by = by;
	}
	@Override
	public void executeAction(WebDriver driver) {
		this.logEvent("Firefox", "Saisie Texte : " + text);
	    WebElement textElem = driver.findElement(by);
	    textElem.clear();
	    textElem.sendKeys(text);

	}
}
