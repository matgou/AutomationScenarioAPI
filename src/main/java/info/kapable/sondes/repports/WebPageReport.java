package info.kapable.sondes.repports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import info.kapable.sondes.scenarios.ScenarioException;
import info.kapable.sondes.scenarios.action.Action;
import info.kapable.sondes.scenarios.action.ScreenshotAction;

public class WebPageReport extends Report {

	private File folder;
	private String filename;
	private List<ActionResult> results;
	private Map<String, String> envVars;
	
	public Map<String, String> getEnvVars() {
		return envVars;
	}

	public List<ActionResult> getResults() {
		return results;
	}

	public void setResults(List<ActionResult> results) {
		this.results = results;
	}

	private String templateName = "template.ftlh";
	private Map<String, String> testInfo;

	public Map<String, String> getTestInfo() {
		return testInfo;
	}

	public void setTestInfo(Map<String, String> testInfo) {
		this.testInfo = testInfo;
	}
	
	public void putInfo(String param, String value) {
		this.testInfo.put(param, value);
	}
	
	public void setEnvVars(Map<String, String> envVars) {
		this.envVars = envVars;
	}

	public WebPageReport(File folder, String filename) {
		super();
		this.setFolder(folder);
		this.setFilename(filename);
		this.results = new ArrayList<ActionResult>();
		this.testInfo = new HashMap<String,String>();
		this.envVars = new HashMap<String, String>();
	}

	@Override
	public void addActionResult(ActionResult result) {
		this.results.add(result);
	}

	public Long getStartTime() {
		return this.results.get(0).startTimeMillis;
	}
	
	public String getStartTimeStr() {
		Date date=new Date(this.getStartTime());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(date);
	}
	
	public String getEndTimeStr() {
		Date date=new Date(this.getEndTime());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return format.format(date);
	}
	
	public Long getEndTime() {
		return this.results.get(this.results.size() - 1).endTimeMillis;
	}
	
	public Long getDuration() {
		return this.getEndTime() - this.getStartTime();
	}
	
	public String getDurationStr() {
		Long minutes = this.getDuration() / (60 * 1000);
		Long secondes = (this.getDuration() - minutes * (60 * 1000))/ (1000);
		return minutes + " minutes, " + secondes + " secondes";
	}
	
	public boolean getOK() {
		Iterator<ActionResult> it = this.results.iterator();
		while(it.hasNext()) {
			ActionResult res = it.next();
			if(res.getStatus() != ActionResult.END_OK) {
				return false;
			}
		}
		return true;
	}
	/**
	 * @return the folder
	 */
	public File getFolder() {
		return folder;
	}

	/**
	 * @param folder
	 *            the folder to set
	 */
	public void setFolder(File folder) {
		this.folder = folder;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getTemplatePath() {
		return this.getClass().getClassLoader().getResource("templates/").getPath();
	}
	
	@Override
	public void finishError(WebDriver driver, ScenarioException e, ActionResult result) {
		result.finishKO(e);
		this.addActionResult(result);
		String outputDir = folder.getAbsolutePath();
		Action screenShot = new ScreenshotAction(outputDir + System.getProperty("file.separator") + "erreur.png");
		try {
			screenShot.executeAction(driver);
		} catch (ScenarioException e1) {
		}
		this.process();
	}

	@Override
	public void finishOK() {
		this.process();
	}

	/**
	 * Generate the HTML file from result
	 */
	private void process() {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		Template temp;
		try {
			FileTemplateLoader ftl1 = new FileTemplateLoader(new File(this.getTemplatePath()));
			cfg.setTemplateLoader(ftl1);
			temp = cfg.getTemplate(this.templateName);
			Writer out = new OutputStreamWriter(new FileOutputStream(this.getFolder() + System.getProperty("file.separator") + this.filename + ".html"));
			temp.process(this, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
