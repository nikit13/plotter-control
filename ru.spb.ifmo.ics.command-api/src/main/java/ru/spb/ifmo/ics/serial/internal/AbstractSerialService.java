package ru.spb.ifmo.ics.serial.internal;

import java.util.Collection;
import java.util.HashSet;

import ru.spb.ifmo.ics.serial.SerialService;

public abstract class AbstractSerialService implements SerialService {

	private final Collection<DeviceListChangedListener> listeners = new HashSet<>();

	@Override
	public void addDeviceListChangedListener(DeviceListChangedListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Аргумент не может быть null");
		}
		listeners.add(l);
	}

	@Override
	public boolean removeDeviceListChangedListener(DeviceListChangedListener l) {
		if (l == null) {
			throw new IllegalArgumentException("Аргумент не может быть null");
		}
		return listeners.remove(l);
	}

	@Override
	public Collection<DeviceListChangedListener> getDeviceListChangedListeners() {
		return listeners;
	}
}
