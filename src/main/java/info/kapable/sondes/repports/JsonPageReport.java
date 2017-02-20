package info.kapable.sondes.repports;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonPageReport extends FreemarkerTemplateReport {

	public JsonPageReport(File file) {
		super();
		this.setFile(file);
		this.results = new ArrayList<ActionResult>();
		this.testInfo = new HashMap<String,String>();
		this.envVars = new HashMap<String, String>();
		this.setTemplateName("templateJson.ftlh");
	}
}
