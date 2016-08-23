package ru.spb.ifmo.ics.command;

public class CommandResponse {

	private final byte resultCode;
	private final byte[] data;

	public CommandResponse(byte resultCode, byte... data) {
		this.resultCode = resultCode;
		this.data = (data != null) ? data.clone() : new byte[0];
	}

	/**
	 * Код возврата
	 * 
	 * @return код результата выполнения команды
	 */
	public byte getReturnCode() {
		return resultCode;
	}

	/**
	 * Байты результата выполнения команды
	 * 
	 * @return байты результата
	 */
	public byte[] getResultData() {
		return data.clone();
	}
}
