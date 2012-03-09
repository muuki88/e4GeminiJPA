package de.mukis.hsqldb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")})
public class Student {

	@Id
	@GeneratedValue
	private long id;

	private String firstname;
	private String lastname;

	@ManyToOne(cascade = CascadeType.ALL)
	private School school;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "student_course", joinColumns = { @JoinColumn(name = "studentId") }, inverseJoinColumns = { @JoinColumn(name = "courseId") })
	private Set<Course> courses;

	public Student() {
	}
	
	public Student(String firstname, String lastname) {
		setFirstname(firstname);
		setLastname(lastname);
	}

	public Student(String firstname, String lastname, School school) {
		setFirstname(firstname);
		setLastname(lastname);
		setSchool(school);
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public School getSchool() {
		return school;
	}
	
	public void setSchool(School school) {
		this.school = school;
		school.addStudent(this);
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ",courses=" + courses
				+ "]";
	}
	
}
