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

	static Options options;

	/**
	 * 
	 * @param navigator
	 * @return
	 * @throws UnsuportedNavigatorException
	 */
	private static int getNavigatorId(String navigator) throws UnsuportedNavigatorException {
		if(navigator.compareToIgnoreCase("firefox") == 0) {
			return  ScenarioCaller.FIREFOX;
		} else if (navigator.compareToIgnoreCase("ie") == 0) {
			return ScenarioCaller.IE;
		} else if (navigator.compareToIgnoreCase("direct") == 0) {
			return ScenarioCaller.DIRECT;
		} else {
			throw new UnsuportedNavigatorException();
		}
	}
	/**
	 * Parsing arguments
	 * @param args
	 * @return
	 * @throws ParseException
	 */
	public static CommandLine option(String[] args) throws ParseException {
		/* Running option */
		options = new Options();
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
		Option statisticOption = Option.builder("s")
				.longOpt("statistic")
				.desc("File to export statistic")
				.hasArg()
				.argName( "statistic" )
				.build();

		options.addOption( scenarioFileOption );
		options.addOption( navigatorOption );
		options.addOption( statisticOption );
		
	    CommandLineParser parser = new DefaultParser();
		return parser.parse( options, args );
	}
	
	public static void main(String[] args) {
		
	    CommandLine line = null ;
	    /* Default navigator */
	    int navigatorId = ScenarioCaller.FIREFOX;
	    /* Default statistic file */
		File outputStatisticFile = null;
	    File file;
        try {
        	line = option(args);
        	
        	// Optional Navigator option
			if(line.hasOption("navigator")) {
				String navigator = line.getOptionValue("navigator");
				navigatorId = Scenario.getNavigatorId(navigator);
			}
			
			// Optional Statistic file
			if(line.hasOption("statistic")) {
				outputStatisticFile = new File(line.getOptionValue("statistic"));
			}
			
			// Scenario file
			file = new File(line.getOptionValue("file"));
			String scenarioFilePath =  file.getAbsolutePath();

			
			ScenarioCaller caller;
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> env = System.getenv();
			for (String envName : env.keySet()) {
				map.put(envName, env.get(envName));
			}
			try {
				caller = new ScenarioCaller(scenarioFilePath, map, "UTF-8", navigatorId);
				if(outputStatisticFile != null) {
					caller.setOutputStatisticFile(outputStatisticFile);
				}
				int returnCode = caller.launchTest();
				System.exit(returnCode);
			} catch (ScenarioParsingException e) {
				e.printStackTrace();
			} catch (UnsuportedNavigatorException e) {
				e.printStackTrace();
			}
		} catch (ParseException exp) {
	        System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
	        // automatically generate the help statement
	        HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp( "info.kapable.sondes.run.Scenario", options );
		} catch (UnsuportedNavigatorException e) {
	        System.err.println( "Reason: navigator must be (ie or firefox or direct)" );
			HelpFormatter formatter = new HelpFormatter();
	        formatter.printHelp( "info.kapable.sondes.run.Scenario", options );
		}
        System.exit(255);
	}


}
