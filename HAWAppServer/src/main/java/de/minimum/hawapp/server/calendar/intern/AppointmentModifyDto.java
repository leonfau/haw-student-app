package de.minimum.hawapp.server.calendar.intern;

import java.io.Serializable;

import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;

public class AppointmentModifyDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9170130756609721126L;

    private AppointmentPO appointment;
    private String fromPerson;
    private String reason;

    public AppointmentPO getAppointment() {
        return appointment;
    }

    public void setAppointment(final AppointmentPO appointment) {
        this.appointment = appointment;
    }

    public String getFromPerson() {
        return fromPerson;
    }

    public void setFromPerson(final String fromPerson) {
        this.fromPerson = fromPerson;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

}
