package ru.spb.ifmo.ics.model;

/**
 * Представляет запись из истории выпонения команд
 * 
 * @author nikit
 *
 */
public class HistoryRecord {

	private final CommandOpcode opcode;

	private final byte[] args;

	/**
	 * @param opcode
	 *            код команды
	 * @param args
	 *            аргументы
	 */
	public HistoryRecord(CommandOpcode opcode, byte... args) {
		if (opcode == null) {
			throw new IllegalArgumentException("Код команды не может быть null");
		}
		this.opcode = opcode;
		this.args = args;
	}

	/**
	 * @param opcode
	 *            код команды
	 * @param args
	 *            аргументы
	 */
	public HistoryRecord(final byte opcode, byte... args) {
		this(CommandOpcode.forOpcode(opcode), args);
	}

	/**
	 * @param command
	 */
	public HistoryRecord(Command command) {
		this(command.getOpcode(), command.getArgs());
	}

	/**
	 * @return код команды
	 */
	public CommandOpcode getOpcode() {
		return opcode;
	}

	/**
	 * @return аргументы
	 */
	public byte[] getArgs() {
		return args;
	}

	public byte[] toArray() {
		final byte[] result = new byte[(args == null) ? 0 : args.length + 1];
		result[0] = opcode.getOpcode();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				result[i + 1] = args[i];
			}
		}
		return result;
	}

	/**
	 * Создает команду на основе записи, готовую к исполнению
	 * 
	 * @return экземпляр {@link Command} команды, готовой к исполнению
	 */
	public Command createCommand() {
		return Command.generic(opcode, args);
	}
}
