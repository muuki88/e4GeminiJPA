package de.mukis.gemini.sample.rcp.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;

public class SavePreferencesHandler {

	@Inject
	@Preference
	IEclipsePreferences preferences;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws InvocationTargetException, InterruptedException {
		try {
			preferences.put("jdbc_driver", "org.apache.derby.jdbc.EmbeddedDriver");
			preferences.put("jdbc_url", "jdbc:derby:memory:test2;create=true");
			preferences.putBoolean("jdbc_reconnect", true);
			preferences.flush();
			MessageDialog.openInformation(shell, "Info :: Save Preferences", "Done! Saved driver (derby embedded) and url");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
}
