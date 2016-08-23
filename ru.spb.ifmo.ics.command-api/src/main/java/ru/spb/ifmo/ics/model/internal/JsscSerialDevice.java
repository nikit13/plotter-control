package ru.spb.ifmo.ics.model.internal;

import ru.spb.ifmo.ics.model.SerialDevice;
import jssc.SerialPort;

/**
 * Устройство, подключенное к последовательному каналу
 * 
 * @author nikit
 *
 */
public class JsscSerialDevice implements SerialDevice {

	private final SerialPort port;

	private boolean connected;

	public JsscSerialDevice(final SerialPort serialPort) {
		if (serialPort == null) {
			throw new NullPointerException(
			        "Неверное значение аргумента 'serialPort'");
		}
		port = serialPort;
	}

	@Override
	public SerialPort getPort() {
		return port;
	}

	@Override
	public String getName() {
		return port.getPortName();
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void setConnected(final boolean isConnected) {
		connected = isConnected;
	}
}
