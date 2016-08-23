package ru.spb.ifmo.ics.model;

public enum CommandOpcode {

	/**
	 * Печать числа
	 */
	PRINTNUM((byte) 0x10, "printnum"),

	/**
	 * Отрисовка линии
	 */
	PRINTLN((byte) 0x11, "println"),

	/**
	 * Отрисовка прямоугольника
	 */
	PRINTQUAD((byte) 0x12, "printquad"),

	/**
	 * Отрисовка ромба
	 */
	PRINTRHOM((byte) 0x13, "printrhom"),

	/**
	 * Перемещение плоттера
	 */
	MOVCOORDS((byte) 0x20, "movcoords"),

	/**
	 * Перемещение плоттера по оси X
	 */
	MOVX((byte) 0x21, "movx"),

	/**
	 * Перемещение плоттера по оси Y
	 */
	MOVY((byte) 0x22, "movy"),

	/**
	 * Установление соединения с SDK
	 */
	CHECKCONNECTION((byte) 0x30, "checkconnection"),

	/**
	 * Закрытие соединения с SDK
	 */
	CLOSECONNECTION((byte) 0x31, "closeconnection"),

	/**
	 * Пустая команда, не делает ничего. Должна всегда возвращать 0 в качестве
	 * результата. Для тестовых целей
	 */
	EMPTYCOMMAND((byte) 0x32, "emptycommand"),

	/**
	 * Загрузка команды из истории
	 */
	HISTORYLOAD((byte) 0x40, "historyload"),

	/**
	 * Исполнение команды из истории
	 */
	INVOKEHISTORY((byte) 0x41, "invokehistory"),

	/**
	 * Загрузка всех команд из истории
	 */
	HISTORYLOAD_ALL((byte) 0x42, "historyloadall"),

	/**
	 * Очистка истории
	 */
	HISTORYCLEAR((byte) 0x43, "historyclear"),

	/**
	 * Поднятие пишущего элемента плоттера
	 */
	PENUP((byte) 0x50, "penup"),

	/**
	 * Опускание пишущего элемента плоттера
	 */
	PENDOWN((byte) 0x51, "pendown");

	private final byte opcode;

	private final String name;

	private CommandOpcode(final byte opcode, final String name) {
		this.opcode = opcode;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public byte getOpcode() {
		return opcode;
	}

	/**
	 * Возвращает экземпляр {@link CommandOpcode} для переданного кода команды
	 * 
	 * @param opcode
	 *            код команды
	 * @return экземпляр {@link CommandOpcode}, или <code>null</code>, если
	 *         команды с таким кодом не существует
	 */
	public static CommandOpcode forOpcode(final byte opcode) {
		for (CommandOpcode commandOpcode : values()) {
			if (commandOpcode.getOpcode() == opcode) {
				return commandOpcode;
			}
		}
		return null;
	}
}
