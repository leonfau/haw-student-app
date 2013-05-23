package de.minimum.hawapp.app.calendar.beans;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as = CategoryImpl.class)
public interface Category {

    public String getUuid();

    public String getName();

    public void setName(String name);

    public Date getLastModified();

    public List<Lecture> getLectures();

    public void setLectures(List<Lecture> lectures);

}