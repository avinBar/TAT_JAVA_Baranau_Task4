package by.rdtc.library.controller.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.rdtc.library.controller.CommandName;
import by.rdtc.library.controller.command.Command;
import by.rdtc.library.controller.xmlparser.bean.XMLCommand;

public class CommandUtil {

	public static Map<CommandName, Command> initCommands(ArrayList<XMLCommand> commands) {
		Map<CommandName, Command> reflectCommands = new HashMap<>();
		CommandName name;
		Class clazz;
		for (XMLCommand xmlCommand : commands) {
			try {
				clazz = Class.forName(xmlCommand.getPath());
				Command command = (Command) clazz.newInstance();
				if ((name = CommandName.valueOf(xmlCommand.getName().toUpperCase())) != null) {
					reflectCommands.put(name, command);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			}
		}
		return reflectCommands;
	}
}
