package ru.spb.ifmo.ics.command.internal;

import java.util.LinkedList;
import java.util.List;

import ru.spb.ifmo.ics.command.CommandResponse;
import ru.spb.ifmo.ics.command.HistoryCommandResponse;
import ru.spb.ifmo.ics.model.Command;
import ru.spb.ifmo.ics.model.CommandOpcode;
import ru.spb.ifmo.ics.model.HistoryRecord;

/**
 * Заглушка для сервиса исполнения команд, имитирует отправку команд на
 * устройство и получение результатов. Предназначен для тестирования, жестко
 * задает логику ответов на команды
 * 
 * @author nikit
 *
 */
public class CommandServiceStub extends AbstractCommandService {

	private static final List<HistoryRecord> HISTORY = new LinkedList<>();

	private boolean isConnected;

	@Override
	public CommandResponse invoke(Command command) {
		CommandResponse response = null;
		switch (CommandOpcode.forOpcode(command.getOpcode())) {
		case CHECKCONNECTION:
			response = new CommandResponse((byte) ((isConnected) ? 0x11 : 0x00));
			isConnected = true;
			break;
		case CLOSECONNECTION:
			response = new CommandResponse((byte) ((isConnected) ? 0x00 : 0x11));
			isConnected = false;
			break;
		case EMPTYCOMMAND:
			response = new CommandResponse((byte) 0x00);
			HISTORY.add(new HistoryRecord(command));
			break;
		case HISTORYCLEAR:
			HISTORY.clear();
			response = new CommandResponse((byte) 0x00);
			break;
		case HISTORYLOAD: {
			byte[] args = command.getArgs();
			if (args == null || args.length != 1) {
				// не хватает аргументов
				response = new CommandResponse((byte) 0x10);
			} else {
				final byte n = args[0];
				if (n < 1 || n > HISTORY.size()) {
					// неверный номер команды
					response = new CommandResponse((byte) 0x11);
				} else {
					// если все хорошо
					HistoryRecord historyRecord = HISTORY.get(n);
					response = new CommandResponse((byte) 0x00,
					        historyRecord.toArray());
				}
			}
			break;
		}
		case HISTORYLOAD_ALL: {
			response = new HistoryCommandResponse((byte) 0x00,
			        HISTORY.toArray(new HistoryRecord[0]));
			break;
		}
		case INVOKEHISTORY: {
			byte[] args = command.getArgs();
			if (args == null || args.length != 1) {
				// не хватает аргументов
				response = new CommandResponse((byte) 0x10);
			} else {
				final byte n = args[0];
				if (n < 1 || n > HISTORY.size()) {
					// неверный номер команды
					response = new CommandResponse((byte) 0x11);
				} else {
					HistoryRecord record = HISTORY.get(n);
					response = invoke(record.createCommand());
				}
			}
			break;
		}
		case MOVCOORDS:
		case MOVX:
		case MOVY:
		case PENDOWN:
		case PENUP:
		case PRINTLN:
		case PRINTNUM:
		case PRINTQUAD:
		case PRINTRHOM:
			HISTORY.add(new HistoryRecord(command));
			response = new CommandResponse((byte) 0x00);
			break;
		default:
			response = null;
			break;
		}
		return response;
	}

}
