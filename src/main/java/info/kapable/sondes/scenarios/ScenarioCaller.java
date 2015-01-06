package info.kapable.sondes.scenarios;

import info.kapable.sondes.scenarios.action.Action;
import info.kapable.sondes.scenarios.action.ScreenshotAction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScenarioCaller {
	protected static WebDriver driver;
	protected ArrayList<Action> actions;
	
	private static final Logger logger = LoggerFactory.getLogger(ScenarioCaller.class);
	 
	protected String outputDir = "./";
	private HashMap<String, Object> EnvVarsBag;
	
	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public ScenarioCaller ( String xml_cmd) {
		EnvVarsBag = new HashMap<String, Object>();
		this.actions = BuildAction(xml_cmd); 
	}
	public ScenarioCaller ( String xml_cmd, HashMap<String, Object> EnvVarsBag) {
		this.EnvVarsBag = EnvVarsBag;
		this.actions = BuildAction(xml_cmd); 
	}
	
	public ScenarioCaller ( String xml_path, String encoding) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(xml_path));
			String xml_cmd = new String(encoded, encoding);

			this.actions = BuildAction(xml_cmd); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<Action> BuildAction(String xml_cmd) {
		// TODO Auto-generated method stub
		ArrayList<Action> actions = new ArrayList<Action>();
		InputStream stream;
		try {
			stream = new ByteArrayInputStream(xml_cmd.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return actions;
		}
	    SAXBuilder sxb = new SAXBuilder();
	      try {
			org.jdom.Document document = sxb.build(stream);
			Element racine = document.getRootElement();
			List<Element> actionsXML = racine.getChildren("Action");
			for(Element action: actionsXML) {
				String className = action.getAttribute("class").getValue();
				Class ActionClass = Class.forName(className);
				List<Element> paramsXML = action.getChildren("param");
				List<Object> params = new ArrayList<Object>();
				for(Element param: paramsXML) {
					if(param.getAttribute("type") == null) {
						params.add(param.getAttribute("value").getValue());
					}else {
						String type= param.getAttribute("type").getValue();
						if(type.contentEquals("By")) {
							String selector = param.getAttribute("selector").getValue();
							String value = param.getAttribute("value").getValue();
							params.add(By.class.getMethod(selector, String.class).invoke(By.class, value));
						} else if(type.contentEquals("EnvVar")) {
							params.add(this.EnvVarsBag.get(param.getAttribute("value").getValue()));
						}
					}
				}
				actions.add((Action) ActionClass.getConstructors()[0].newInstance(params.toArray()));
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return actions;
	}
	
	public int launchTest() {
		int returnCode = 0;
		logger.info("Firefox : Lancement du navigateur");
		FirefoxProfile profile=new FirefoxProfile();
		profile.setAssumeUntrustedCertificateIssuer(false);
		FirefoxDriver driver=new FirefoxDriver(profile);
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    for(Action action: actions) {
	    	try {
				action.executeAction(driver);
			} catch (ScenarioException e) {
				// TODO Auto-generated catch block
				Action screenShot = new ScreenshotAction(outputDir + System.getProperty("file.separator") + "erreur.png");
				try {
					screenShot.executeAction(driver);
				} catch (ScenarioException e1) {
				}
				returnCode = 2;
				break;
			}
	    }
		
	    try {
	    	driver.close();
	    } catch (NoSuchWindowException e) {
	    	
	    }
	    driver.quit();
	    return returnCode;
	}
}
