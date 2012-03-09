package de.mukis.hsqldb.rcp.handlers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import de.mukis.hsqldb.entity.Student;

public class AddStudentHandler {

	@Inject
	@GeminiPersistenceContext(unitName = "school")
	private EntityManager em;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		try {
			em.getTransaction().begin();
			Student student = new Student();
			student.setFirstname("Max");
			student.setLastname("Mustermann");
			em.persist(student);
			em.getTransaction().commit();
			MessageDialog.openInformation(shell, "Student saved", "Saved student " + student);
			
			em.getTransaction().begin();
			List students = em.createNamedQuery("Student.findAll")
				.getResultList();
			em.getTransaction().commit();
			System.out.println(students);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
