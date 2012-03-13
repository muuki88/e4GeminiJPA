package de.mukis.hsqldb.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d") })
public class Data {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "FROM_TIMESTAMP", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date from;

	@Column(name = "TO_TIMESTAMP", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date to;

	private String file;

	private String type;

	@OneToOne(cascade = { CascadeType.REMOVE })
	private TherapyResult therapyResult;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Sensor sensor;

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TherapyResult getTherapyResult() {
		return therapyResult;
	}

	public void setTherapyResult(TherapyResult therapyResult) {
		this.therapyResult = therapyResult;
	}
	
	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
		if(sensor != null)
			sensor.addData(this);
	}

	@PreRemove
	protected void preRemove() {
		sensor.removeData(this);
	}

	@Override
	public String toString() {
		return "Data [id=" + id + ", from=" + from + ", to=" + to + ", file=" + file + ", type=" + type + ", therapyResult="
				+ (therapyResult == null ? "null" : therapyResult.getId()) + ", sensor=" + sensor.getId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Data other = (Data) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
