package ru.spb.ifmo.ics.log;

import ru.spb.ifmo.ics.log.internal.DefaultLogService;
import ru.spb.ifmo.ics.util.service.GlobalServiceFactory;

public class LogServiceFactory extends GlobalServiceFactory<LogService> {

	@Override
	public LogService createNewInstance() {
		return new DefaultLogService();
	}

}
