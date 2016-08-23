package ru.spb.ifmo.ics.model;

import jssc.SerialPort;

/**
 * Представляет устройство, подключенное по последовательному порту
 * 
 * @author nikit
 *
 */
public interface SerialDevice {

	/**
	 * @return имя устройства
	 */
	String getName();

	/**
	 * @return порт
	 */
	SerialPort getPort();

	/**
	 * @return <code>true</code>, если устройство подключено
	 */
	boolean isConnected();

	/**
	 * @param connected
	 *            флаг подключения устройства
	 */
	void setConnected(final boolean connected);
}
