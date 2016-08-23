package ru.spb.ifmo.ics.control.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.log.LogService;
import ru.spb.ifmo.ics.log.Tag;

public class DisconnectDeviceHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			CommandService commandService = (CommandService) PlatformUI
			        .getWorkbench().getService(CommandService.class);
			if (commandService.getCurrentDevice() == null) {
				throw new IllegalStateException("Устройство не выбрано");
			}

			if (!commandService.disconnect()) {
				throw new IllegalStateException(
				        "Ошибка при отключении устройства");
			}

			return true;
		} catch (RuntimeException e) {
			LogService logService = (LogService) PlatformUI.getWorkbench()
			        .getService(LogService.class);
			logService.logError(Tag.DEVICES, e.getMessage());
			MessageBox box = new MessageBox(PlatformUI.getWorkbench()
			        .getActiveWorkbenchWindow().getShell());
			box.setMessage((e.getMessage() == null) ? "" : e.getMessage());
			box.setText("Ошибка");
			box.open();
			return false;
		}
	}

}
