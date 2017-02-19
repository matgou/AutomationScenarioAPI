package info.kapable.sondes.scenarios.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import info.kapable.sondes.scenarios.ScenarioException;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.openqa.selenium.WebDriver;
import org.jdom.xpath.XPath;

public class VerifierXmlDansPageAction extends Action {
	protected Element xml;
	protected String erreur;
	protected XPath xpath;

	public VerifierXmlDansPageAction(String xpath, String xml_str, String erreur) {
	ByteArrayInputStream stream;
	try {
		this.xpath = XPath.newInstance(xpath);
	} catch (JDOMException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		stream = new ByteArrayInputStream(xml_str.getBytes("UTF-8"));
		SAXBuilder sxb = new SAXBuilder();
		org.jdom.Document document = sxb.build(stream);
		xml = document.getRootElement();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JDOMException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.erreur = erreur;
	}

	@Override
	public void executeAction(WebDriver driver) throws ScenarioException {
		try {
			String pageXml = driver.getPageSource();
			ByteArrayInputStream stream = new ByteArrayInputStream(pageXml.getBytes("UTF-8"));
			SAXBuilder sxb = new SAXBuilder();
			org.jdom.Document document;
			document = sxb.build(stream);
			List results = xpath.selectNodes(document.getRootElement());
	        Iterator iter = results.iterator() ;

	        while (iter.hasNext()){
	        	Element noeudCourant = (Element) iter.next();

				XMLOutputter outp = new XMLOutputter();
				String noeudCourantString = outp.outputString(noeudCourant);
				String xmlString = outp.outputString(xml);
				
	        	if(noeudCourantString.compareTo(xmlString) != 0) {
	        		this.logEvent("XML Verification","Attendu" + xmlString);
	        		this.logEvent("XML Verification","Trouve" + noeudCourantString);
	    			throw new ScenarioException("ERREUR: " + erreur);        		
	        	} else {
	        		this.logEvent("XML Verification","Attendu : " + xmlString);
	        		this.logEvent("XML Verification","Trouve : " + noeudCourantString);
	        		this.logEvent("XML Verification","Verification OK");
	        	}
	        }
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ScenarioException("ERREUR: " + e);
		}
	}

	@Override
	public String getDescription() {
		return "Verifier que le XML retour contient " + this.xml.toString();
	}

}
