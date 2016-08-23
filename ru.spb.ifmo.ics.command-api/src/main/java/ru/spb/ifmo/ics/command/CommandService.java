package ru.spb.ifmo.ics.command;

import java.util.Collection;

import jssc.SerialPortException;
import ru.spb.ifmo.ics.model.Command;
import ru.spb.ifmo.ics.model.SerialDevice;

/**
 * Сервис для отправки команд плоттеру и получение ответов
 * 
 * @author nikit
 *
 */
public interface CommandService {

	/**
	 * Подключение к устройству
	 * 
	 * @return <code>true</code>, если устройство готово к работе,
	 *         <code>false</code> в обратном случае
	 */
	default boolean connect() {
		SerialDevice device = getCurrentDevice();
		if (device == null) {
			throw new DeviceNotFoundException("Устройство не задано");
		}

		if (device.isConnected()) {
			return true;
		}

		try {
			SerialDevice currentDevice = getCurrentDevice();
			if (currentDevice != null && currentDevice.getPort() != null) {
				currentDevice.getPort().openPort();
				currentDevice.setConnected(true);
			}
		} catch (SerialPortException e) {
			e.printStackTrace();
			getCurrentDevice().setConnected(false);
			try {
	            getCurrentDevice().getPort().closePort();
            } catch (SerialPortException e1) {
	            e1.printStackTrace();
            }
		}

		CommandResponse response = invoke(Command.checkconnection());
		if (response == null) {
			throw new ConnectionException("Ошибка при подключении");
		}

		final boolean connected = response.getReturnCode() == 0;
		device.setConnected(connected);
		if (connected) {
			fireDeviceConnected(device);
		}
		return connected;
	}

	/**
	 * Задает устройство для работы. Если перед этим уже было задано устройство,
	 * при его смене предыдущее устройство отключается {@link #connect()}
	 * 
	 * @param serialDevice
	 *            новое устройство
	 */
	default void setCurrentDevice(SerialDevice serialDevice) {
		fireDeviceChanged(serialDevice);
	}

	/**
	 * Получить текущее устройство
	 * 
	 * @return текущее устройство, или <code>null</code>, если устройство не
	 *         задано
	 */
	SerialDevice getCurrentDevice();

	/**
	 * Возвращает <code>true</code>, если устройство задано, иначе
	 * <code>null</code>
	 * 
	 * @return <code>true</code>, если устройство задано, <code>false</code> в
	 *         обратно случае
	 */
	default boolean isSpecifiedDevice() {
		return getCurrentDevice() != null;
	}

	/**
	 * Отключение устройства
	 * 
	 * @return <code>true</code>, если отключение прошло успешно
	 */
	default boolean disconnect() {
		SerialDevice device = getCurrentDevice();
		if (device == null) {
			throw new DeviceNotFoundException("Устройство не задано");
		}

		if (!device.isConnected()) {
			return true;
		}

		CommandResponse response = invoke(Command.closeconnection());
		if (response == null) {
			throw new ConnectionException("Ошибка при отключении");
		}
		final boolean disconnected = response.getReturnCode() == 0;
		device.setConnected(false);
		if (disconnected) {
			fireDeviceDisconnected(device);
		}
		return disconnected;
	}

	/**
	 * Запуск выполнения команды на SDK
	 * 
	 * @param command
	 * @return
	 */
	CommandResponse invoke(Command command);

	Collection<DeviceConnectedListener> getDeviceConnectedListeners();

	default void addDeviceConnectedListener(DeviceConnectedListener l) {
		getDeviceConnectedListeners().add(l);
	}

	default boolean removeDeviceConnectedListener(DeviceConnectedListener l) {
		return getDeviceConnectedListeners().remove(l);
	}

	default void fireDeviceConnected(SerialDevice device) {
		getDeviceConnectedListeners().forEach(l -> l.handle(device));
	}

	Collection<DeviceChangedListener> getDeviceChangedListeners();

	default void addDeviceChangedListener(DeviceChangedListener l) {
		getDeviceChangedListeners().add(l);
	}

	default boolean removeDeviceChangedListener(DeviceChangedListener l) {
		return getDeviceChangedListeners().remove(l);
	}

	default void fireDeviceChanged(SerialDevice device) {
		getDeviceChangedListeners().forEach(l -> l.handle(device));
	}

	Collection<DeviceDisconnectedListener> getDeviceDisconnectedListeners();

	default void addDeviceDisonnectedListener(DeviceDisconnectedListener l) {
		getDeviceDisconnectedListeners().add(l);
	}

	default boolean removeDeviceDisonnectedListener(DeviceDisconnectedListener l) {
		return getDeviceDisconnectedListeners().remove(l);
	}

	default void fireDeviceDisconnected(SerialDevice device) {
		getDeviceDisconnectedListeners().forEach(l -> l.handle(device));
	}

	/**
	 * Обработчик события при подключении устройства
	 * 
	 * @author nikit
	 *
	 */
	@FunctionalInterface
	interface DeviceConnectedListener {
		void handle(SerialDevice serialDevice);
	}

	/**
	 * Обработчик события при изменении текущего устройства
	 * 
	 * @author nikit
	 *
	 */
	@FunctionalInterface
	interface DeviceChangedListener {
		void handle(SerialDevice serialDevice);
	}

	/**
	 * Обработчик события отключения устройства
	 * 
	 * @author nikit
	 *
	 */
	@FunctionalInterface
	interface DeviceDisconnectedListener {
		void handle(SerialDevice serialDevice);
	}
}
