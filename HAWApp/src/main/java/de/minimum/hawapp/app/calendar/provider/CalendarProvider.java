package de.minimum.hawapp.app.calendar.provider;

import java.util.List;
import java.util.Set;

public interface CalendarProvider {
    /**
     * Calender mit folgenden Infos: ID ACCOUNT_NAME DISPLAY_NAME
     * 
     */
    public Set<Calendar> getCalendars();

    /**
     * 
     * @param event
     *            , must have startMillis, endMillis
     * @param calendarID
     * 
     */
    public long addEvent(long calendarID, Event event);

    public List<Long> addEvents(Calendar calendar, List<Event> events);

    /**
     * 
     * @param event
     *            must have event id;
     */
    public void deleteEvent(Event event);

    public void deleteEvents(List<Event> events);

    public void deleteEventById(long id);

    public void deleteEventsById(List<Long> ids);

    /**
     * 
     * @param event
     *            must have event id;
     */
    public void updateEvent(Event event);
}
