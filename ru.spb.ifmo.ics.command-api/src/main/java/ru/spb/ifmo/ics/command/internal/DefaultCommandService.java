package ru.spb.ifmo.ics.command.internal;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import ru.spb.ifmo.ics.command.CommandResponse;
import ru.spb.ifmo.ics.command.ConnectionException;
import ru.spb.ifmo.ics.command.DeviceException;
import ru.spb.ifmo.ics.model.Command;
import ru.spb.ifmo.ics.model.SerialDevice;

public class DefaultCommandService extends AbstractCommandService {

	private static final int PORT_TIMEOUT = 10000;

	@Override
	public CommandResponse invoke(Command command) {
		final SerialDevice currentDevice = getCurrentDevice();
		if (currentDevice == null || !currentDevice.isConnected()) {
			throw new DeviceException("Отсутствует подключение к устройству");
		}

		final byte cmd = command.getOpcode();
		byte[] args = command.getArgs();
		byte dataSize = (byte) ((args == null) ? 0 : args.length);

		SerialPort port = currentDevice.getPort();
		byte[] sendPacket = new byte[dataSize + 2];
		sendPacket[0] = cmd;
		sendPacket[1] = dataSize;
		if (args != null && args.length > 0) {
			int index = 2;
			for (byte data : args) {
				sendPacket[index++] = data;
			}
		}
		try {
			port.writeBytes(sendPacket);
		} catch (SerialPortException e) {
			throw new ConnectionException("Ошибка при отправке данных в порт "
			        + e.getPortName() + ". Причина: " + e.getExceptionType(), e);
		}

		try {
			System.out.println("receive result");
			byte[] bytes = port.readBytes(2, PORT_TIMEOUT);
			final byte resultCode = bytes[0];
			dataSize = bytes[1];
			System.out.println("result code : " + resultCode + ", dataSize: "
			        + dataSize);
			byte[] data = null;
			if (dataSize > 0) {
				data = port.readBytes(dataSize, PORT_TIMEOUT);
			}
			return new CommandResponse(resultCode, data);
		} catch (SerialPortException e) {
			throw new ConnectionException(
			        "Ошибка при чтении результатов из порта " + e.getPortName()
			                + ". Причина: " + e.getExceptionType(), e);
		} catch (SerialPortTimeoutException e) {
			throw new ConnectionException(
			        "Истекло время ожидания результата из порта "
			                + e.getPortName(), e);
		}
	}
}
