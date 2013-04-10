package de.minimum.hawapp.server.calendar.api;

import java.util.Date;
import java.util.Set;

public interface CategoryBO {

	public String getUuid();

	public SemesterBO getSemester();

	public String getName();

	public void setName(String name);

	public Date getLastModified();

	public Set<LectureBO> getLectureBOs();
	

}