package ru.spb.ifmo.ics.control.figure.internal;

import org.eclipse.swt.graphics.GC;

import ru.spb.ifmo.ics.control.figure.FigureDescriptor;
import ru.spb.ifmo.ics.model.Command;

/**
 * Дескриптор ромба
 * 
 * @author nikit
 *
 */
public class RhomFigureDescriptor implements FigureDescriptor {

	private final Drawer drawer = new RhomDrawer();

	@Override
	public Command draw(int width, int height) {
		return Command.printrhom((byte) normalizeWidth(width / 2),
		        (byte) normalizeHeight(height / 2));
	}

	@Override
	public String getName() {
		return "Ромб";
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	private static class RhomDrawer implements Drawer {

		@Override
		public void draw(GC canvas, int x, int y, int width, int height) {
			canvas.drawLine(x + width / 2, y, x + width, y + height / 2);
			canvas.drawLine(x + width, y + height / 2, x + width / 2, y
			        + height);
			canvas.drawLine(x + width / 2, y + height, x, y + height / 2);
			canvas.drawLine(x, y + height / 2, x + width / 2, y);
		}
	}
}
