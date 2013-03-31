package de.mukis.gemini.sample.rcp.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceUnit;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import de.mukis.gemini.sample.model.Person;
import de.mukis.gemini.sample.rcp.dao.PersonDAO;

/**
 * <p>
 * This POJO holds two database connections. One completely configured <br>
 * and the dynamic {@linkplain PersonDAO}.
 * </p>
 * 
 * <p>
 * The dao object won't be reinjected if some {@linkplain IEclipsePreferences}
 * change, <br>
 * so this is static.
 * </p>
 * 
 * 
 * @author muki
 * 
 */
public class SaveMultiDBHandler {

    @Inject
    @GeminiPersistenceUnit(unitName = "configured")
    EntityManagerFactory emf;

    @Inject
    PersonDAO dao;

    @Execute
    public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws InvocationTargetException, InterruptedException {

        try {
            // Via injected DAO
            Person person1 = new Person("Max", "Wild");
            dao.save(person1);

            // Single EntityManager
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Person person2 = new Person("Bill", "Smith");
            em.persist(person2);
            em.getTransaction().commit();
            em.close();

            MessageDialog.openInformation(shell, "Person persisted",
                    "Persisted person via injected DAO \n and via temporary EntityManager!");
        } catch (Exception e) {
            MessageDialog.openError(shell, "Error persisting Person", "Something went wrong \n " + e.getMessage());
        }

    }

}
