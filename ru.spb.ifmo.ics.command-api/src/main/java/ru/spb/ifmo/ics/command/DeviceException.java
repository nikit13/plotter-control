package ru.spb.ifmo.ics.command;

/**
 * Исключение при работе с устройством
 * 
 * @author nikit
 *
 */
public class DeviceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DeviceException() {
		super();
	}

	public DeviceException(String message, Throwable cause,
	        boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeviceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeviceException(String message) {
		super(message);
	}

	public DeviceException(Throwable cause) {
		super(cause);
	}
}
