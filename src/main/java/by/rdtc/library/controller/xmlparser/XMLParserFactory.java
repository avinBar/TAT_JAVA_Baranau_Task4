package by.rdtc.library.controller.xmlparser;

import by.rdtc.library.controller.xmlparser.iface.XMLParser;
import by.rdtc.library.controller.xmlparser.impl.dom.CommandDomParser;
import by.rdtc.library.controller.xmlparser.impl.sax.CommandSaxParser;

public class XMLParserFactory {
	private static final XMLParserFactory instance = new XMLParserFactory();
	private final XMLParser domParser = new CommandDomParser();
	private final XMLParser saxParser = new CommandSaxParser();
	
	private final static String DOM = "DOM";
    private final static String SAX = "SAX";

	private XMLParserFactory() {
	}

	public static XMLParserFactory getInstance() {
		return instance;
	}
	
	public XMLParser getParser(String parserName){
		XMLParser parser=null;
		switch(parserName){
		case DOM:
			parser=getDomParser();
			break;
		case SAX:
			parser=getSaxParser();
			break;
		default:
            parser = getDomParser();
            break;
		}
		
		return parser;
	}

	private XMLParser getDomParser() {
		return domParser;
	}

	private XMLParser getSaxParser() {
		return saxParser;
	}
}
