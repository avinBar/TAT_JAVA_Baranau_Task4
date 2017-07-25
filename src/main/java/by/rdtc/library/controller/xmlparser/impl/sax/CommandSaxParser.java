package by.rdtc.library.controller.xmlparser.impl.sax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.rdtc.library.controller.xmlparser.bean.XMLCommand;
import by.rdtc.library.controller.xmlparser.iface.XMLParser;

public class CommandSaxParser implements XMLParser {
	private static final Logger log = Logger.getLogger(CommandSaxParser.class);
	
	@Override
	public Map<String, ArrayList<XMLCommand>> getCommandsMap(String xmlPath) {
		CommandSaxHandler handler = new CommandSaxHandler();
		try {
			XMLReader reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(xmlPath));
			reader.setFeature("http://xml.org/sax/features/validation", true);
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setFeature("http://xml.org/sax/features/string-interning", true);
			reader.setFeature("http://apache.org/xml/features/validation/schema", false);
		} catch (SAXException | IOException e) {
			log.error(e);}

			Map<String, ArrayList<XMLCommand>> map = handler.getCommandMap();
			return map;
		}
	}

