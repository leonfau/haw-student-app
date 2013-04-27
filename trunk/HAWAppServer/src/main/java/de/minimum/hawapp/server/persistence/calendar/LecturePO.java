package de.minimum.hawapp.server.persistence.calendar;

// Generated 26.04.2013 13:35:12 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.minimum.hawapp.server.calendar.api.LectureBO;

/**
 * CalendarLecture generated by hbm2java
 */
@Entity
@Table(name = "calendar_lecture", catalog = "haw_app")
public class LecturePO implements LectureBO, java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;
	private CategoryPO category;
	private Date lastModified;
	private String name;
	private String lecturerName;
	private Set<AppointmentPO> appointments = new HashSet<AppointmentPO>(
			0);
	private Set<ChangeMessagePO> changemessages = new HashSet<ChangeMessagePO>(
			0);

	public LecturePO() {
	}

	public LecturePO(String uuid, CategoryPO calendarCategory,
			Date lastModified) {
		this.uuid = uuid;
		this.category = calendarCategory;
		this.lastModified = lastModified;
	}

	public LecturePO(String uuid, CategoryPO calendarCategory,
			Date lastModified, String name, String lecturerName,
			Set<AppointmentPO> calendarAppointments,
			Set<ChangeMessagePO> calendarChangemessages) {
		this.uuid = uuid;
		this.category = calendarCategory;
		this.lastModified = lastModified;
		this.name = name;
		this.lecturerName = lecturerName;
		this.appointments = calendarAppointments;
		this.changemessages = calendarChangemessages;
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Category_uuid", nullable = false)
	@JsonIgnore 
	public CategoryPO getCategory() {
		return this.category;
	}

	public void setCategory(CategoryPO calendarCategory) {
		this.category = calendarCategory;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastModified", nullable = false, length = 19)
	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "lecturerName")
	public String getLecturerName() {
		return this.lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "lecture")
	@Override
	@JsonIgnore
	public Set<AppointmentPO> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(
			Set<AppointmentPO> calendarAppointments) {
		this.appointments = calendarAppointments;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "lecture")
	@JsonIgnore
	public Set<ChangeMessagePO> getChangeMessages() {
		return this.changemessages;
	}

	public void setChangeMessages(
			Set<ChangeMessagePO> calendarChangemessages) {
		this.changemessages = calendarChangemessages;
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
		LecturePO other = (LecturePO) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
