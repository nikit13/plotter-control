package ru.spb.ifmo.ics.control;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import ru.spb.ifmo.ics.control.view.DevicesView;
import ru.spb.ifmo.ics.control.view.DrawFigureView;
import ru.spb.ifmo.ics.control.view.HistoryView;
import ru.spb.ifmo.ics.control.view.LogView;

public class ControlPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);

		IFolderLayout topLeft = layout.createFolder("topLeft",
		        IPageLayout.LEFT, 0.2f, layout.getEditorArea());
		topLeft.addView(DevicesView.ID);
		layout.getViewLayout(DevicesView.ID).setCloseable(false);

		IFolderLayout bottom = layout.createFolder("bottom",
		        IPageLayout.BOTTOM, 0.7f, layout.getEditorArea());
		bottom.addView(LogView.ID);
		layout.addShowViewShortcut(LogView.ID);
		layout.getViewLayout(LogView.ID).setCloseable(false);

		IFolderLayout bottomLeft = layout.createFolder("bottomLeft",
		        IPageLayout.BOTTOM, 0.5f, "topLeft");
		bottomLeft.addView(HistoryView.ID);
		layout.getViewLayout(HistoryView.ID).setCloseable(false);

		IFolderLayout top = layout.createFolder("top", IPageLayout.TOP, 0.3f,
		        layout.getEditorArea());
		top.addView(DrawFigureView.ID);
		layout.getViewLayout(DrawFigureView.ID).setCloseable(false);
	}
}
