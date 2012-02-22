package org.e4.gemini.rcp.dao;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.e4.gemini.test.data.DataObject;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.persistence.config.PersistenceUnitProperties;

public class DataObjectDAO {

	@Inject
	@GeminiPersistenceContext(unitName="puTest", properties = {
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_DRIVER, value="org.apache.derby.jdbc.EmbeddedDriver"),
			@GeminiPersistenceProperty(name=PersistenceUnitProperties.JDBC_URL, value="jdbc:derby:test;create=true"),
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
	private EntityManager entityManager;
	
	public void saveDataObject(DataObject dataObj){		
		EntityTransaction trx = entityManager.getTransaction();
		trx.begin();
		entityManager.persist(dataObj);
		trx.commit();
	}
	
	@PreDestroy
	public void destroy(){
		entityManager.close();
	}
}
