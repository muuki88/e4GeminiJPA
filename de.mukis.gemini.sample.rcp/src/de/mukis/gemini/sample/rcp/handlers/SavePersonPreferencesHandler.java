package de.mukis.gemini.sample.rcp.handlers;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import de.mukis.gemini.sample.rcp.dao.PersonDAO;

/**
 * If you based your configuration on {@linkplain IEclipsePreferences} and you <br>
 * want to handle them without a restart, than you need to inject your <br>
 * {@linkplain EntityManager} within the execute method.
 * 
 * @author muki
 * @see SavePersonPreferencesHandler2
 * @see PersonDAO
 */
public class SavePersonPreferencesHandler {

    @Execute
    public void execute(@GeminiPersistenceContext(unitName = "unconfigured3", properties = {
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_DRIVER, valuePref = @Preference("jdbc_driver")),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_URL, valuePref = @Preference("jdbc_url")),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.LOGGING_LEVEL, value = "FINE"),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING, value = "false"),
            @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING_INTERNAL, value = "false") }) EntityManager em) {
        if (em != null) {
            System.out.println("Ready to to some db stuff!");
            em.close();
        }
    }

    @Inject
    @Optional
    public void trackDatabaseSettings(@Preference("jdbc_url") String url, @Preference("jdbc_driver") String driver) {
        System.out.println("Settings changed: ");
        System.out.println("\tURL   :\t" + url);
        System.out.println("\tDriver:\t" + driver);
    }

}
