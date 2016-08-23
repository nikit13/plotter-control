package ru.spb.ifmo.ics.control.figure;

import ru.spb.ifmo.ics.control.figure.internal.LineFigureDescriptor;
import ru.spb.ifmo.ics.control.figure.internal.QuadFigureDescriptor;
import ru.spb.ifmo.ics.control.figure.internal.RhomFigureDescriptor;

public final class Figures {

	private static final FigureDescriptor QUAD = new QuadFigureDescriptor();

	private static final FigureDescriptor LINE = new LineFigureDescriptor();

	private static final FigureDescriptor RHOM = new RhomFigureDescriptor();

	private Figures() {
		// do nothing
	}

	/**
	 * @return дескриптор прямоугольника
	 */
	public static FigureDescriptor quad() {
		return QUAD;
	}

	/**
	 * @return дескриптор линии
	 */
	public static FigureDescriptor line() {
		return LINE;
	}

	/**
	 * @return дескриптор ромба
	 */
	public static FigureDescriptor rhom() {
		return RHOM;
	}

}
