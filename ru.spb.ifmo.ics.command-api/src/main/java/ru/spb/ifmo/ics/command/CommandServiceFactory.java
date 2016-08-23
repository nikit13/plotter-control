package ru.spb.ifmo.ics.command;

import ru.spb.ifmo.ics.command.internal.CommandServiceStub;
import ru.spb.ifmo.ics.command.internal.DefaultCommandService;
import ru.spb.ifmo.ics.util.service.GlobalServiceFactory;

public class CommandServiceFactory extends GlobalServiceFactory<CommandService> {

	private static final boolean EMULATED_MODE = Boolean
	        .getBoolean("plotter.emulated");

	@Override
	public CommandService createNewInstance() {
		return (EMULATED_MODE) ? new CommandServiceStub()
		        : new DefaultCommandService();
	}
}
