package de.mukis.hsqldb.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
	@NamedQuery(name = "Patient.findById", query = "SELECT p FROM Patient p WHERE p.id = :id"),
	@NamedQuery(name = "Patient.findByFirstname", query = "SELECT p FROM Patient p WHERE p.firstname = :firstname"),
	@NamedQuery(name = "Patient.findByLastname", query = "SELECT p FROM Patient p WHERE p.lastname = :lastname"),
	@NamedQuery(name = "Patient.findByInsuranceId", query = "SELECT p FROM Patient p WHERE p.insuranceId = :insuranceId"),
	@NamedQuery(name = "Patient.findIdentical", query = "SELECT p FROM Patient p WHERE p.firstname = :firstname AND p.lastname = :lastname AND p.birth = :birth"),
	@NamedQuery(name = "Patient.likeName", query = "SELECT p FROM Patient p WHERE lower(p.lastname) LIKE :lastname OR lower(p.firstname) LIKE :firstname") })
public class Patient {

	public static final short MALE = 0;
	public static final short FEMALE = 1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String firstname;

	private String lastname;

	private short gender;

	@Column(unique = true)
	private String insuranceId;

	@Temporal(TemporalType.DATE)
	private Date birth;

	@Lob
	private String comment;

	private String cluster;

	@Temporal(TemporalType.DATE)
	private Date therapystart;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
	private List<Therapy> therapies;

	public Patient() {
	}
	
	public Patient(String firstname, String lastname, String insuranceId) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.insuranceId = insuranceId;
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

	public short getGender() {
		return gender;
	}

	public void setGender(short gender) {
		this.gender = gender;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public Date getTherapystart() {
		return therapystart;
	}

	public void setTherapystart(Date therapystart) {
		this.therapystart = therapystart;
	}

	public List<Therapy> getTherapies() {
		return therapies;
	}

	protected void setTherapies(List<Therapy> therapies) {
		this.therapies = therapies;
	}
	
	public void addTherapy(Therapy therapy) {
		therapies.add(therapy);
		if(!this.equals(therapy.getPatient()))
			therapy.setPatient(this);
	}
	
	public void removeTherapy(Therapy therapy) {
		therapies.remove(therapy);
	}

	@Override
	public String toString() {
		return "Patient [firstname=" + firstname + ", lastname=" + lastname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((insuranceId == null) ? 0 : insuranceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (insuranceId == null) {
			if (other.insuranceId != null)
				return false;
		} else if (!insuranceId.equals(other.insuranceId))
			return false;
		return true;
	}
	
	
}
