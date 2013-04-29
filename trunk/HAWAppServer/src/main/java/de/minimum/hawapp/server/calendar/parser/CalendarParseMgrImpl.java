package de.minimum.hawapp.server.calendar.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.CategoryPO;
import de.minimum.hawapp.server.persistence.calendar.ChangeMessagePO;
import de.minimum.hawapp.server.persistence.calendar.LecturePO;

public class CalendarParseMgrImpl implements CalendarParseManager {
    HibernateSessionMgr hibernateMgr = ManagerFactory.getManager(HibernateSessionMgr.class);

    @Override
    public List<AppointmentPO> parseFromFile(String path, String encoding) throws IOException {
        ANTLRFileStream fileStream = new ANTLRFileStream(path, encoding);
        CalendarLexer calendarLexer = new CalendarLexer(fileStream);
        TokenStream tokenStream = new BufferedTokenStream(calendarLexer);
        CalendarParser calendarParser = new CalendarParser(tokenStream);
        CalendarParser.ProgContext prog = calendarParser.prog();
        CalendarAppointmentVisitor visitor = new CalendarAppointmentVisitor();
        prog.accept(visitor);
        return visitor.getAppointments();
    }

    @Override
    public void parseFromFileToDB(String path, String encoding) throws IOException {
        List<AppointmentPO> appointments = removeDuplicateAppointments(parseFromFile(path, encoding));
        Map<String, CategoryPO> categories = createCategories();
        Map<String, LecturePO> lectures = createLectures(categories, appointments);
        for(AppointmentPO appointment : appointments) {
            String name = appointment.getName();
            appointment.setLecture(lectures.get(name));
            appointment.setLastModified(new Date(System.currentTimeMillis()));
        }
        List<ChangeMessagePO> changeMessages = new ArrayList<ChangeMessagePO>();
        for(LecturePO lecture : lectures.values()) {
            ChangeMessagePO newChangeMessage = new ChangeMessagePO();
            newChangeMessage.setReason("Neue Termine");
            newChangeMessage.setPerson("Automatisch generiert");
            newChangeMessage.setWhat("Alle Termine neu erstellt!");
            newChangeMessage.setLecture(lecture);
            newChangeMessage.setUuid(UUID.randomUUID().toString());
            newChangeMessage.setChangeat(new Date(System.currentTimeMillis()));
            newChangeMessage.setLastModified(new Date(System.currentTimeMillis()));
            changeMessages.add(newChangeMessage);
        }
        Session session = this.hibernateMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for(CategoryPO category : categories.values()) {
            session.persist(category);
        }
        for(LecturePO lecture : lectures.values()) {
            session.persist(lecture);
        }
        for(AppointmentPO appointment : appointments) {
            session.persist(appointment);
        }
        for(ChangeMessagePO changemessage : changeMessages) {
            session.persist(changemessage);
        }
        transaction.commit();

    }

    private Map<String, LecturePO> createLectures(Map<String, CategoryPO> categories, List<AppointmentPO> appointments) {
        Map<String, LecturePO> map = new HashMap<String, LecturePO>();
        for(AppointmentPO appointment : appointments) {
            String name = appointment.getName();
            if (!map.containsKey(name)) {
                LecturePO newLecture = new LecturePO();
                newLecture.setCategory(categories.get(getCategoryNameforAppointment(appointment)));
                newLecture.setName(name);
                newLecture.setLecturerName(appointment.getDetails());
                newLecture.setUuid(UUID.randomUUID().toString());
                newLecture.setLastModified(new Date(System.currentTimeMillis()));
                map.put(name, newLecture);
            }
        }
        return map;
    }

    private String getCategoryNameforAppointment(AppointmentPO app) {
        String appName = app.getName();
        for(String catname : this.categoryNames) {
            if (appName.contains(catname)) {
                return catname;
            }
        }
        return this.defaultCategoryName;
    }

    private final String defaultCategoryName = "Sonstiges";
    private final String[] categoryNames = { "BAI1", "BAI2", "BAI3", "BAI4", "BAI5", "BAI6", "BTI1", "BTI2", "BTI3",
                    "BTI4", "BTI5", "BTI6", "BWI1", "BWI2", "BWI3", "BWI4", "BWI5", "BWI6", "MINF1", "MINF2", "MINF3",
                    "MINF4", "INF", "GW", this.defaultCategoryName };

    private Map<String, CategoryPO> createCategories() {
        Map<String, CategoryPO> map = new HashMap<String, CategoryPO>();
        for(String name : this.categoryNames) {
            CategoryPO newCategory = new CategoryPO();
            newCategory.setName(name);
            newCategory.setUuid(UUID.randomUUID().toString());
            newCategory.setLastModified(new Date(System.currentTimeMillis()));
            map.put(name, newCategory);
        }
        return map;
    }

    private List<AppointmentPO> removeDuplicateAppointments(List<AppointmentPO> appointments) {
        List<AppointmentPO> list = new ArrayList<AppointmentPO>();
        for(AppointmentPO appointment : appointments) {
            boolean isAlreadyPresent = false;
            for(AppointmentPO addedAppointment : list) {
                if (addedAppointment.getName().equals(appointment.getName())
                                && addedAppointment.getBegin().equals(appointment.getBegin())
                                && addedAppointment.getEnd().equals(appointment.getEnd())) {
                    isAlreadyPresent = true;
                }
            }
            if (!isAlreadyPresent) {
                list.add(appointment);
            }
        }
        return list;
    }

}
