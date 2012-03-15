package de.mukis.hsqldb.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@NamedQueries({ @NamedQuery(name = "TherapyResult.findAll", query = "SELECT t FROM TherapyResult t") })
public class TherapyResult {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Temporal(TemporalType.DATE)
	private Date timestamp;

	@Column
	private String comment;

	@Column
	private String caption;

	@Column
	private int success;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
	private Data data;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
	private Therapy therapy;

	@PreRemove
	protected void preRemove() {
		//CONSTRAINT ON DELETE SET NULL
		if (data != null)
			data.setTherapyResult(null);
		//Remove itself from the therapy entity, but only if the therapy entity isn't removed itself.
		if (therapy != null && !therapy.removed)
			therapy.removeTherapyResult(this);
	}

	public long getId() {
		return id;
	}

	protected void setId(long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
		if (data != null && !this.equals(data.getTherapyResult()))
			data.setTherapyResult(this);
	}

	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
		if (therapy != null)
			therapy.addTherapyResult(this);

	}

	public Therapy getTherapy() {
		return therapy;
	}

	@Override
	public String toString() {
		return "TherapyResult [id=" + id + ", timestamp=" + timestamp + ", comment=" + comment + ", success=" + success + ", data="
				+ (data == null ? "null" : data.getId()) + ", therapy=" + (therapy == null ? "null" : therapy.getId()) + "]";
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
		TherapyResult other = (TherapyResult) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
