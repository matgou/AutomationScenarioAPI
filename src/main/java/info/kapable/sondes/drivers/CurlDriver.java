package info.kapable.sondes.drivers;

import info.kapable.sondes.scenarios.ScenarioException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CurlDriver implements WebDriver {
	private final Logger logger = LoggerFactory
			.getLogger(CurlDriver.class);

	private Options options;

	private HttpResponse response;

	private long elapsedTime;

	private int status;

	private String responseData;

	private boolean ok;

	public CurlDriver() {
		super();
		this.options = new CurlDriverOptions();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public WebElement findElement(By arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void get(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPageSource() {
		return this.responseData;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Options manage() {
		
		return this.options;
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub

	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void post(String url, String raw) {
		// TODO Auto-generated method stub

		HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost(url);
	    try {
			post.setEntity(new StringEntity(raw));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	    logger.info("Post : " + url);
	    logger.info("Data : " + raw);
	    long startTime = System.currentTimeMillis();
		try {
			this.response = client.execute(post);
			long elapsedTime = System.currentTimeMillis() - startTime;
			logger.info("WebServiceCaller : http request/response time : " + elapsedTime + "ms");
			this.elapsedTime = elapsedTime;
			this.status = response.getStatusLine().getStatusCode();
		    logger.info("ResponseStatus : " + status);
			if(status == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    
				this.responseData = org.apache.commons.io.IOUtils.toString(rd);
				this.ok = true;

			    logger.info("Response : " + responseData);
			} else {
				this.ok = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.ok = false;
		}
	}

}
