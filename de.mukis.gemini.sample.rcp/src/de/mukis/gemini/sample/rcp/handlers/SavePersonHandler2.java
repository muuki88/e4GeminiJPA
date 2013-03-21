package de.mukis.gemini.sample.rcp.handlers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.swt.widgets.Shell;

import de.mukis.gemini.sample.model.Person;

public class SavePersonHandler2 {

    @Inject
    @GeminiPersistenceContext(unitName = "unconfigured", properties = {
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_DRIVER, value = "org.apache.derby.jdbc.EmbeddedDriver"), // com.mysql.jdbc.Driver
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_URL, value = "jdbc:derby:memory:test2;create=true"), // jdbc:mysql://127.0.0.1/test
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.DDL_GENERATION, value = PersistenceUnitProperties.CREATE_ONLY),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.DDL_GENERATION_MODE, value = PersistenceUnitProperties.DDL_DATABASE_GENERATION),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.LOGGING_LEVEL, value = "FINE"),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING, value = "false"),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING_INTERNAL, value = "false") })
    private EntityManager em;

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Person person = new Person("John", "Smith");
            em.persist(person);
            tx.commit();
            MessageDialog.openInformation(shell, "Person persisted", "Persisted person in test2 database!");
        } catch (Exception e) {
            MessageDialog.openError(shell, "Error persisting Person", "Derby bundle is not installed \n " + e.getMessage());
        } finally {
            em.clear();
        }

    }
}
