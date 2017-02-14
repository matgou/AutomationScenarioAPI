package info.kapable.sondes.run;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import info.kapable.sondes.scenarios.ScenarioCaller;
import info.kapable.sondes.scenarios.ScenarioParsingException;
import info.kapable.sondes.scenarios.UnsuportedNavigatorException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class IEScenario {

	public static void main(String[] args) {
		File file = new File(args[0]);
		String scenarioFilePath =  file.getAbsolutePath();
		
		ScenarioCaller caller;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			map.put(envName, env.get(envName));
		}
		try {
			caller = new ScenarioCaller(scenarioFilePath, map, "UTF-8", ScenarioCaller.IE);
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
