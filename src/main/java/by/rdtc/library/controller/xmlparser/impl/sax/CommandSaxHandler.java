package by.rdtc.library.controller.xmlparser.impl.sax;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import by.rdtc.library.controller.xmlparser.bean.XMLCommand;

public class CommandSaxHandler extends DefaultHandler {
	private HashMap<String, ArrayList<XMLCommand>> commandMap= new HashMap<>();
	private XMLCommand command;
	private ArrayList<XMLCommand> commands;
	private String role;
	private StringBuilder text;

	public HashMap<String, ArrayList<XMLCommand>> getCommandMap() {
		return commandMap;
	}

	public void setCommandMap(HashMap<String, ArrayList<XMLCommand>> commandMap) {
		this.commandMap = commandMap;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Parsing started");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Parsing ended");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		text = new StringBuilder();
		switch(qName){
		case "role":
			role = attributes.getValue("title");
			commands=new ArrayList<>();
			break;
		case "command":
			command=new XMLCommand();
			command.setName(attributes.getValue("name"));
			break;
		default:
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "path":
			command.setPath(text.toString());
			break;
		case "command":
			commands.add(command);
			command=null;
			break;
		case "role":
			commandMap.put(role,commands);
			commands=null;
		default:
			break;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		text.append(ch, start, length);
	}
}
