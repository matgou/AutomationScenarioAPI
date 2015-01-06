package info.kapable.sondes.scenarios.action;

import info.kapable.sondes.scenarios.ScenarioException;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class VerifierImageDesktopAction extends Action {
	protected String imgPath;
	protected String erreur;
	
	public VerifierImageDesktopAction(String imgPath, String erreur) {
		this.imgPath = imgPath;
		this.erreur = erreur;
	}
	
	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		this.logEvent("Desktop", "Verification image présent ");

        ScreenRegion s = new DesktopScreenRegion();

        URL imageURL;
		try {
			imageURL = new URL(imgPath);     
	        Target imageTarget = new ImageTarget(imageURL);
	        ScreenRegion r = s.wait(imageTarget,1);
	        if(r == null)
		    {
		    	throw new ScenarioException("ERREUR: " + erreur);
		    }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
	}

}
