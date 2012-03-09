package de.mukis.hsqldb.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")})
public class Course {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "student_course", joinColumns = { @JoinColumn(name = "courseId") }, inverseJoinColumns = { @JoinColumn(name = "studentId") })
	private Set<Student> participants;

	private Teacher teacher;

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

	public Set<Student> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<Student> participants) {
		this.participants = participants;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
}
