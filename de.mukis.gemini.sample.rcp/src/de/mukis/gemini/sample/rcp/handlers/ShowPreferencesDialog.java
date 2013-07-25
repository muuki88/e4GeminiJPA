package de.mukis.gemini.sample.rcp.handlers;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import de.mukis.gemini.sample.rcp.dialogs.PreferenceDialog;

public class ShowPreferencesDialog {

	@Inject
	IPreferencesService preferences;

	@Execute
	public void execute(IEclipseContext context) {
		PreferenceDialog dialog = ContextInjectionFactory.make(PreferenceDialog.class, context);
		dialog.setBlockOnOpen(true);
		dialog.open();
	}

}