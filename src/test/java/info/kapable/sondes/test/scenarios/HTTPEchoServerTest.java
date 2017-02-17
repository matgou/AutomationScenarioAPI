package info.kapable.sondes.test.scenarios;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.junit.Test;

import info.kapable.sondes.scenarios.ScenarioCaller;
import info.kapable.sondes.scenarios.ScenarioParsingException;
import info.kapable.sondes.scenarios.UnsuportedNavigatorException;

public class HTTPEchoServerTest {
	static final int port = 8080;

	@Test
	public void test() {
		Thread thread = new Thread() {
			public void run() {
				ServerSocket s;
				try {
					s = new ServerSocket(port);
					Socket soc = s.accept();

					// Un BufferedReader permet de lire par ligne.
					BufferedReader plec = new BufferedReader(new InputStreamReader(soc.getInputStream()));

					// Un PrintWriter possède toutes les opérations print
					// classiques.
					// En mode auto-flush, le tampon est vidé (flush) à l'appel
					// de println.
					PrintWriter pred = new PrintWriter(
							new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true);
					while (true) {
						String str = plec.readLine(); // lecture du message
						if (str.equals(""))
							break;
						System.out.println("MINI-HTTP = " + str); // trace locale
					}
					String str = "Hello from HTTPEchoServerTest";
					pred.println("HTTP/1.0 200 OK");
					pred.println("Date: Fri, 31 Dec 1999 23:59:59 GMT");
					pred.println("Server: minihttp/0.0.1");
					pred.println("Content-Type: text/html");
					pred.println("Content-Length: " + str.length());
					pred.println("Expires: Sat, 01 Jan 2000 00:59:59 GMT");
					pred.println("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT");
					pred.println("");
					pred.println(str);
					
					plec.close();
					pred.close();
					soc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		thread.start();

		String scenarioFilePath =  this.getClass().getClassLoader().getResource("scenario/wsPost.xml").getPath();
		String osAppropriatePath = System.getProperty( "os.name" ).contains( "indow" ) ? scenarioFilePath.substring(1) : scenarioFilePath;
		ScenarioCaller caller;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("index", "http://localhost:" + this.port);
		
		try {
			caller = new ScenarioCaller(osAppropriatePath, map, "UTF-8", ScenarioCaller.DIRECT);
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
		
		thread.interrupt();
	}

}
