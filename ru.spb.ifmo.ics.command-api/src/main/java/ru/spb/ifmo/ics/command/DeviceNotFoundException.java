package ru.spb.ifmo.ics.command;

import ru.spb.ifmo.ics.model.SerialDevice;

/**
 * Возникает при отсутствии подключенного устройства
 * 
 * @author nikit
 *
 */
public class DeviceNotFoundException extends DeviceException {
	private static final long serialVersionUID = 1L;

	public DeviceNotFoundException() {
		super();
	}

	public DeviceNotFoundException(String message, Throwable cause,
	        boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeviceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceNotFoundException(String message) {
		super(message);
	}

	public DeviceNotFoundException(Throwable cause) {
		super(cause);
	}

	public DeviceNotFoundException(SerialDevice serialDevice) {
		super("Устройство не найдено: " + serialDevice.getName());
	}
}
