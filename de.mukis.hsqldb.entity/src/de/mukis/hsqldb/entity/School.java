package de.mukis.hsqldb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "School.findAll", query = "SELECT s FROM School s") })
public class School {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
	private Set<Student> students;

	protected School() {
	}

	public School(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean addStudent(Student student) {
		if (!this.equals(student.getSchool()))
			student.setSchool(this);
		if (!getStudents().contains(student))
			return getStudents().add(student);
		return false;
	}

	public boolean removeStudent(Student student) {
		return getStudents().remove(student);
	}

	public Set<Student> getStudents() {
		return students;
	}

	protected void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", students=" + students + "]";
	}

}
