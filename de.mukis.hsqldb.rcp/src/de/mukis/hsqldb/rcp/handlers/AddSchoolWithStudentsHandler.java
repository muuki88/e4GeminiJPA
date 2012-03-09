package de.mukis.hsqldb.rcp.handlers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.gemini.ext.di.GeminiPersistenceContext;
import org.eclipse.swt.widgets.Shell;

import de.mukis.hsqldb.entity.School;
import de.mukis.hsqldb.entity.Student;

public class AddSchoolWithStudentsHandler {

	@Inject
	@GeminiPersistenceContext(unitName = "school")
	private EntityManager em;

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		try {
			studentsFirst();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* =========================================================== */
	/* =========================================================== */
	/* =========================================================== */

	public void schoolFirst() throws Exception {
		School school = addSchool();
		printSchool();
		addStudents(school);
		printSchool();
		printStudents();
	}

	public School addSchool() throws Exception {
		em.getTransaction().begin();
		School school = new School("WHG");
		em.persist(school);
		em.getTransaction().commit();
		return school;
	}

	public void addStudents(School school) throws Exception {
		em.getTransaction().begin();
		em.persist(new Student("Lisa", "Simpson", school));
		em.persist(new Student("Bart", "Simpson", school));
		em.getTransaction().commit();
	}

	/* =========================================================== */
	/* =========================================================== */
	/* =========================================================== */

	public void studentsFirst() throws Exception {
		Set<Student> students = addStudents();
		printStudents();
		addSchool(students);
		printSchool();
		printStudents();
	}

	public Set<Student> addStudents() throws Exception {
		HashSet<Student> students = new HashSet<>();
		students.add(new Student("Lisa", "Simpson"));
		students.add(new Student("Bart", "Simpson"));
		em.getTransaction().begin();
		for (Student student : students) {
			em.persist(student);
		}
		em.getTransaction().commit();
		return students;
	}

	public void addSchool(Set<Student> students) throws Exception {
		em.getTransaction().begin();
		School school = new School("WHG");
		em.persist(school);
		for (Student student : students) {
			school.addStudent(student);
		}
		em.getTransaction().commit();
	}

	/* =========================================================== */
	/* =========================================================== */
	/* =========================================================== */

	public void printSchool() {
		em.getTransaction().begin();
		List<?> students = em.createNamedQuery("School.findAll").getResultList();
		em.getTransaction().commit();
		System.err.println(students);
	}

	public void printStudents() {
		em.getTransaction().begin();
		List<?> students = em.createNamedQuery("Student.findAll").getResultList();
		em.getTransaction().commit();
		System.err.println(students);
	}

}
