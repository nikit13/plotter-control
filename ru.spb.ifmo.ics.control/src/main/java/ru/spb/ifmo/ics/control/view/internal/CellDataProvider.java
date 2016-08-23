package ru.spb.ifmo.ics.control.view.internal;

import java.util.function.Function;

import org.eclipse.jface.viewers.ColumnLabelProvider;

public class CellDataProvider<T> extends ColumnLabelProvider {

	private Function<T, String> extractor;

	public CellDataProvider(Function<T, String> extractor) {
		this.extractor = extractor;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getText(Object element) {
		if (extractor == null) {
			return super.getText(element);
		} else {
			return extractor.apply((T) element);
		}
	}
}