package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.drivers.CurlDriver;
import info.kapable.sondes.drivers.CurlPostRequestWebElement;
import info.kapable.sondes.scenarios.ScenarioException;
import info.kapable.sondes.scenarios.UnsuportedNavigatorException;

import java.net.SocketTimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DoPostAction extends Action {
	private String url;
	private String raw;
	
	public DoPostAction(String url, String raw) {
		super();
		this.url = url;
		this.raw = raw;
	}

	public String getDescription() {
		return "Execute une requette HTTP Post sur l'url : " + this.url;
	}
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		try {
			if(driver.getClass().equals(CurlDriver.class)) {
				CurlDriver curlDriver = (CurlDriver) driver;
				WebElement element = new CurlPostRequestWebElement(curlDriver, url, raw);
				element.submit();
			} else {
				throw new UnsuportedNavigatorException();
			}
		} catch (UnsuportedNavigatorException e) {
			e.printStackTrace();
			throw new ScenarioException("Le navigateur choisi ne peux pas executer l'action 'doPost'");
		}
	}

}
