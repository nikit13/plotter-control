package ru.spb.ifmo.ics.log;

public enum Tag {

	DEVICES("Устройства"), HISTORY("История"), FIGURE("Фигуры");

	private final String description;

	private Tag(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
