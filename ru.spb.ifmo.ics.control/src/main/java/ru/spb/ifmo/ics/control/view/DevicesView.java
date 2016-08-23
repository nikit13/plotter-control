package ru.spb.ifmo.ics.control.view;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;

import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.control.view.internal.CellDataProvider;
import ru.spb.ifmo.ics.log.LogService;
import ru.spb.ifmo.ics.log.Tag;
import ru.spb.ifmo.ics.model.SerialDevice;
import ru.spb.ifmo.ics.serial.SerialService;

/**
 * Окно вывода списка доступных устройств
 * 
 * @author nikit
 *
 */
public class DevicesView extends PlotterControlTableView {
	public static final String ID = "ru.spb.ifmo.ics.view.devicesView";

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		final LogService logService = getLogService();
		final ISelectionService selectionService = getService(ISelectionService.class);
		ISelectionListener selectionListener = (part, selection) -> {
			IStructuredSelection structured = (IStructuredSelection) selection;
			SerialDevice selectedDevice = (SerialDevice) structured
			        .getFirstElement();
			getCommandService().setCurrentDevice(selectedDevice);
			logService.logInfo(Tag.DEVICES, "Выбор устройства: "
			        + ((selectedDevice != null) ? selectedDevice.getName()
			                : null));
		};
		selectionService.addSelectionListener(ID, selectionListener);

		final TableViewer viewer = getTableViewer();
		final CommandService commandService = getCommandService();
		commandService.addDeviceConnectedListener(l -> viewer.refresh());
		commandService.addDeviceDisonnectedListener(l -> viewer.refresh());

		commandService.addDeviceConnectedListener(d -> logService.logInfo(
		        Tag.DEVICES, "Устройство успешно подключено"));
		commandService.addDeviceDisonnectedListener(d -> logService.logInfo(
		        Tag.DEVICES, "Устройство успешно отключено"));

		final SerialService serialService = getSerialService();
		serialService.addDeviceListChangedListener(devices -> viewer
		        .setInput(devices));

		serialService.addDeviceListChangedListener(devices -> logService
		        .logInfo(Tag.DEVICES, "Список устройств обновлен"));

	}

	@Override
	protected ColumnDescriptor[] createColumns() {
		return new ColumnDescriptor[] {
		        new ColumnDescriptor("Имя порта", 100,
		                new CellDataProvider<SerialDevice>(d -> d.getName())),
		        new ColumnDescriptor("Состояние подключения", 100,
		                new CellDataProvider<SerialDevice>(
		                        d -> String.valueOf(d.isConnected()))) };
	}

	@Override
	public void setFocus() {
		getSerialService().reloadDevices();
	}
}
