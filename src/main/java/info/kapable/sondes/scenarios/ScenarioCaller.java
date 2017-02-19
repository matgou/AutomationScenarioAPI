package info.kapable.sondes.scenarios;

import info.kapable.sondes.drivers.CurlDriver;
import info.kapable.sondes.scenarios.action.Action;
import info.kapable.sondes.repports.ActionResult;
import info.kapable.sondes.repports.Report;
import info.kapable.sondes.scenarios.action.ScreenshotAction;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScenarioCaller {
	protected static WebDriver driver;
	protected ArrayList<Action> actions;

	private static final Logger logger = LoggerFactory.getLogger(ScenarioCaller.class);
	public static final int FIREFOX = 1;
	public static final int IE = 2;
	public static final int DIRECT = 3;
	
	
	protected String outputDir = "./";
	private Map<String, Object> EnvVarsBag;
	private int navigator = 1;
	private Report outputReport = null;
	private Map<String, String> testDescription;
	private Map<String, String> envVarsUsed;
	
	public Report getOutputReport() {
		return outputReport;
	}

	public void setOutputReport(Report outputReport) {
		this.outputReport = outputReport;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public ScenarioCaller(String xml_cmd) throws ScenarioParsingException {
		EnvVarsBag = new HashMap<String, Object>();
		this.actions = BuildAction(xml_cmd);
	}

	public ScenarioCaller(String xml_cmd, Map<String,Object> EnvVarsBag, int navigator) throws ScenarioParsingException {
		this.EnvVarsBag = EnvVarsBag;
		this.navigator = navigator;
		this.actions = BuildAction(xml_cmd);
	}
	
	public ScenarioCaller(String xml_path, Map<String, Object> EnvVarsBag, String encoding) throws ScenarioParsingException {
		this(xml_path, EnvVarsBag, encoding, ScenarioCaller.FIREFOX);
	}
	
	public ScenarioCaller(String xml_path, Map<String, Object> EnvVarsBag, String encoding, int navigator) throws ScenarioParsingException {
		byte[] encoded;
		try {
			try {
				encoded = Files.readAllBytes(Paths.get(xml_path));
			} catch (NoSuchFileException e) {
				throw new ScenarioParsingException("Le fichier scenario est introuvable.");
			}
			String xml_cmd = new String(encoded, encoding);
			
			this.EnvVarsBag = EnvVarsBag;
			this.actions = BuildAction(xml_cmd);
			this.navigator = navigator;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ScenarioCaller(String xml_path, String encoding) throws ScenarioParsingException {
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
	
	private Action xmlToAction(Element action) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchMethodException, ClassNotFoundException, ScenarioParsingException
	{
		
		String className = action.getAttribute("class").getValue();
		Class ActionClass = Class.forName(className);
		List<Object> params = new ArrayList<Object>();	
		List<Element> paramsXML = action.getChildren("param");
		for (Element param : paramsXML) {
			if (param.getAttribute("type") == null) {
				if(param.getAttribute("value") != null) {
					params.add(param.getAttribute("value").getValue());
				} else {
					XMLOutputter outp = new XMLOutputter();
					String s = outp.outputString(param.getChildren());
					params.add(s);
				}
			} else {
				String type = param.getAttribute("type").getValue();
				if (type.contentEquals("By")) {
					String selector = param.getAttribute("selector").getValue();
					String value = param.getAttribute("value").getValue();
					params.add(By.class.getMethod(selector, String.class).invoke(By.class, value));
				} else if (type.contentEquals("EnvVar")) {
					String key = param.getAttribute("value").getValue();
					Object value = this.EnvVarsBag.get(key);
					this.envVarsUsed.put(key, (String) value);
					if(value == null) {
						throw new ScenarioParsingException("No environement variable (" + param.getAttribute("value").getValue() + ") found!");
					}
					params.add(value);
				} else if (type.contentEquals("raw")) {
					XMLOutputter outp = new XMLOutputter();
					String s = outp.outputString(param.getChildren());
					params.add(s);
				}
				if (type.contentEquals("then") || type.contentEquals("else")) {
					List<Action> subActions = new ArrayList<Action>();
					List<Element> subActionsXML = param.getChildren("Action");
					for(Element subActionXML : subActionsXML) {
						subActions.add(xmlToAction(subActionXML));
					}
					params.add(subActions);
				}
			}
		}
		return (Action) ActionClass.getConstructors()[0].newInstance(params.toArray());	
	}
	
	private ArrayList<Action> BuildAction(String xml_cmd) throws ScenarioParsingException {
		// TODO Auto-generated method stub
		ArrayList<Action> actions = new ArrayList<Action>();
		this.testDescription = new HashMap<String,String>();

		this.envVarsUsed = new HashMap<String, String>();
		
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
			for (Element action : actionsXML) {
				Action actionToAdd = xmlToAction(action);
				actions.add(actionToAdd);
			}

			List<Element> descriptionXML = racine.getChildren("description");
			if(descriptionXML.size() > 0) {
				Element descriptions = descriptionXML.get(0);
				Iterator<Element> descriptionIterator = descriptions.getChildren().iterator();
				while (descriptionIterator.hasNext()) {
					Element descriptionElement = descriptionIterator.next();
					String name = descriptionElement.getName();
					String value = descriptionElement.getText();
					this.testDescription.put(name, value);
				}
			}
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ScenarioParsingException(e.getCause());
		}

		return actions;
	}

	public int launchTest() throws UnsuportedNavigatorException {
		return launchTest(null);
	}
	
	public int launchTest(String execPath) throws UnsuportedNavigatorException {
		int returnCode = 0;
		WebDriver driver = null;
		if(this.navigator == ScenarioCaller.FIREFOX) {
			FirefoxBinary binary;
			FirefoxProfile profile = new FirefoxProfile();
			
			logger.info("Firefox : Lancement du navigateur");
			profile.setAssumeUntrustedCertificateIssuer(false);
			if(execPath == null) {
				driver = new FirefoxDriver(profile);
			} else {
				binary = new FirefoxBinary(new File(execPath));
				driver = new FirefoxDriver(binary, profile);
			}
		} else if (this.navigator == ScenarioCaller.IE) {
			if(execPath != null) {
				File file = new File(execPath);
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			}
			driver = new InternetExplorerDriver();
		} else if (this.navigator == ScenarioCaller.DIRECT) {
			driver = new CurlDriver();
		} else throw new UnsuportedNavigatorException();
				
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		if(this.outputReport != null) {
			this.outputReport.setTestInfo(this.testDescription);
			this.outputReport.setEnvVars(this.envVarsUsed);
		}
		for (Action action : actions) {
			ActionResult result = new ActionResult();
			result.setDescription(action.getDescription());
			try {
				action.executeAction(driver);
				result.finishOK();
			} catch (ScenarioException e) {
				
				if(this.outputReport != null)
					this.outputReport.finishError(driver, e, result);
				
				returnCode = 2;
				break;
			}
			if(this.outputReport != null)
				this.outputReport.addActionResult(result);
		}
		if(this.outputReport != null)
			this.outputReport.finishOK();
		try {
			driver.close();
		} catch (NoSuchWindowException e) {

		}
		driver.quit();
		return returnCode;
	}

	/**
	 * @return the envVarsUsed
	 */
	public Map<String, String> getEnvVarsUsed() {
		return envVarsUsed;
	}

	/**
	 * @param envVarsUsed the envVarsUsed to set
	 */
	public void setEnvVarsUsed(Map<String, String> envVarsUsed) {
		this.envVarsUsed = envVarsUsed;
	}
}
