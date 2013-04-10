package de.minimum.hawapp.server.persistence.calendar;

// Generated 08.04.2013 22:52:22 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;

/**
 * Changemessage generated by hbm2java
 */
public class Changemessage implements java.io.Serializable, ChangeMessageBO {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;
	private Lecture lecture;
	private Date lastModified;
	private Date changeat;
	private String reason;
	private String what;
	private String person;

	public Changemessage() {
	}

	public Changemessage(String uuid, Lecture lecture) {
		this.uuid = uuid;
		this.lecture = lecture;
	}

	public Changemessage(String uuid, Lecture lecture, Date lastModified,
			Date changeat, String reason, String what, String person) {
		this.uuid = uuid;
		this.lecture = lecture;
		this.lastModified = lastModified;
		this.changeat = changeat;
		this.reason = reason;
		this.what = what;
		this.person = person;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Lecture getLecture() {
		return this.lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getChangeat() {
		return this.changeat;
	}

	public void setChangeat(Date changeat) {
		this.changeat = changeat;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getWhat() {
		return this.what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getPerson() {
		return this.person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		Changemessage other = (Changemessage) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
