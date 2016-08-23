package ru.spb.ifmo.ics.control.view;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.part.ViewPart;

/**
 * Расширяет функциональность стандартного окна Eclipse. Методы, которые
 * добавлены в ExtendedViewPart призваны сделать разработку интерфейса более
 * удобной
 * 
 * @author nikit
 *
 */
public abstract class ExtendedViewPart extends ViewPart {

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceClass) {
		return (T) getViewSite().getService(serviceClass);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSelectedObject(ISelection selection, Class<T> objectClass) {
		if (selection instanceof IStructuredSelection) {
			return (T) ((IStructuredSelection) selection).getFirstElement();
		}
		return null;
	}
}
