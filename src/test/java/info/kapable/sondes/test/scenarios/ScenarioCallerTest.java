package info.kapable.sondes.test.scenarios;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.HashMap;

import org.junit.Test;

import info.kapable.sondes.scenarios.ScenarioCaller;
import info.kapable.sondes.scenarios.ScenarioParsingException;
import info.kapable.sondes.scenarios.UnsuportedNavigatorException;

public class ScenarioCallerTest {

	@Test
	public void emptyTestFirefox() {
		String scenarioFilePath =  this.getClass().getClassLoader().getResource("scenario/emptyScenario.xml").getPath();
		String osAppropriatePath = System.getProperty( "os.name" ).contains( "indow" ) ? scenarioFilePath.substring(1) : scenarioFilePath;
		ScenarioCaller caller;
		String index_html = this.getClass().getClassLoader().getResource("index.html").getPath();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("index", "file://" + index_html);
		try {
			caller = new ScenarioCaller(osAppropriatePath, map, "UTF-8", ScenarioCaller.FIREFOX);
			int returnCode = caller.launchTest();
			assertTrue(returnCode == 0);
		} catch (ScenarioParsingException e) {
			e.printStackTrace();
			fail("ScenarioParsingException raise");
		} catch (UnsuportedNavigatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("UnsuportedNavigatorException raise");
		}
	}
	@Test
	public void emptyTestIE() {
		String scenarioFilePath =  this.getClass().getClassLoader().getResource("scenario/emptyScenario.xml").getPath();
		String osAppropriatePath = System.getProperty( "os.name" ).contains( "indow" ) ? scenarioFilePath.substring(1) : scenarioFilePath;
		ScenarioCaller caller;
		String index_html = this.getClass().getClassLoader().getResource("index.html").getPath();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("index", "file://" + index_html);
		try {
			caller = new ScenarioCaller(osAppropriatePath, map, "UTF-8", ScenarioCaller.IE);
			int returnCode = caller.launchTest();
			assertTrue(returnCode == 0);
		} catch (ScenarioParsingException e) {
			e.printStackTrace();
			fail("ScenarioParsingException raise");
		} catch (UnsuportedNavigatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("UnsuportedNavigatorException raise");
		}
	}
}
