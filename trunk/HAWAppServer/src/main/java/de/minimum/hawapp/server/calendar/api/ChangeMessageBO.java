package de.minimum.hawapp.server.calendar.api;

import java.util.Date;

public interface ChangeMessageBO {

	public String getUuid();

	public LectureBO getLecture();

	public Date getLastModified();

	public Date getChangeat();

	public void setChangeat(Date changeat);

	public String getReason();

	public void setReason(String reason);

	public String getWhat();

	public void setWhat(String what);

	public String getPerson();

	public void setPerson(String person);

}