package de.mukis.gemini.sample.rcp.dao;

import java.sql.SQLException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
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
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING_INTERNAL, value = "false") })
    private EntityManager em;

    public void save(Person dataObj) throws SQLException {
        checkConnection();
        EntityTransaction trx = em.getTransaction();
        trx.begin();
        em.persist(dataObj);
        trx.commit();
    }

    @PreDestroy
    public void destroy() {
        em.close();
    }

    private void checkConnection() throws SQLException {
        if (em == null) {
            throw new SQLException("EntityManager is null. Not connected to database!");
        }
    }
}
