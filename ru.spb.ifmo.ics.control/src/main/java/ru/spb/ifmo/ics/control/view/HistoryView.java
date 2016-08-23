package ru.spb.ifmo.ics.control.view;

import java.util.Arrays;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionService;

import ru.spb.ifmo.ics.command.CommandResponse;
import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.command.HistoryCommandResponse;
import ru.spb.ifmo.ics.control.view.internal.CellDataProvider;
import ru.spb.ifmo.ics.log.LogService;
import ru.spb.ifmo.ics.log.Tag;
import ru.spb.ifmo.ics.model.Command;
import ru.spb.ifmo.ics.model.HistoryRecord;
import ru.spb.ifmo.ics.model.SerialDevice;

/**
 * Окно вывода истории выполнения команд
 * 
 * @author nikit
 *
 */
public class HistoryView extends PlotterControlTableView {
	public static final String ID = "ru.spb.ifmo.ics.view.historyView";

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		IToolBarManager toolBar = getViewSite().getActionBars()
		        .getToolBarManager();
		toolBar.add(new ShowInfoAction());
		toolBar.add(new InvokeHistoryCommandAction());
		toolBar.add(new LoadHistoryAction());
	}

	@Override
	protected ColumnDescriptor[] createColumns() {
		return new ColumnDescriptor[] { new ColumnDescriptor("Команда", 100,
		        new CellDataProvider<HistoryRecord>(r -> r.getOpcode()
		                .getName())) };
	}

	/**
	 * Задает список команд из истории
	 * 
	 * @param historyRecords
	 *            список команд из истории
	 */
	public void setHistory(HistoryRecord... historyRecords) {
		getTableViewer().setInput(historyRecords);
	}

	/**
	 * Показать информацию о команде
	 * 
	 * @author nikit
	 *
	 */
	private class ShowInfoAction extends Action {

		@Override
		public void run() {
			final ISelectionService selectionService = getService(ISelectionService.class);
			HistoryRecord record = getSelectedObject(
			        selectionService.getSelection(), HistoryRecord.class);

			final LogService logService = getService(LogService.class);
			if (record == null) {
				logService.logError(Tag.HISTORY, "Не выбрана команда");
				return;
			}

			logService.logInfo(
			        Tag.HISTORY,
			        "Информация о команде \""
			                + record.getOpcode().getName()
			                + "\" - код 0x"
			                + Integer.toHexString(record.getOpcode()
			                        .getOpcode()) + ", аргументы - "
			                + Arrays.toString(record.getArgs()));
		}

		@Override
		public String getText() {
			return "Показать информацию о команде";
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return ImageDescriptor.createFromURL(getClass().getClassLoader()
			        .getResource("icons/info_obj.png"));
		}
	}

	/**
	 * Запуск выбранной команды из истории
	 * 
	 * @author nikit
	 *
	 */
	private class InvokeHistoryCommandAction extends Action {

		@Override
		public void run() {
			super.run();
			CommandService commandService = getCommandService();
			SerialDevice currentDevice = commandService.getCurrentDevice();

			final LogService logService = getLogService();
			if (currentDevice == null || !currentDevice.isConnected()) {
				logService.logError(Tag.HISTORY, "Устройство не подключено");
				return;
			}

			ISelectionService selectionService = getService(ISelectionService.class);
			HistoryRecord record = getSelectedObject(
			        selectionService.getSelection(), HistoryRecord.class);
			if (record == null) {
				logService.logError(Tag.HISTORY,
				        "Не выбрана команда для исполнения");
				return;
			}

			logService.logInfo(
			        Tag.HISTORY,
			        "Повторное выполнение команды \""
			                + record.getOpcode().getName()
			                + "\" - код 0x"
			                + Integer.toHexString(record.getOpcode()
			                        .getOpcode()) + ", аргументы - "
			                + Arrays.toString(record.getArgs()));
			CommandResponse response = commandService.invoke(record
			        .createCommand());
			logService.logInfo(
			        Tag.HISTORY,
			        "Код возврата 0x"
			                + Integer.toHexString(response.getReturnCode()));
		}

		@Override
		public String getText() {
			return "Запустить команду";
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return ImageDescriptor.createFromURL(getClass().getClassLoader()
			        .getResource("icons/connect.png"));
		}
	}

	/**
	 * Загрузка истории команд
	 * 
	 * @author nikit
	 *
	 */
	private class LoadHistoryAction extends Action {

		@Override
		public void run() {
			super.run();
			try {
				CommandService commandService = getCommandService();
				if (commandService.getCurrentDevice() == null
				        || !commandService.getCurrentDevice().isConnected()) {
					throw new IllegalStateException("Устройство не подключено");
				}

				HistoryCommandResponse response = (HistoryCommandResponse) commandService
				        .invoke(Command.historyloadall());
				setHistory(response.getRecords());
			} catch (RuntimeException e) {
				getLogService().logError(Tag.HISTORY, e.getMessage());
			}
		}

		@Override
		public String getText() {
			return "Обновить историю";
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return ImageDescriptor.createFromURL(getClass().getClassLoader()
			        .getResource("icons/refresh.gif"));
		}
	}
}
