package ru.spb.ifmo.ics.command;

/**
 * Генерируется при попытке выполнить команду при отсутствии подключенного
 * устройства
 * 
 * @author nikit
 *
 */
public class DeviceNotConnectedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DeviceNotConnectedException() {
		super();
	}

	public DeviceNotConnectedException(String message, Throwable cause,
	        boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeviceNotConnectedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceNotConnectedException(String message) {
		super(message);
	}

	public DeviceNotConnectedException(Throwable cause) {
		super(cause);
	}
}
