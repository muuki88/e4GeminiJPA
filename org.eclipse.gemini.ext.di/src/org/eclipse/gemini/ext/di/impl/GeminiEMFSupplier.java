/*******************************************************************************
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Filipp A. - Initial implementation
 *     Nepomuk Seiler - Use of declarative services
 *******************************************************************************/
package org.eclipse.gemini.ext.di.impl;

import java.util.HashMap;
import java.util.Map;

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
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;

@Component(immediate = true, servicefactory = true)
public class GeminiEMFSupplier extends ExtendedObjectSupplier {

	private boolean trace = false;
	private Map<String, EntityManagerFactory> emfs = new HashMap<String, EntityManagerFactory>();
	private Map<String, EntityManagerFactoryBuilder> emfbs = new HashMap<String, EntityManagerFactoryBuilder>();

	/* ================================================ */
	/* =========== Eclipse e4 DI Extender ============ */
	/* ================================================ */

	@PostConstruct
	public void init() {
		trace = (System.getProperty("GEMINI_DEBUG") != null && System.getProperty("GEMINI_DEBUG").equals("true"));
		trace("INIT");
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

		EntityManagerFactoryBuilder emfb = lookupEntityManagerFactoryBuilder(unitName);
		if (emfb == null) {
			error("EntityManagerFactoryBuilder is null...");
			return null;
		}
		
		//Catch to start up the application, even the db connection failed
		try {
			EntityManagerFactory emf = emfb.createEntityManagerFactory(emProperties);
			emfs.put(unitName, emf);
			return emf;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Extracts the persistenceUnit name by searching for the annotation
	 * \@GeminiPersistenceUnit.
	 * 
	 * @param descriptor
	 * @param requestingObject
	 * @return unitName or null
	 */
	protected String getUnitName(IObjectDescriptor descriptor, Class<?> requestingObject) {
		if (descriptor == null)
			return null;
		GeminiPersistenceUnit qualifier = descriptor.getQualifier(GeminiPersistenceUnit.class);
		return qualifier.unitName();
	}

	/**
	 * Extracts the persistenceUnit name from the service properties map
	 * 
	 * @param properties
	 * @return unitName or null
	 */
	protected String getUnitName(Map<String, String> properties) {
		return properties.get("osgi.unit.name");
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

	public EntityManagerFactoryBuilder lookupEntityManagerFactoryBuilder(String puName) {
		return emfbs.get(puName);
	}

	@PreDestroy
	public void destroy() {
		for (EntityManagerFactory emf : emfs.values()) {
			try {
				if (emf.isOpen())
					emf.close();
			} catch (IllegalStateException e) {
				trace("Error while closing EntityManagerFactory." + e.getMessage());
			} catch (Exception e) {
				throw new RuntimeException("Error while closing EntityManagerFactory." + e.getMessage(), e);
			}
		}
		emfs.clear();
	}

	/* ================================================ */
	/* ======== Declarative Service Component ========= */
	/* ================================================ */

	protected void activate() {
		trace("GeminiEMFSupplier service activated");
	}

	protected void bindEntityManagerFactory(EntityManagerFactory emf, Map<String, String> properties) {
		trace("GeminiEMFSupplier.bindEntityManagerFactory() with " + properties);
		emfs.put(getUnitName(properties), emf);
	}

	protected void unbindEntityManagerFactory(EntityManagerFactory emf, Map<String, String> properties) {
		trace("GeminiEMFSupplier.unbindEntityManagerFactory() with " + properties);
		emf.close();
		emfs.remove(getUnitName(properties));
	}

	protected void bindEntityManagerFactoryBuilder(EntityManagerFactoryBuilder emfb, Map<String, String> properties) {
		trace("GeminiEMFSupplier.bindEntityManagerFactoryBuilder() with " + properties);
		emfbs.put(getUnitName(properties), emfb);
	}

	protected void unbindEntityManagerFactoryBuilder(EntityManagerFactoryBuilder emfb, Map<String, String> properties) {
		trace("GeminiEMFSupplier.unbindEntityManagerFactoryBuilder() with " + properties);
		EntityManagerFactoryBuilder old = emfbs.remove(getUnitName(properties));
		if (old != emfb) {
			throw new RuntimeException("Error while removing EntityManagerFactoryBuilder. Not identical! ");
		}
	}

	/* ================================================ */
	/* ========== Simple Debugging Options ============ */
	/* ================================================ */

	protected void trace(String str) {
		if (!trace)
			return;
		System.err.println("[GEMINI_EXT] " + str);
	}

	protected void error(String str) {
		System.err.println("[GEMINI_EXT] " + str);
	}
}
