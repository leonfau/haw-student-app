package de.minimum.hawapp.app.calendar.beans;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = ChangeMessageImpl.class)
public interface ChangeMessage {

    public String getUuid();

    public Lecture getLecture();

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