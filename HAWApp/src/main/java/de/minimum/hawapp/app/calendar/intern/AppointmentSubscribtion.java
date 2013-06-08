package de.minimum.hawapp.app.calendar.intern;

import java.io.Serializable;
import java.util.Date;

import de.minimum.hawapp.app.calendar.beans.Appointment;

public class AppointmentSubscribtion implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4905383575606262971L;
    private String uuid;
    private long appointmentID;
    private Date lastModified;

    public static AppointmentSubscribtion newInstance(final Appointment appointment, final long appointmentID) {
        final AppointmentSubscribtion a = new AppointmentSubscribtion();
        a.uuid = appointment.getUuid();
        a.lastModified = appointment.getLastModified();
        a.appointmentID = appointmentID;
        return a;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(final long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

}
