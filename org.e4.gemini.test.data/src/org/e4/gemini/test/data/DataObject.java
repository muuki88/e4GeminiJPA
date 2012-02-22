package org.e4.gemini.test.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DataObject implements Serializable{
	private static final long serialVersionUID = -7396607474441878553L;
	@Id
	private long id;
	private String dummy;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDummy() {
		return dummy;
	}
	public void setDummy(String dummy) {
		this.dummy = dummy;
	}
	
}
