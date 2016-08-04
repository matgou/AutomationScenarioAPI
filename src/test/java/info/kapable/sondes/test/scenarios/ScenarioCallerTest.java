package info.kapable.sondes.test.scenarios;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

import info.kapable.sondes.scenarios.ScenarioCaller;

public class ScenarioCallerTest {

	@Test
	public void emptyTest() {
		String scenarioFilePath =  this.getClass().getClassLoader().getResource("scenario/emptyScenario.xml").getPath();
		String osAppropriatePath = System.getProperty( "os.name" ).contains( "indow" ) ? scenarioFilePath.substring(1) : scenarioFilePath;
		ScenarioCaller caller = new ScenarioCaller(osAppropriatePath, "UTF-8");
		int returnCode = caller.launchTest();
		assertTrue(returnCode == 0);
	}

}
