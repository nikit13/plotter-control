package ru.spb.ifmo.ics.model.internal;

import jssc.SerialPort;
import ru.spb.ifmo.ics.model.SerialDevice;

public class SerialDeviceStub implements SerialDevice {

	private boolean isConnected;

	@Override
	public String getName() {
		return "Serial Device";
	}

	@Override
	public SerialPort getPort() {
		return null;
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public void setConnected(boolean connected) {
		isConnected = connected;
	}

}
