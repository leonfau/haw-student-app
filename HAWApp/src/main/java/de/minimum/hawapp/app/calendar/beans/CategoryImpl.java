package de.minimum.hawapp.app.calendar.beans;

// Generated 26.04.2013 13:35:12 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * CalendarCategory generated by hbm2java
 */

public class CategoryImpl implements Category, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5746285398576303020L;
    private String uuid;
    private String name;
    private Date lastModified;
    private List<Lecture> lectures = new ArrayList<Lecture>(0);

    public CategoryImpl() {
    }

    public CategoryImpl(final String uuid, final Date lastModified) {
        this.uuid = uuid;
        this.lastModified = lastModified;
    }

    public CategoryImpl(final String uuid, final String name, final Date lastModified,
                    final List<Lecture> calendarLectures) {
        this.uuid = uuid;
        this.name = name;
        this.lastModified = lastModified;
        lectures = calendarLectures;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    @JsonIgnore
    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(final List<Lecture> calendarLectures) {
        lectures = calendarLectures;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoryImpl other = (CategoryImpl)obj;
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        }
        else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

}
