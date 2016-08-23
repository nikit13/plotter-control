package ru.spb.ifmo.ics.command;

/**
 * Возникает при ошибке открытия/закрытия порта
 * 
 * @author nikit
 *
 */
public class ConnectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConnectionException() {
	    super();
    }

	public ConnectionException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
	    super(message, cause, enableSuppression, writableStackTrace);
    }

	public ConnectionException(String message, Throwable cause) {
	    super(message, cause);
    }

	public ConnectionException(String message) {
	    super(message);
    }

	public ConnectionException(Throwable cause) {
	    super(cause);
    }
}
