package ru.spb.ifmo.ics.util.service;

import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IServiceLocator;

public abstract class GlobalServiceFactory<T> extends AbstractServiceFactory {

	private T instance;

	@Override
	public final Object create(@SuppressWarnings("rawtypes") Class serviceInterface,
	        IServiceLocator parentLocator, IServiceLocator locator) {
		if (instance == null) {
			instance = createNewInstance();
		}

		return instance;
	}

	public abstract T createNewInstance();

}
