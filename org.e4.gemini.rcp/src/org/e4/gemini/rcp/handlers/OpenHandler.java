/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.e4.gemini.rcp.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;

import org.e4.gemini.rcp.dao.DataObjectDAO;
import org.e4.gemini.test.data.DataObject;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.gemini.ext.di.GeminiPersistenceUnit;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.swt.widgets.Shell;

public class OpenHandler {
	
	//com.mysql.jdbc.Driver
	//org.gjt.mm.mysql.Driver
	
	@Inject
	@GeminiPersistenceUnit(unitName="puTest", properties = {
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_DRIVER, value = "com.mysql.jdbc.Driver"),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_URL, value = "jdbc:mysql://127.0.0.1/test"),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_USER, value = "test"),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_PASSWORD, value = "test"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.DDL_GENERATION, value=PersistenceUnitProperties.DROP_AND_CREATE),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.DDL_GENERATION_MODE, value=PersistenceUnitProperties.DDL_DATABASE_GENERATION),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.CONNECTION_POOL_MIN, value="20"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.TEMPORAL_MUTABLE, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.WEAVING, value="false"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.WEAVING_INTERNAL, value="false"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_LEVEL, value="FINEST"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_TIMESTAMP, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_SESSION, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_THREAD, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_EXCEPTIONS, value="true")
	})
	EntityManagerFactory emf;
	
	@Inject
	@GeminiPersistenceUnit(unitName="puTest2", properties = {
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_DRIVER, valuePref=@Preference(value="jdbc_driver")),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_URL, valuePref=@Preference(value="jdbc_url")),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_USER, valuePref=@Preference(value="jdbc_user")),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_PASSWORD, valuePref=@Preference(value="jdbc_password")),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.DDL_GENERATION, value=PersistenceUnitProperties.DROP_AND_CREATE),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.DDL_GENERATION_MODE, value=PersistenceUnitProperties.DDL_DATABASE_GENERATION),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.CONNECTION_POOL_MIN, value="20"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.TEMPORAL_MUTABLE, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.WEAVING, value="false"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.WEAVING_INTERNAL, value="false"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_LEVEL, value="FINEST"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_TIMESTAMP, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_SESSION, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_THREAD, value="true"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.LOGGING_EXCEPTIONS, value="true")
	})
	EntityManagerFactory emf1;
	
	
	@Inject
	DataObjectDAO dao;
	
	@Execute
	public void execute(IEclipseContext context, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws InvocationTargetException, InterruptedException {

		try {
			System.out.println("entityManagerFactory:" + emf);
			System.out.println("entityManagerFactory 1:" + emf1);
			
			
			DataObject dataObj = new DataObject();
			dataObj.setDummy("bla bla");
			dao.saveDataObject(dataObj);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
