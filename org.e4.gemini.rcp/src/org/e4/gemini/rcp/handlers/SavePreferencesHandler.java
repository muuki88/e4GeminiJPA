package org.e4.gemini.rcp.handlers;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.prefs.BackingStoreException;

@SuppressWarnings("restriction")
public class SavePreferencesHandler {
	
	@Inject
	@Preference
	IEclipsePreferences preferences;
	
	@Execute
	public void execute(IWorkbench workbench, IEclipseContext context,@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) throws InvocationTargetException, InterruptedException {
		preferences.put("jdbc_driver", "org.gjt.mm.mysql.Driver");
		preferences.put("jdbc_url", "jdbc:mysql://127.0.0.1/test");
		preferences.put("jdbc_user", "test");
		preferences.put("jdbc_password", "test");
		try {
			preferences.flush();
			MessageDialog.openInformation(shell, "Info :: Save Preferences","Done!");
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
}
