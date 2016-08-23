package ru.spb.ifmo.ics.control.view;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISelectionService;

import ru.spb.ifmo.ics.command.CommandResponse;
import ru.spb.ifmo.ics.command.CommandService;
import ru.spb.ifmo.ics.control.figure.FigureDescriptor;
import ru.spb.ifmo.ics.control.figure.Figures;
import ru.spb.ifmo.ics.control.view.internal.CellDataProvider;
import ru.spb.ifmo.ics.log.Tag;
import ru.spb.ifmo.ics.model.SerialDevice;

/**
 * Окно рисования фигур
 * 
 * @author nikit
 *
 */
public class DrawFigureView extends PlotterControlView {
	public static final String ID = "ru.spb.ifmo.ics.view.drawFigureView";

	private Group drawGroup;

	private Figure figure;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		
		drawGroup = new Group(parent, SWT.BORDER);
		final GridData groupData = new GridData(SWT.FILL, SWT.FILL, true, true);
		groupData.verticalSpan = 3;
		drawGroup.setLayoutData(groupData);

		DrawFigureListener drawFigureListener = new DrawFigureListener();
		drawGroup.addPaintListener(drawFigureListener);
		drawGroup.addMouseMoveListener(drawFigureListener);
		drawGroup.addDragDetectListener(drawFigureListener);
		drawGroup.addMouseListener(drawFigureListener);

		final Label figureTypeLabel = new Label(parent, SWT.NONE);
		figureTypeLabel.setText("Выберите тип фигуры:");
		figureTypeLabel.setLayoutData(new GridData(SWT.BEGINNING,
		        SWT.BEGINNING, false, false));

		final ComboViewer figureType = new ComboViewer(parent, SWT.NONE);
		figureType.setContentProvider(ArrayContentProvider.getInstance());
		figureType.setLabelProvider(new CellDataProvider<FigureDescriptor>(
		        f -> f.getName()));
		figureType.setInput(new FigureDescriptor[] { Figures.line(),
		        Figures.quad(), Figures.rhom() });
		figureType.getCombo().setLayoutData(
		        new GridData(SWT.FILL, SWT.BEGINNING, false, false));

		final Button drawFigure = new Button(parent, SWT.PUSH);
		drawFigure.setText("Нарисовать фигуру");
		drawFigure.setLayoutData(new GridData(SWT.CENTER, SWT.BEGINNING, false,
		        false));
		drawFigure.addSelectionListener(new StartDrawSelectionListener());

		getSite().setSelectionProvider(figureType);

		getService(ISelectionService.class).addSelectionListener(ID,
		        (part, selection) -> drawGroup.redraw());

		figureType.setSelection(new StructuredSelection(Figures.line()));
	}

	@Override
	public void setFocus() {
		drawGroup.redraw();
	}

	private class StartDrawSelectionListener extends SelectionAdapter {

		@Override
		public void widgetSelected(SelectionEvent e) {
			try {
				if (figure == null) {
					throw new IllegalStateException(
					        "Фигура для рисования не задана");
				}

				CommandService commandService = getCommandService();
				SerialDevice currentDevice = commandService.getCurrentDevice();
				if (currentDevice == null || !currentDevice.isConnected()) {
					throw new IllegalStateException(
					        "Устройство для рисования не подключено");
				}

				ISelectionService selectionService = getService(ISelectionService.class);
				FigureDescriptor descriptor = getSelectedObject(
				        selectionService.getSelection(), FigureDescriptor.class);
				if (descriptor != null) {
					getLogService().logInfo(Tag.FIGURE,
					        "Рисуется фигура \"" + descriptor.getName() + "\"");
					CommandResponse response = commandService.invoke(descriptor
					        .draw(figure.getWidth(), figure.getHeight()));
					getLogService().logInfo(
					        Tag.FIGURE,
					        "Код возврата - 0x"
					                + Integer.toHexString(response
					                        .getReturnCode()));
				}
			} catch (RuntimeException ex) {
				getLogService().logError(Tag.FIGURE, ex.getMessage());
			}
		}
	}

	/**
	 * Слушатель для рисования фигуры на окне
	 * 
	 * @author nikit
	 *
	 */
	private class DrawFigureListener implements MouseListener,
	        MouseMoveListener, PaintListener, DragDetectListener {

		private boolean dragDetected;

		private int x;

		private int y;

		private int width;

		private int height;

		@Override
		public void paintControl(PaintEvent e) {
			if (dragDetected) {
				ISelection selection = getService(ISelectionService.class)
				        .getSelection();
				FigureDescriptor selected = (FigureDescriptor) ((StructuredSelection) selection)
				        .getFirstElement();
				if (selected != null) {
					selected.getDrawer().draw(e.gc, x, y, width, height);
				}
			}
		}

		@Override
		public void dragDetected(DragDetectEvent e) {
			figure = null;
			x = e.x;
			y = e.y;
			dragDetected = true;
		}

		@Override
		public void mouseUp(MouseEvent e) {
			dragDetected = false;
			figure = new Figure(width, height);
			x = 0;
			y = 0;
		}

		@Override
		public void mouseMove(MouseEvent e) {
			if (dragDetected) {
				width = e.x - x;
				height = e.y - y;
				drawGroup.redraw();
			}
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			// не используется
		}

		@Override
		public void mouseDown(MouseEvent e) {
			// не используется
		}
	}

	private static class Figure {
		private final int width;
		private final int height;

		public Figure(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}
}
