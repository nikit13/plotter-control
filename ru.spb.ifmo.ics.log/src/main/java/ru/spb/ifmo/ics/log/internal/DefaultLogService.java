package ru.spb.ifmo.ics.log.internal;

import java.util.Collection;
import java.util.HashSet;

import ru.spb.ifmo.ics.log.LogService;

public class DefaultLogService implements LogService {

	private Collection<MessageListener> listeners;

	public DefaultLogService() {
		listeners = new HashSet<>();
	}

	@Override
	public Collection<MessageListener> getMessageListeners() {
		return listeners;
	}
}
