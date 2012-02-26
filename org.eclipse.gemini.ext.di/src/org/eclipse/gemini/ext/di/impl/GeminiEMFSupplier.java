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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;

import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.di.internal.extensions.DIEActivator;
import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.gemini.ext.di.GeminiPersistenceUnit;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("restriction")
@Component(immediate = true, servicefactory = true)
public class GeminiEMFSupplier extends ExtendedObjectSupplier {

	private boolean trace = false;
	private ServiceTracker<?, ?> emfTracker;
	private Map<String, EntityManagerFactory> emfs = new HashMap<String, EntityManagerFactory>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostConstruct
	public void init() {
		trace = (System.getProperty("GEMINI_DEBUG") != null && System.getProperty("GEMINI_DEBUG").equals("true"));
		trace("INIT");
		if (emfTracker != null)
			return;
		EMFServiceTracker st = new EMFServiceTracker(getContext());
		emfTracker = new ServiceTracker(getContext(), EntityManagerFactory.class.getName(), st);
		emfTracker.open();
	}

	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor, boolean track, boolean group) {
		return getEMF(getUnitName(descriptor, requestor.getRequestingObjectClass()),
				getProperties(descriptor, requestor.getRequestingObjectClass()));
	}

	protected EntityManagerFactory getEMF(String unitName, Map<String, Object> emProperties) {
		if (emProperties.containsKey("REINIT")) {
			if (emfs.get(unitName) != null) {
				emfs.get(unitName).close();
				emfs.remove(unitName);
			}
		}

		if (emfs.get(unitName) != null) {
			if (emfs.get(unitName).isOpen()) {
				return emfs.get(unitName);
			} else {
				emfs.remove(unitName);
			}
		}

		EntityManagerFactoryBuilder emfb = lookupEntityManagerFactoryBuilder(unitName);
		if (emfb == null) {
			error("EntityManagerFactoryBuilder is null...");
			return null;
		}
		EntityManagerFactory emf = emfb.createEntityManagerFactory(emProperties);
		emfs.put(unitName, emf);
		return emf;
	}

	protected String getUnitName(IObjectDescriptor descriptor, Class<?> requestingObject) {
		if (descriptor == null)
			return null;
		GeminiPersistenceUnit qualifier = descriptor.getQualifier(GeminiPersistenceUnit.class);
		return qualifier.unitName();
	}

	protected Map<String, Object> getProperties(IObjectDescriptor descriptor, Class<?> requestingObject) {
		if (descriptor == null)
			return null;
		GeminiPersistenceUnit qualifier = descriptor.getQualifier(GeminiPersistenceUnit.class);
		Map<String, Object> properties = new HashMap<String, Object>();
		for (GeminiPersistenceProperty pp : qualifier.properties()) {
			if (pp.valuePref().value().length() > 0) {
				String val = DIEActivator.getDefault().getPreferencesService()
						.getString(getNodePath(pp.valuePref(), requestingObject), pp.valuePref().value(), null, null);
				properties.put(pp.name(), val);
			} else {
				properties.put(pp.name(), pp.value());
			}
		}
		trace(properties.toString());
		return properties;
	}

	protected String getNodePath(Preference preference, Class<?> requestingObject) {
		String nodePath = preference.nodePath();

		if (nodePath == null || nodePath.length() == 0) {
			if (requestingObject == null)
				return null;
			nodePath = FrameworkUtil.getBundle(requestingObject).getSymbolicName();
		}
		return nodePath;
	}

	protected BundleContext getContext() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		return bundle.getBundleContext();
	}

	public EntityManagerFactoryBuilder lookupEntityManagerFactoryBuilder(String puName) {
		String filter = "(osgi.unit.name=" + puName + ")";
		ServiceReference<?>[] refs = null;
		try {
			refs = getContext().getServiceReferences(EntityManagerFactoryBuilder.class.getName(), filter);
		} catch (InvalidSyntaxException isEx) {
			new RuntimeException("Bad filter", isEx);
		}
		trace("EMF Builder Service refs looked up from registry: " + refs);
		return (refs == null) ? null : (EntityManagerFactoryBuilder) getContext().getService(refs[0]);
	}

	@PreDestroy
	public void destroy() {
		trace("DESTROY");
		try {
			emfTracker.close();
		} finally {
			emfTracker = null;
		}
		Iterator<Entry<String, EntityManagerFactory>> it = emfs.entrySet().iterator();
		while (it.hasNext()) {
			try {
				it.next().getValue().close();
			} catch (Exception e) {
			}
		}
		emfs.clear();
	}

	protected void trace(String str) {
		if (!trace)
			return;
		System.err.println("[GEMINI_EXT] " + str);
	}

	protected void error(String str) {
		System.err.println("[GEMINI_EXT] " + str);
	}
}
