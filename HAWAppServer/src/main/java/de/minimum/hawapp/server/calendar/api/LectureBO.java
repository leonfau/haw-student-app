package de.minimum.hawapp.server.calendar.api;

import java.util.Date;
import java.util.Set;

public interface LectureBO {

	public String getUuid();

	public Date getLastModified();

	public String getName();

	public void setName(String name);

	public String getLecturerName();

	public void setLecturerName(String lecturerName);
	
	public CategoryBO getCategory();
	public Set<AppointmentBO> getAppointmentBOs();

	public Set<ChangeMessageBO> getChangeMessageBOs();

}