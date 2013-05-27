package de.minimum.hawapp.server.persistence.calendar;

// Generated 26.04.2013 13:35:12 by Hibernate Tools 3.4.0.CR1

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;

/**
 * CalendarAppointment generated by hbm2java
 */
@Entity
@Table(name = "Calendar_Appointment", catalog = "haw_app")
public class AppointmentPO implements AppointmentBO, java.io.Serializable {
    private static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT,
                    Locale.GERMANY);

    @Override
    public String toString() {
        return name + " von " + dateFormat.format(begin) + " bis " + dateFormat.format(end);
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private String uuid;
    private LecturePO lecture;
    private String name;
    private Date begin;
    private Date end;
    private String location;
    private String details;
    private Date lastModified;

    public AppointmentPO() {
    }

    public AppointmentPO(final String uuid, final LecturePO calendarLecture, final Date lastModified) {
        this.uuid = uuid;
        lecture = calendarLecture;
        this.lastModified = lastModified;
    }

    public AppointmentPO(final String uuid, final LecturePO calendarLecture, final String name, final Date begin,
                    final Date end, final String location, final String details, final Date lastModified) {
        this.uuid = uuid;
        lecture = calendarLecture;
        this.name = name;
        this.begin = begin;
        this.end = end;
        this.location = location;
        this.details = details;
        this.lastModified = lastModified;
    }

    @Override
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Lecture_uuid", nullable = false)
    @JsonIgnore
    public LecturePO getLecture() {
        return lecture;
    }

    public void setLecture(final LecturePO calendarLecture) {
        lecture = calendarLecture;
    }

    @Override
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "begin", length = 19)
    public Date getBegin() {
        return begin;
    }

    @Override
    public void setBegin(final Date begin) {
        this.begin = begin;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end", length = 19)
    public Date getEnd() {
        return end;
    }

    @Override
    public void setEnd(final Date end) {
        this.end = end;
    }

    @Override
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(final String location) {
        this.location = location;
    }

    @Override
    @Column(name = "details", length = 1000)
    public String getDetails() {
        return details;
    }

    @Override
    public void setDetails(final String details) {
        this.details = details;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastModified", nullable = false, length = 19)
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
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
        final AppointmentPO other = (AppointmentPO)obj;
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