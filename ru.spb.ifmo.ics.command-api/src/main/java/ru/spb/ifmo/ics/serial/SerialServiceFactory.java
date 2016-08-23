package ru.spb.ifmo.ics.serial;

import ru.spb.ifmo.ics.serial.internal.DefaultSerialService;
import ru.spb.ifmo.ics.serial.internal.SerialServiceStub;
import ru.spb.ifmo.ics.util.service.GlobalServiceFactory;

public class SerialServiceFactory extends GlobalServiceFactory<SerialService> {

	private static final boolean EMULATED = Boolean.getBoolean("plotter.emulated");

	@Override
	public SerialService createNewInstance() {
		return (EMULATED) ? new SerialServiceStub()
		        : new DefaultSerialService();
	}
}
