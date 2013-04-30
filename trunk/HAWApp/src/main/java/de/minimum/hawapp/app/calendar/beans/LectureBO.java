package de.minimum.hawapp.app.calendar.beans;

import java.util.Date;
import java.util.List;

public interface LectureBO {

    public String getUuid();

    public Date getLastModified();

    public String getName();

    public void setName(String name);

    public String getLecturerName();

    public void setLecturerName(String lecturerName);

    public CategoryBO getCategory();

    public List<AppointmentPO> getAppointments();

    public List<ChangeMessagePO> getChangeMessages();

}