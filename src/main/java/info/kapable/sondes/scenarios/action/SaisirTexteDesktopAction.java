package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;

public class SaisirTexteDesktopAction extends Action {
	private String imgPath;
	private String texte;

	public SaisirTexteDesktopAction(String imgPath, String texte) {
		this.imgPath = imgPath;
		this.texte = texte;
	}

	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		// TODO Auto-generated method stub
		ScreenRegion s = new DesktopScreenRegion();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		URL imageURL;
		try {
			imageURL = new URL(imgPath);
			Target imageTarget = new ImageTarget(imageURL);
			ScreenRegion r = s.wait(imageTarget, 1);
			// Click the center of the found target
			Mouse mouse = new DesktopMouse();
			mouse.click(r.getCenter());

			Keyboard keyboard = new DesktopKeyboard();
			this.logEvent("Desktop", "Saisie Texte : " + texte);

			char[] chaine = texte.toCharArray();
			for (int i = 0; i < chaine.length; i++) {
				char c = chaine[i];

				if (Character.isDigit(c)) {
					int key = Integer.parseInt(Character.toString(c));
					keyboard.keyDown(KeyEvent.VK_SHIFT);
					int keyEvent = KeyEvent.VK_0;
					switch (key) {
					case 0:
						keyEvent = KeyEvent.VK_0;
						break;
					case 1:
						keyEvent = KeyEvent.VK_1;
						break;

					case 2:
						keyEvent = KeyEvent.VK_2;
						break;

					case 3:
						keyEvent = KeyEvent.VK_3;
						break;

					case 4:
						keyEvent = KeyEvent.VK_4;
						break;

					case 5:
						keyEvent = KeyEvent.VK_5;
						break;

					case 6:
						keyEvent = KeyEvent.VK_6;
						break;

					case 7:
						keyEvent = KeyEvent.VK_7;
						break;

					case 8:
						keyEvent = KeyEvent.VK_8;
						break;

					case 9:
						keyEvent = KeyEvent.VK_9;
						break;
					}

					keyboard.keyDown(keyEvent);
					keyboard.keyUp(keyEvent);
					keyboard.keyUp(KeyEvent.VK_SHIFT);
				} else {
					keyboard.type(Character.toString(c));
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
