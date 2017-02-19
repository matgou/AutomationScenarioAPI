package info.kapable.sondes.scenarios.action;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import info.kapable.sondes.scenarios.ScenarioException;

public class CliquerDesktopAction extends Action {
	private String imgPath;

	public CliquerDesktopAction(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		// TODO Auto-generated method stub
		ScreenRegion s = new DesktopScreenRegion();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		URL imageURL;
		try {
			imageURL = new URL(imgPath);
			Target imageTarget = new ImageTarget(imageURL);
			ScreenRegion r = s.wait(imageTarget, 1);
			// Click the center of the found target
			Mouse mouse = new DesktopMouse();
			this.logEvent("Desktop", "Click ");
			mouse.click(r.getCenter());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getDescription() {
		return "Cliquer sur l'image : <img src='" + this.imgPath + "'></img>";
	}

}
