package org.e4.gemini.rcp.handlers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.e4.gemini.test.derby.Person;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class SavePersonHandler {

	@Inject
	@GeminiPersistenceContext(unitName = "testDerby")
	private EntityManager em;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Person person = new Person();
			person.setFirstName("John");
			person.setLastName("Smith");
			em.persist(person);
			tx.commit();
			
		} catch (Exception e) {
			MessageDialog.openError(shell, "Error persisting Person", "Derby bundle is not installed \n " + e.getMessage());
		} finally {
			em.clear();
		}

	}
}
