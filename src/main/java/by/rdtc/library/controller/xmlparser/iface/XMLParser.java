package by.rdtc.library.controller.xmlparser.iface;

import java.util.ArrayList;
import java.util.Map;

import by.rdtc.library.controller.xmlparser.bean.XMLCommand;

public interface XMLParser {
	Map<String, ArrayList<XMLCommand>> getCommandsMap(String xmlPath);
}
