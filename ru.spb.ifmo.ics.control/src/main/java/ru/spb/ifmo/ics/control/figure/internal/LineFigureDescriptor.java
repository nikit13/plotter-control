package ru.spb.ifmo.ics.control.figure.internal;

import org.eclipse.swt.graphics.GC;

import ru.spb.ifmo.ics.control.figure.FigureDescriptor;
import ru.spb.ifmo.ics.model.Command;

/**
 * Дескриптор линии
 * 
 * @author nikit
 *
 */
public class LineFigureDescriptor implements FigureDescriptor {

	private final Drawer drawer = new LineDrawer();

	@Override
	public Command draw(int width, int height) {
		return Command.println((byte) normalizeWidth(width),
		        (byte) normalizeHeight(height));
	}

	@Override
	public String getName() {
		return "Линия";
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	private static class LineDrawer implements Drawer {

		@Override
		public void draw(GC canvas, int x, int y, int width, int height) {
			canvas.drawLine(x, y, x + width, y + height);
		}

	}
}
