package info.kapable.sondes.drivers;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver.ImeHandler;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.logging.Logs;

public class CurlDriverOptions implements Options {

	private Timeouts timeouts;

	public CurlDriverOptions() {
		super();
		this.timeouts = new CurlDriverTimeouts();
	}

	@Override
	public void addCookie(Cookie arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllCookies() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCookie(Cookie arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCookieNamed(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cookie getCookieNamed(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Cookie> getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImeHandler ime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logs logs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timeouts timeouts() {
		return this.timeouts;
	}

	@Override
	public Window window() {
		// TODO Auto-generated method stub
		return null;
	}

}
