package ru.spb.ifmo.ics.control.view;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;

import ru.spb.ifmo.ics.control.view.internal.CellDataProvider;
import ru.spb.ifmo.ics.log.LogService;
import ru.spb.ifmo.ics.log.Tag;

public class LogView extends PlotterControlTableView {
	public static final String ID = "ru.spb.ifmo.ics.view.logView";

	private static final Collection<LogMessage> MESSAGES = new ArrayList<>();

	private LogService.MessageListener messageListener;

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		TableViewer viewer = getTableViewer();
		viewer.setInput(MESSAGES);

		messageListener = (level, tag, message) -> {
			LogMessage logMessage = new LogMessage(level, tag, message);
			MESSAGES.add(logMessage);
			viewer.refresh(true, true);
			viewer.reveal(logMessage);
		};
		LogService logService = getLogService();
		logService.addMessageListener(messageListener);
	}

	@Override
	protected ColumnDescriptor[] createColumns() {
		return new ColumnDescriptor[] {
		        new ColumnDescriptor("Уровень", 100, new ImageCellProvider()),
		        new ColumnDescriptor("Тэг", 200,
		                new CellDataProvider<LogMessage>(e -> e.getTag())),
		        new ColumnDescriptor("Сообщение", 500,
		                new CellDataProvider<LogMessage>(e -> e.getMessage())) };
	}

	@Override
	public void dispose() {
		super.dispose();
		getLogService().removeMessageListener(messageListener);
	}

	@Override
	public void setFocus() {
		getTableViewer().refresh(true, false);
	}

	private static class LogMessage {
		private final LogService.Level level;
		private final Tag tag;
		private final String message;

		public LogMessage(LogService.Level level, Tag tag, String message) {
			this.level = level;
			this.tag = tag;
			this.message = message;
		}

		public LogService.Level getLevel() {
			return level;
		}

		public String getTag() {
			return tag.getDescription();
		}

		public String getMessage() {
			return message;
		}
	}

	private static class ImageCellProvider extends StyledCellLabelProvider {

		private static final ImageDescriptor INFO_ICON = ImageDescriptor
		        .createFromURL(ImageCellProvider.class.getClassLoader()
		                .getResource("icons/info_obj.png"));

		private static final ImageDescriptor WARNING_ICON = ImageDescriptor
		        .createFromURL(ImageCellProvider.class.getClassLoader()
		                .getResource("icons/warning_obj.png"));

		private static final ImageDescriptor ERROR_ICON = ImageDescriptor
		        .createFromURL(ImageCellProvider.class.getClassLoader()
		                .getResource("icons/error_obj.png"));

		@Override
		public void update(ViewerCell cell) {
			super.update(cell);
			LogMessage entry = (LogMessage) cell.getElement();
			switch (entry.getLevel()) {
			case INFO:
				cell.setImage(INFO_ICON.createImage());
				break;
			case WARNING:
				cell.setImage(WARNING_ICON.createImage());
				break;
			case ERROR:
				cell.setImage(ERROR_ICON.createImage());
				break;
			}
		}
	}
}
