package ru.spb.ifmo.ics.control.figure.internal;

import org.eclipse.swt.graphics.GC;

import ru.spb.ifmo.ics.control.figure.FigureDescriptor;
import ru.spb.ifmo.ics.model.Command;

/**
 * Дескриптор прямоугольника
 * 
 * @author nikit
 *
 */
public class QuadFigureDescriptor implements FigureDescriptor {

	private final Drawer drawer = new QuadDrawer();

	@Override
	public Command draw(final int width, final int height) {
		return Command.printquad((byte) normalizeWidth(width),
		        (byte) normalizeHeight(height));
	}

	@Override
	public String getName() {
		return "Прямоугольник";
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	private static class QuadDrawer implements Drawer {

		@Override
		public void draw(GC canvas, int x, int y, int width, int height) {
			canvas.drawRectangle(x, y, width, height);
		}
	}

}
