package info.kapable.sondes.repports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import info.kapable.sondes.scenarios.ScenarioException;
import info.kapable.sondes.scenarios.action.Action;
import info.kapable.sondes.scenarios.action.ScreenshotAction;

public abstract class FreemarkerTemplateReport extends Report {

	private File file;
	protected List<ActionResult> results;
	protected Map<String, String> envVars;
	private String templateName;
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	protected Map<String, String> testInfo;

	public Map<String, String> getEnvVars() {
		return envVars;
	}

	public List<ActionResult> getResults() {
		return results;
	}

	public void setResults(List<ActionResult> results) {
		this.results = results;
	}

	public FreemarkerTemplateReport() {
		super();
	}

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

	public String getTemplatePath() {
		return this.getClass().getClassLoader().getResource("templates/").getPath();
	}

	@Override
	public void finishError(WebDriver driver, ScenarioException e, ActionResult result) {
		result.finishKO(e);
		this.addActionResult(result);
		
		String outputDir = this.getFile().getAbsoluteFile().getParentFile().getAbsolutePath();
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
			Writer out = new OutputStreamWriter(new FileOutputStream(this.getFile()));
			temp.process(this, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

}