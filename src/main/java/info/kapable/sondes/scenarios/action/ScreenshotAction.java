package info.kapable.sondes.scenarios.action;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

public class ScreenshotAction extends Action {
	private String name;

	public ScreenshotAction(String name) {
		this.name = name;
	}

	private void takePictureOfError(String Name) throws IOException,
			AWTException {
		new File("Errors").mkdir();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRect);
		ImageIO.write(image, "png", new File(Name));
	}

	@Override
	public void executeAction(WebDriver driver) {
		this.logEvent("Screen", "Take screenshoot : " + this.name);
		try {
			this.takePictureOfError(this.name);
		} catch (IOException e) {
			this.logEvent("Error", "Error when saving image " + this.name + " : " + e.getMessage());
		} catch (AWTException e) { 
			this.logEvent("Error", "Error when making the screenshoot : " + e.getMessage());
		}
	}
}
