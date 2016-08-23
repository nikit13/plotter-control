package ru.spb.ifmo.ics.control.view;

import java.util.Arrays;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public abstract class PlotterControlTableView extends PlotterControlView {

	private TableViewer viewer;

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.V_SCROLL | SWT.BORDER | SWT.SINGLE);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		final Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		Arrays.stream(createColumns()).forEach(c -> createColumn(viewer, c));

		getSite().setSelectionProvider(viewer);
	}

	@Override
	public void setFocus() {
		viewer.refresh();
	}

	@Override
	public void dispose() {
		super.dispose();
		viewer = null;
	}

	protected TableViewer getTableViewer() {
		return viewer;
	}

	protected abstract ColumnDescriptor[] createColumns();

	protected TableViewerColumn createColumn(TableViewer parent,
	        ColumnDescriptor columnDesc) {
		TableViewerColumn column = new TableViewerColumn(parent, SWT.NONE);
		column.getColumn().setWidth(columnDesc.getWidth());
		column.getColumn().setText(columnDesc.getName());
		column.setLabelProvider(columnDesc.getProvider());
		return column;
	}

	public static class ColumnDescriptor {

		private final String name;

		private final int width;

		private final CellLabelProvider provider;

		public ColumnDescriptor(String name, int width,
		        CellLabelProvider provider) {
			this.name = name;
			this.width = width;
			this.provider = provider;
		}

		public String getName() {
			return name;
		}

		public int getWidth() {
			return width;
		}

		public CellLabelProvider getProvider() {
			return provider;
		}
	}
}
