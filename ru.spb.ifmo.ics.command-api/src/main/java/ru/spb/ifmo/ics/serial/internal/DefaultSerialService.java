package ru.spb.ifmo.ics.serial.internal;

import java.util.Arrays;

import jssc.SerialPort;
import jssc.SerialPortList;
import ru.spb.ifmo.ics.model.SerialDevice;
import ru.spb.ifmo.ics.model.internal.JsscSerialDevice;

/**
 * Реализация SerialService, используется по умолчанию
 * 
 * @author nikit
 *
 */
public class DefaultSerialService extends AbstractSerialService {

	private SerialDevice[] availableDevices;

	@Override
	public SerialDevice[] getDevices() {
		if (availableDevices == null) {
			reloadDevices();
		}
		return availableDevices;
	}

	@Override
	public void reloadDevices() {
		availableDevices = Arrays.asList(SerialPortList.getPortNames())
		        .stream().map(portName -> new SerialPort(portName))
		        .map(port -> new JsscSerialDevice(port))
		        .toArray(JsscSerialDevice[]::new);
		fireDevicesListChanged();
	}
}
