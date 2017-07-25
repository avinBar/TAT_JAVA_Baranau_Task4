package by.rdtc.library.controller.xmlparser.impl.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.rdtc.library.controller.xmlparser.bean.XMLCommand;
import by.rdtc.library.controller.xmlparser.iface.XMLParser;

public class CommandDomParser implements XMLParser {
	private static final Logger log = Logger.getLogger(CommandDomParser.class);
	
	public HashMap<String, ArrayList<XMLCommand>> getCommandsMap(String xmlPath) {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(xmlPath);
		} catch (SAXException | IOException e) {
			log.error(e);
		}
		
		Document document = parser.getDocument();
		Element root = document.getDocumentElement();
		
		HashMap<String, ArrayList<XMLCommand>> map = new HashMap<>();
		ArrayList<XMLCommand> commandList;
		
		NodeList roleNodes = root.getElementsByTagName("role");
		for (int i = 0; i < roleNodes.getLength(); i++) {
			String role = null;
			commandList = new ArrayList<XMLCommand>();
			Element roleElement = (Element) roleNodes.item(i);
			role = roleElement.getAttribute("title");
			
			NodeList commandNodes = roleElement.getElementsByTagName("command");
			for (int j = 0; j < commandNodes.getLength(); j++) {
				XMLCommand command = null;
				Element commandElement = (Element) commandNodes.item(j);
				command = getCommand(commandElement);
				commandList.add(command);
			}
			map.put(role, commandList);
		}
		return map;
	}

	private static Element getSingleChild(Element element, String childName) {
		NodeList nlist = element.getElementsByTagName(childName);
		Element child = (Element) nlist.item(0);
		return child;
	}
	
	private static XMLCommand getCommand(Element element) {
		XMLCommand command = new XMLCommand();
		command.setName(element.getAttribute("name"));
		command.setPath(getSingleChild(element, "path").getTextContent().trim());
		return command;
	}
}
