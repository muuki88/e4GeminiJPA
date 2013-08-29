package de.mukis.gemini.sample.rcp.dao;

import java.sql.SQLException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperties;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import de.mukis.gemini.sample.model.Person;

@Creatable
public class PersonDAO {

	@Inject
	@GeminiPersistenceContext(unitName = "unconfigured2", properties = {
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_DRIVER, valuePref = @Preference("jdbc_driver")),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_URL, valuePref = @Preference("jdbc_url")),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.LOGGING_LEVEL, value = "FINE"),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING, value = "false"),
			@GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING_INTERNAL, value = "false"),
			@GeminiPersistenceProperty(name = GeminiPersistenceProperties.GEMINI_REINIT, valuePref = @Preference("jdbc_reconnect")) })
	private EntityManager em;

	@Inject
	private void updatePreferences(@Preference IEclipsePreferences preferences) {
		preferences.putBoolean("jdbc_reconnect", false);
	}

	public void save(Person dataObj) throws SQLException {
		checkConnection();
		EntityTransaction trx = em.getTransaction();
		trx.begin();
		em.persist(dataObj);
		trx.commit();
	}

	@PreDestroy
	public void destroy() {
		if (em != null && em.isOpen()) {
			em.close();
		}
	}

	private void checkConnection() throws SQLException {
		if (em == null) {
			throw new SQLException("EntityManager is null. Not connected to database!");
		}
	}
}
