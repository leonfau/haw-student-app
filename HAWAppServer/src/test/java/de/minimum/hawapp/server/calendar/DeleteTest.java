package de.minimum.hawapp.server.calendar;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;

public class DeleteTest {
    @Rule
    public AutoCleanUpRule rule = new AutoCleanUpRule();

    public static CalendarManager calendarMgr = ManagerFactory.getManager(CalendarManager.class);

    @Test
    public void test() throws IOException {
        final CalendarParseManager cl = ManagerFactory.getManager(CalendarParseManager.class);
        cl.parseFromFileToDB("./src/test/java/de/minimum/hawapp/server/calendar/Sem_2012.txt", "Cp1250");
        assertTrue(calendarMgr.getAllCategories().size() > 0);
        calendarMgr.deleteAllCalendarDataFromDB();
        assertTrue(calendarMgr.getAllCategories().size() == 0);
    }
}
