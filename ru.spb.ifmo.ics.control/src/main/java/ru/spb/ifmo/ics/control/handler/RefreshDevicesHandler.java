package ru.spb.ifmo.ics.control.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

import ru.spb.ifmo.ics.serial.SerialService;

public class RefreshDevicesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SerialService serialService = (SerialService) PlatformUI.getWorkbench()
		        .getService(SerialService.class);
		serialService.reloadDevices();
		return null;
	}

}
