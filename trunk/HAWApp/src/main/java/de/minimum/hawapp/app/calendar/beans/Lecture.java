package de.minimum.hawapp.app.calendar.beans;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = LectureImpl.class)
public interface Lecture {

    public String getUuid();

    public Date getLastModified();

    public String getName();

    public void setName(String name);

    public String getLecturerName();

    public void setLecturerName(String lecturerName);

    public Category getCategory();

    public List<Appointment> getAppointments();

    public void setAppointments(List<Appointment> appointments);

    public List<ChangeMessage> getChangeMessages();

    public void setChangeMessages(List<ChangeMessage> messages);

}