package de.mukis.gemini.sample.rcp.handlers;

import static org.eclipse.gemini.ext.di.GeminiPersistenceProperties.GEMINI_REINIT;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.WEAVING;
import static org.eclipse.persistence.config.PersistenceUnitProperties.WEAVING_INTERNAL;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.gemini.ext.di.GeminiPersistenceProperty;

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

	@Inject
	@Preference
	IEclipsePreferences preferences;

	@Execute
	public void execute(@GeminiPersistenceContext(unitName = "unconfigured3", properties = {
			@GeminiPersistenceProperty(name = JDBC_DRIVER, valuePref = @Preference("jdbc_driver")),
			@GeminiPersistenceProperty(name = JDBC_URL, valuePref = @Preference("jdbc_url")),
			@GeminiPersistenceProperty(name = LOGGING_LEVEL, value = "FINE"), //
			@GeminiPersistenceProperty(name = WEAVING, value = "false"),
			@GeminiPersistenceProperty(name = WEAVING_INTERNAL, value = "false"),
			@GeminiPersistenceProperty(name = GEMINI_REINIT, valuePref = @Preference("jdbc_reconnect")) }) EntityManager em) {

		// this is needed so the connection is reinitialized everytime
		preferences.putBoolean("jdbc_reconnect", false);
		if (em != null) {
			System.out.println("Ready to to some db stuff!");
			em.close();
		}
	}
}
