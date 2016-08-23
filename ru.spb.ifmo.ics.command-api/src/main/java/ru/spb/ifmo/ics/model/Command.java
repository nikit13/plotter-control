package ru.spb.ifmo.ics.model;

import static ru.spb.ifmo.ics.model.CommandOpcode.*;

/**
 * Представляет команду плоттера, которая принимается и исполняется SDK1.1
 * 
 * @author nikit
 *
 */
public final class Command {

	private final CommandOpcode opcode;

	private final byte[] args;

	/**
	 * Создание экземпляра команды
	 * 
	 * @param opcode
	 *            код команды
	 * @param args
	 *            аргументы
	 */
	private Command(CommandOpcode opcode, byte... args) {
		if (opcode == null) {
			throw new IllegalArgumentException("Код команды не может быть null");
		}
		this.opcode = opcode;
		this.args = args.clone();
	}

	/**
	 * Возвращает код команды. Коды делятся на несколько групп: <br>
	 * 1. print commands (0x1) - команды печати символов и фигур <br>
	 * 2. motion commands (0x2) - команды передвижения плоттера <br>
	 * 3. special commands (0x3) - специальные команды для управления SDK1.1 <br>
	 * 4. history commands (0x4) - команды для работы с историей <br>
	 * 5. pen commands (0x5) - команды для пишущего элемента плоттера
	 * 
	 * @return код команды
	 */
	public byte getOpcode() {
		return opcode.getOpcode();
	}

	/**
	 * Возвращает список аргументов команды
	 * 
	 * @return список аргументов. ЕСли команда не имеет аргументов, возвращается
	 *         <code>null</code>
	 */
	public byte[] getArgs() {
		return args.clone();
	}

	/**
	 * Команда печати числа. Принимает цифру для печати
	 * 
	 * @param number
	 *            печатаемая цифра
	 */
	public static Command printnum(byte number) {
		return generic(PRINTNUM, number);
	}

	/**
	 * Команда отрисовки линии. В качестве аргументов принимаются смещения по
	 * осям (могут быть отрицательными)
	 * 
	 * @param dx
	 *            смещение по оси X
	 * @param dy
	 *            смещение по оси Y
	 */
	public static Command println(byte dx, byte dy) {
		return generic(PRINTLN, dx, dy);
	}

	/**
	 * Команда отрисовки прямоугольника. Рисование начинается с текущей позиции
	 * плоттера. В качестве аргументов принимаются смещения по осям (могут быть
	 * отрицательными). Смещения определяют длину и ширину прямоугольника
	 * 
	 * @param dx
	 *            ширина прямоугольника
	 * @param dy
	 *            высота прямоугольника
	 */
	public static Command printquad(byte dx, byte dy) {
		return generic(PRINTQUAD, dx, dy);
	}

	/**
	 * Команда отрисовки ромба. Рисование начинается с текущей позиции плоттера.
	 * В качестве аргументов принимаются смещения по осям (<b>не</b> могут быть
	 * отрицательными). Представляют расстояния от центра ромба до вершин
	 * 
	 * @param dx
	 *            смещение по оси X
	 * @param dy
	 *            смещение по оси Y
	 */
	public static Command printrhom(byte dx, byte dy) {
		return generic(PRINTRHOM, dx, dy);
	}

	/**
	 * Команда перемещения плоттера. В качестве аргументов принимает смещения по
	 * осям (могут быть отрицательными). При выполнении команды <b>не</b>
	 * учитывается позиция пишущего элемента (при необходимости поднять или
	 * опустить пишущий элемент)
	 * 
	 * @param dx
	 *            смещение по оси X
	 * @param dy
	 *            смещение по оси Y
	 */
	public static Command movcoords(byte dx, byte dy) {
		return generic(MOVCOORDS, dx, dy);
	}

	/**
	 * Команда перемещения плоттера по оси X. В качестве аргумента принимает
	 * смещение по оси (может быть отрицательным). При выполнении команды
	 * <b>не</b> учитывается позиция пишущего элемента (при необходимости
	 * поднять или опустить пишущий элемент)
	 * 
	 * @param dx
	 *            смещение по оси X
	 */
	public static Command movx(byte dx) {
		return generic(MOVX, dx);
	}

	/**
	 * Команда перемещения плоттера по оси Y. В качестве аргумента принимает
	 * смещение по оси (может быть отрицательным). При выполнении команды
	 * <b>не</b> учитывается позиция пишущего элемента (при необходимости
	 * поднять или опустить пишущий элемент)
	 * 
	 * @param dy
	 *            смещение по оси Y
	 */
	public static Command movy(byte dy) {
		return generic(MOVY, dy);
	}

	/**
	 * Команда проверки соединения с SDK1.1 и установка соединения при успешном
	 * обнаружении SDK1.1
	 */
	public static Command checkconnection() {
		return generic(CHECKCONNECTION);
	}

	/**
	 * Команда отключения от SDK1.1
	 */
	public static Command closeconnection() {
		return generic(CLOSECONNECTION);
	}

	/**
	 * @param n
	 */
	public static Command historyload(byte n) {
		return generic(HISTORYLOAD, n);
	}

	/**
	 */
	public static Command historyloadall() {
		return generic(HISTORYLOAD_ALL);
	}

	/**
	 * @param n
	 */
	public static Command invokehistory(byte n) {
		return generic(INVOKEHISTORY, n);
	}

	/**
	 */
	public static Command historyclear() {
		return generic(HISTORYCLEAR);
	}

	/**
	 * Команда поднятия пишущего элемента
	 */
	public static Command penup() {
		return generic(PENUP);
	}

	/**
	 * Команда опускания пишущего элемента
	 */
	public static Command pendown() {
		return generic(PENDOWN);
	}

	/**
	 * Создает новый экземпляр команды по указанному коду команды и аргументам
	 * 
	 * @param opcode
	 * @param args
	 * @return
	 */
	public static Command generic(CommandOpcode opcode, byte... args) {
		return new Command(opcode, args);
	}
}
