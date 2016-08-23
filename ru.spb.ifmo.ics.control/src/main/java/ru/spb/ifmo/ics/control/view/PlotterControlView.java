package ru.spb.ifmo.ics.control.view;

import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.log.LogService;
import ru.spb.ifmo.ics.serial.SerialService;

public abstract class PlotterControlView extends ExtendedViewPart {

	/**
	 * Получить экземпляр сервиса для управления доступными устройствами
	 */
	public SerialService getSerialService() {
		return getService(SerialService.class);
	}

	/**
	 * Получить экземпляр сервиса для управления подключением к устройству и
	 * исполнением команд плоттера
	 */
	public CommandService getCommandService() {
		return getService(CommandService.class);
	}

	/**
	 * Получить экземпляр сервиса логирования событий
	 */
	public LogService getLogService() {
		return getService(LogService.class);
	}
}
