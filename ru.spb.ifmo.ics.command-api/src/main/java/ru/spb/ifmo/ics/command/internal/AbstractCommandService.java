package ru.spb.ifmo.ics.command.internal;

import java.util.Collection;
import java.util.HashSet;

import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.model.SerialDevice;

/**
 * Реализация абстрактного сервиса исполнения команд, реализует хранение
 * текущего устройства и слушателей на события
 * 
 * @author nikit
 *
 */
public abstract class AbstractCommandService implements CommandService {

	private final Collection<DeviceConnectedListener> connectedListeners;

	private final Collection<DeviceChangedListener> changedListeners;

	private final Collection<DeviceDisconnectedListener> disconnectedListeners;

	private SerialDevice currentDevice;

	/**
	 * Новый экземпляр сервиса по умолчанию
	 */
	public AbstractCommandService() {
		connectedListeners = new HashSet<>();
		changedListeners = new HashSet<>();
		disconnectedListeners = new HashSet<>();
	}

	@Override
	public final void setCurrentDevice(SerialDevice serialDevice) {
		this.currentDevice = serialDevice;
		CommandService.super.setCurrentDevice(serialDevice);
	}

	@Override
	public final SerialDevice getCurrentDevice() {
		return currentDevice;
	}

	@Override
	public final Collection<DeviceConnectedListener> getDeviceConnectedListeners() {
		return connectedListeners;
	}

	@Override
	public final Collection<DeviceChangedListener> getDeviceChangedListeners() {
		return changedListeners;
	}

	@Override
	public final Collection<DeviceDisconnectedListener> getDeviceDisconnectedListeners() {
		return disconnectedListeners;
	}

}
