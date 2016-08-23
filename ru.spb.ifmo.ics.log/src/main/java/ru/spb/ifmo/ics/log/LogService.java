package ru.spb.ifmo.ics.log;

import java.util.Collection;

public interface LogService {

	enum Level {
		INFO, WARNING, ERROR
	}

	default void logGeneric(Level level, Tag tag, String message) {
		fireMessageHandled(level, tag, message);
	}

	default void logInfo(Tag tag, String message) {
		logGeneric(Level.INFO, tag, message);
	}

	default void logWarning(Tag tag, String message) {
		logGeneric(Level.WARNING, tag, message);
	}

	default void logError(Tag tag, String message) {
		logGeneric(Level.ERROR, tag, message);
	}

	Collection<MessageListener> getMessageListeners();

	default void addMessageListener(MessageListener l) {
		getMessageListeners().add(l);
	}

	default void removeMessageListener(MessageListener l) {
		getMessageListeners().remove(l);
	}

	default void fireMessageHandled(Level level, Tag tag, String message) {
		getMessageListeners().forEach(l -> l.apply(level, tag, message));
	}

	@FunctionalInterface
	interface MessageListener {
		void apply(Level level, Tag tag, String message);
	}
}
