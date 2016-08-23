package ru.spb.ifmo.ics.control.figure;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import ru.spb.ifmo.ics.model.Command;

/**
 * Представляет описание геометрической фигуры и задает способ рисования фигуры
 * по размерам выделенной области
 * 
 * @author nikit
 *
 */
public interface FigureDescriptor {

	int DEFAULT_MAX_WIDTH = 127;

	int DEFAULT_MAX_HEIGHT = 127;

	int DEFAULT_MIN_WIDTH = -128;

	int DEFAULT_MIN_HEIGHT = -128;

	default int getMaxWidth() {
		return DEFAULT_MAX_WIDTH;
	}

	default int getMaxHeight() {
		return DEFAULT_MAX_HEIGHT;
	}

	default int getMinWidth() {
		return DEFAULT_MIN_WIDTH;
	}

	default int getMinHeight() {
		return DEFAULT_MIN_HEIGHT;
	}

	/**
	 * @return имя фигуры
	 */
	String getName();

	/**
	 * Возвращает набор команд для рисования заданной фигуры
	 * 
	 * @param width
	 *            ширина выделенной области
	 * @param height
	 *            высота выделенной области
	 * @return набор команд для рисования
	 */
	Command draw(final int width, final int height);

	/**
	 * @return объект, умеющий рисовать данную фигуру на объекте
	 *         {@link Composite} на заданной области
	 */
	Drawer getDrawer();

	default int normalizeWidth(final int width) {
		return normalize(width, getMinWidth(), getMaxWidth());
	}

	default int normalizeHeight(final int height) {
		return normalize(height, getMinHeight(), getMaxHeight());
	}

	default int normalize(final int value, final int minValue,
	        final int maxValue) {
		return (value < minValue) ? minValue : (value > maxValue) ? maxValue
		        : value;
	}

	/**
	 * Определяет объект, который умеет рисовать фигуры на компоненте
	 * 
	 * @author nikit
	 *
	 */
	@FunctionalInterface
	interface Drawer {
		void draw(GC canvas, int x, int y, int width, int height);
	}
}
