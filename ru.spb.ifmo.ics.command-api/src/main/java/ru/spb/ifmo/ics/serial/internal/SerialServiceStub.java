package ru.spb.ifmo.ics.serial.internal;

import ru.spb.ifmo.ics.model.SerialDevice;
import ru.spb.ifmo.ics.model.internal.SerialDeviceStub;

public class SerialServiceStub extends AbstractSerialService {

	private static SerialDevice DEVICE_STUB = new SerialDeviceStub();

	@Override
	public SerialDevice[] getDevices() {
		return new SerialDevice[] { DEVICE_STUB };
	}

}
