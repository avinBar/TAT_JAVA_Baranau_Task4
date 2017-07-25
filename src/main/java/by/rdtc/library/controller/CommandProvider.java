package by.rdtc.library.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.rdtc.library.controller.command.Command;
import by.rdtc.library.controller.util.CommandUtil;
import by.rdtc.library.controller.xmlparser.XMLParserFactory;
import by.rdtc.library.controller.xmlparser.bean.XMLCommand;

final class CommandProvider {
	private static final String GUEST = "guest";
	private static final String USER = "user";
	private static final String ADMIN = "admin";
	private static final String SUPER_ADMIN = "super_admin";
	private final static String DOM = "DOM";
    private final static String SAX = "SAX";

	private Map<CommandName, Command> adminCommands = new HashMap<>();
	private Map<CommandName, Command> superAdminCommands = new HashMap<>();
	private Map<CommandName, Command> userCommands = new HashMap<>();
	private Map<CommandName, Command> guestCommands = new HashMap<>();

	private CommandProvider() {
		XMLParserFactory parserFactory = XMLParserFactory.getInstance();
		Map<String, ArrayList<XMLCommand>> commandList = parserFactory.getParser(DOM).getCommandsMap("/commands.xml");
		for (Map.Entry<String, ArrayList<XMLCommand>> entry : commandList.entrySet()) {
			System.out.println(entry.getKey() + "  " + entry.getValue());
			String role = entry.getKey();
			ArrayList<XMLCommand> commands = entry.getValue();
			switch (role) {
			case GUEST:
				guestCommands = CommandUtil.initCommands(commands);
				break;
			case USER:
				userCommands = CommandUtil.initCommands(commands);
				break;
			case ADMIN:
				adminCommands = CommandUtil.initCommands(commands);
				break;
			case SUPER_ADMIN:
				superAdminCommands = CommandUtil.initCommands(commands);
				break;
			}
		}
		adminCommands.putAll(userCommands);
		superAdminCommands.putAll(adminCommands);
	}


	Command getCommand(String type, String name) {
		CommandName commandName = null;
		Command command = null;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			switch (type) {
			case USER:
				return userCommands.get(commandName);
			case ADMIN:
				return adminCommands.get(commandName);
			case SUPER_ADMIN:
				return superAdminCommands.get(commandName);
			default:
				return guestCommands.get(commandName);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			command = guestCommands.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}
}
