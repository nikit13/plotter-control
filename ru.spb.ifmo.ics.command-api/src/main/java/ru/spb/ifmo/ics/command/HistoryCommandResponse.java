package ru.spb.ifmo.ics.command;

import ru.spb.ifmo.ics.model.HistoryRecord;


public class HistoryCommandResponse extends CommandResponse {

	private final HistoryRecord[] records;

	public HistoryCommandResponse(byte resultCode, HistoryRecord... records) {
	    super(resultCode);
		this.records = records;
    }

	public HistoryRecord[] getRecords() {
	    return records;
    }
}
