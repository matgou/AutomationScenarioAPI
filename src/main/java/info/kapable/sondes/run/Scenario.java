package info.kapable.sondes.run;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import info.kapable.sondes.scenarios.ScenarioCaller;
import info.kapable.sondes.scenarios.ScenarioParsingException;
import info.kapable.sondes.scenarios.UnsuportedNavigatorException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Scenario {

	public static void main(String[] args) {
		
		Options options = new Options();
		Option scenarioFileOption = Option.builder("f")
			    .longOpt( "file" )
			    .desc( "Scenario xml file to play"  )
			    .hasArg()
			    .required()
			    .argName( "scenarioXMLFile" )
			    .build();
		Option navigatorOption = Option.builder("n")
				.longOpt("navigator")
				.desc("Navigator to use (firefox, ie, or direct)")
				.hasArg()
				.argName( "navigator" )
				.build();
		options.addOption( scenarioFileOption );
		options.addOption( navigatorOption ); 
	    CommandLineParser parser = new DefaultParser();
	    CommandLine line = null ;
	    int navigatorId = ScenarioCaller.FIREFOX;
        try {
			line = parser.parse( options, args );
			if(line.hasOption("navigator")) {
				String navigator = line.getOptionValue("navigator");
				navigatorId = Scenario.getNavigatorId(navigator);
			}
		} catch (ParseException exp) {
	        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
	        // automatically generate the help statement
	        HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp( "info.kapable.sondes.run.Scenario", options );
	        System.exit(255);
		} catch (UnsuportedNavigatorException e) {
	        System.err.println( "Reason: navigator must be (ie or firefox)" );

			HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp( "info.kapable.sondes.run.Scenario", options );
	        System.exit(255);
		}

		
		File file = new File(line.getOptionValue("file"));
		String scenarioFilePath =  file.getAbsolutePath();
		
		ScenarioCaller caller;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			map.put(envName, env.get(envName));
		}
		try {
			caller = new ScenarioCaller(scenarioFilePath, map, "UTF-8", navigatorId);
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

	/**
	 * 
	 * @param navigator
	 * @return
	 * @throws UnsuportedNavigatorException
	 */
	private static int getNavigatorId(String navigator) throws UnsuportedNavigatorException {
		if(navigator.contentEquals("firefox")) {
			return  ScenarioCaller.FIREFOX;
		} else if (navigator.contentEquals("ie")) {
			return ScenarioCaller.IE;
		} else {
			throw new UnsuportedNavigatorException();
		}
	}

}
