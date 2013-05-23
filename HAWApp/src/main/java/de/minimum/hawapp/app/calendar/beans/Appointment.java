package de.minimum.hawapp.app.calendar.beans;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = AppointmentImpl.class)
public interface Appointment {

    public String getUuid();

    public Lecture getLecture();

    public String getName();

    public void setName(String name);

    public Date getBegin();

    public void setBegin(Date begin);

    public Date getEnd();

    public void setEnd(Date end);

    public String getLocation();

    public void setLocation(String location);

    public String getDetails();

    public void setDetails(String details);

    public Date getLastModified();

}