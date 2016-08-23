package ru.spb.ifmo.ics.serial;

import java.util.Collection;

import ru.spb.ifmo.ics.model.SerialDevice;

/**
 * Сервис для управления последовательными портами
 * 
 * @author nikit
 *
 */
public interface SerialService {

	/**
	 * Получить список доступных портов для подключения. При повторном вызове
	 * вернет тот же набор портов, для перезагрузки следует вызвать
	 * {@link #reloadDevices()}
	 * 
	 * @return список доступных портов
	 */
	SerialDevice[] getDevices();

	/**
	 * Обновляет список портов. После обновления их можно будет получить вызовом
	 * {@link #getDevices()}
	 */
	default void reloadDevices() {
		fireDevicesListChanged();
	}

	/**
	 * Добавить обработчик изменения списка доступных портов
	 * 
	 * @param l
	 *            обработчик
	 */
	void addDeviceListChangedListener(DeviceListChangedListener l);

	boolean removeDeviceListChangedListener(DeviceListChangedListener l);
	
	Collection<DeviceListChangedListener> getDeviceListChangedListeners();

	default void fireDevicesListChanged() {
		getDeviceListChangedListeners().forEach(l -> l.devicesChanged(getDevices()));
	}
	
	/**
	 * Слушатель изменения списка доступных устройств
	 * 
	 * @author nikit
	 *
	 */
	@FunctionalInterface
	interface DeviceListChangedListener {

		/**
		 * Реакция на изменение списка доступных устройств
		 * 
		 * @param devices
		 *            новый список устройств
		 */
		void devicesChanged(SerialDevice[] devices);
	}
}
