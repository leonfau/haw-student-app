package de.minimum.hawapp.server.calendar.api;

import java.io.IOException;
import java.util.List;

import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;

public interface CalendarParseManager {

    public List<AppointmentPO> parseFromFile(String path, String encoding) throws IOException;

    public void parseFromFileToDB(String path, String encoding) throws IOException;

}