package info.kapable.sondes.run;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import info.kapable.sondes.repports.JsonPageReport;
import info.kapable.sondes.repports.MultiOutputReport;
import info.kapable.sondes.repports.Report;
import info.kapable.sondes.repports.WebPageReport;
import info.kapable.sondes.repports.XMLPageReport;
import info.kapable.sondes.scenarios.ScenarioCaller;
import info.kapable.sondes.scenarios.ScenarioParsingException;
import info.kapable.sondes.scenarios.UnsuportedNavigatorException;

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
		Option statisticHtmlOption = Option.builder("h")
				.longOpt("statistic-html")
				.desc("File to export statistic as HTML")
				.hasArg()
				.argName( "statistic-html" )
				.build();
		Option statisticXmlOption = Option.builder("x")
				.longOpt("statistic-xml")
				.desc("File to export statistic as XML")
				.hasArg()
				.argName( "statistic-xml" )
				.build();
		Option statisticJsonOption = Option.builder("j")
				.longOpt("statistic-json")
				.desc("File to export statistic as JSON")
				.hasArg()
				.argName( "statistic-json" )
				.build();
		
		options.addOption( scenarioFileOption );
		options.addOption( navigatorOption );
		options.addOption( statisticHtmlOption );
		options.addOption( statisticXmlOption );
		options.addOption( statisticJsonOption );
		
	    CommandLineParser parser = new DefaultParser();
		return parser.parse( options, args );
	}
	
	public static void main(String[] args) {
		
	    CommandLine line = null ;
	    /* Default navigator */
	    int navigatorId = ScenarioCaller.FIREFOX;
	    /* Default statistic file */
		File outputStatisticHTMLFile = null;
		File outputStatisticXMLFile = null;
		File outputStatisticJsonFile = null;
	    File file;
        try {
        	line = option(args);
        	
        	// Optional Navigator option
			if(line.hasOption("navigator")) {
				String navigator = line.getOptionValue("navigator");
				navigatorId = Scenario.getNavigatorId(navigator);
			}
			
			// Optional Statistic file
			if(line.hasOption("statistic-html")) {
				outputStatisticHTMLFile = new File(line.getOptionValue("statistic-html"));
			}

			// Optional Statistic file
			if(line.hasOption("statistic-xml")) {
				outputStatisticXMLFile = new File(line.getOptionValue("statistic-xml"));
			}

			// Optional Statistic file
			if(line.hasOption("statistic-json")) {
				outputStatisticJsonFile = new File(line.getOptionValue("statistic-json"));
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
				MultiOutputReport outputReport = new MultiOutputReport();
				
				if(outputStatisticHTMLFile != null) {
					Report outputReportHTML = new WebPageReport(outputStatisticHTMLFile);
					outputReport.addOutputReport(outputReportHTML);
				}

				if(outputStatisticXMLFile != null) {
					Report outputReportXML = new XMLPageReport(outputStatisticXMLFile);
					outputReport.addOutputReport(outputReportXML);
				}

				if(outputStatisticJsonFile != null) {
					Report outputReportJson = new JsonPageReport(outputStatisticHTMLFile);
					outputReport.addOutputReport(outputReportJson);
				}
				caller.setOutputReport(outputReport);

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
