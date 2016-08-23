package ru.spb.ifmo.ics.control.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.command.CommandResponse;
import ru.spb.ifmo.ics.log.LogService;
import ru.spb.ifmo.ics.log.Tag;
import ru.spb.ifmo.ics.model.Command;
import ru.spb.ifmo.ics.model.CommandOpcode;

public class InvokeCommandHandler extends AbstractHandler {

	public static final String COMMAND_NAME = "ru.spb.ifmo.ics.command.invokeCommand.commandName";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			CommandService invoker = (CommandService) PlatformUI.getWorkbench()
			        .getService(CommandService.class);
			if (!invoker.isSpecifiedDevice()
			        || !invoker.getCurrentDevice().isConnected()) {
				throw new IllegalStateException("Нет подключенных устройств");
			}

			String commandName = event.getParameter(COMMAND_NAME);
			if (commandName == null) {
				throw new IllegalStateException("Не задано имя команды");
			}

			List<CommandOpcode> find = Arrays.stream(CommandOpcode.values())
			        .filter(o -> o.getName().equals(commandName))
			        .collect(Collectors.toList());
			if (find.size() > 1) {
				throw new IllegalStateException(
				        "Неоднозначное определение команды");
			}
			CommandOpcode commandOpcode = find.get(0);

			CommandResponse response = invoker.invoke(Command
			        .generic(commandOpcode));
			if (response == null) {
				throw new IllegalStateException("Ответ не получен");
			}
			MessageBox box = new MessageBox(PlatformUI.getWorkbench()
			        .getActiveWorkbenchWindow().getShell());
			box.setText("Результат выполнения команды");
			box.setMessage("Код возврата: 0x"
			        + Integer.toHexString(response.getReturnCode())
			        + ", данные: " + Arrays.toString(response.getResultData()));
			box.open();
		} catch (RuntimeException e) {
			LogService logService = (LogService) PlatformUI.getWorkbench()
			        .getService(LogService.class);
			logService.logError(Tag.DEVICES, e.getMessage());
			MessageBox box = new MessageBox(PlatformUI.getWorkbench()
			        .getActiveWorkbenchWindow().getShell());
			box.setMessage(e.getMessage());
			box.setText("Ошибка");
			box.open();
		}
		return null;
	}

}
