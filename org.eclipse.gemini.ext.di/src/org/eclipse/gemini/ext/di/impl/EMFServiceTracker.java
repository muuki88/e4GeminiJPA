/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Filipp A. - Initial implementation
 *******************************************************************************/
package org.eclipse.gemini.ext.di.impl;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

@SuppressWarnings("rawtypes")
public class EMFServiceTracker implements ServiceTrackerCustomizer{
	private final BundleContext context;

	public EMFServiceTracker(BundleContext context) {
		this.context = context;
	}
	@Override
	public Object addingService(ServiceReference reference) {
		Bundle b = reference.getBundle();
		@SuppressWarnings("unchecked")
		Object service = b.getBundleContext().getService(reference);
//		String unitName = (String) reference.getProperty(EntityManagerFactoryBuilder.JPA_UNIT_NAME);
//		System.err.println(unitName);
		return service;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		context.ungetService(reference);
	}

}