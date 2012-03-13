package de.mukis.hsqldb.rcp.handlers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.swt.widgets.Shell;

abstract public class AbstractTestHandler {

	@Inject
	@GeminiPersistenceContext(unitName = "medmon-derby")
	private EntityManager em;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		try {
			transaction(em);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	abstract public void transaction(EntityManager em) throws Exception;
	
	protected void trace(String msg) {
		System.err.println(msg);
	}
}
