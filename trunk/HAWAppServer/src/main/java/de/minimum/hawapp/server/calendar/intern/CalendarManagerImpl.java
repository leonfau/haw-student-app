package de.minimum.hawapp.server.calendar.intern;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.calendar.api.SemesterBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.calendar.Appointment;
import de.minimum.hawapp.server.persistence.calendar.Category;
import de.minimum.hawapp.server.persistence.calendar.Changemessage;
import de.minimum.hawapp.server.persistence.calendar.Lecture;
import de.minimum.hawapp.server.persistence.calendar.Semester;

public class CalendarManagerImpl implements CalendarManager {
    private static HibernateSessionMgr hibernateSessionMgr = ManagerFactory.getManager(HibernateSessionMgr.class);

    @Override
    public SemesterBO createSemesterBO(Date begin, Date end) {
        Semester semester = new Semester();
        semester.setBegin(begin);
        semester.setEnd(end);
        return createSemesterBO(semester);
    }

    @Override
    public SemesterBO createSemesterBO(SemesterBO transientSemesterBO) {
        Semester semester = (Semester)transientSemesterBO;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        semester.setUuid(uuid);
        transaction.begin();
        session.persist(semester);
        transaction.commit();
        return getSemesterBO(uuid);
    }

    @Override
    public CategoryBO createCategoryBO(SemesterBO semester, String categoryName) {
        Category category = new Category();
        category.setSemester(semester);
        category.setName(categoryName);
        return createCategoryBO(category);
    }

    @Override
    public CategoryBO createCategoryBO(CategoryBO transientCategoryBO) {
        Category category = (Category)transientCategoryBO;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        category.setUuid(uuid);
        transaction.begin();
        session.persist(category);
        transaction.commit();
        return getCategoryBO(uuid);
    }

    @Override
    public LectureBO createLectureBO(CategoryBO category, String lectureName) {
        Lecture lecture = new Lecture();
        lecture.setName(lectureName);
        lecture.setCategory((Category)category);
        return createLectureBO(lecture);
    }

    @Override
    public LectureBO createLectureBO(LectureBO transientLecture) {
        Lecture lecture = (Lecture)transientLecture;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        lecture.setUuid(uuid);
        transaction.begin();
        session.persist(lecture);
        transaction.commit();
        return getLectureBO(uuid);
    }

    @Override
    public AppointmentBO createAppointment(LectureBO lecture, String appoinmentName, Date begin, Date end) {
        Appointment appointment = new Appointment();
        appointment.setLecture((Lecture)lecture);
        appointment.setBegin(begin);
        appointment.setEnd(end);
        appointment.setName(appoinmentName);
        return createAppointment(appointment);
    }

    @Override
    public AppointmentBO createAppointment(AppointmentBO transientAppointment) {
        Appointment appointment = (Appointment)transientAppointment;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        appointment.setUuid(uuid);
        transaction.begin();
        session.persist(appointment);
        transaction.commit();
        return getAppointment(uuid);
    }

    @Override
    public ChangeMessageBO createChangeMessage(LectureBO lecture, Date changeAt, String reason, String what,
                    String fromPerson) {
        Changemessage changemsg = new Changemessage();
        changemsg.setChangeat(changeAt);
        changemsg.setLecture((Lecture)lecture);
        changemsg.setReason(reason);
        changemsg.setWhat(what);
        changemsg.setPerson(fromPerson);
        return createChangeMessage(changemsg);
    }

    @Override
    public ChangeMessageBO createChangeMessage(ChangeMessageBO transientMessage) {
        Changemessage changemessage = (Changemessage)transientMessage;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        changemessage.setUuid(uuid);
        transaction.begin();
        session.persist(changemessage);
        transaction.commit();
        return getChangeMessage(uuid);
    }

    @Override
    public Set<SemesterBO> getAllSemesterBO() {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        @SuppressWarnings("unchecked")
        Set<SemesterBO> set = new HashSet<SemesterBO>(session.createQuery("from Semester").list());
        transaction.commit();
        return set;
    }

    @Override
    public SemesterBO getSemesterBO(String uuid) {
        return (SemesterBO)getPOObject(Semester.class, uuid);
    }

    @Override
    public CategoryBO getCategoryBO(String uuid) {
        return (CategoryBO)getPOObject(Category.class, uuid);
    }

    @Override
    public LectureBO getLectureBO(String uuid) {
        return (LectureBO)getPOObject(Lecture.class, uuid);
    }

    @Override
    public ChangeMessageBO getChangeMessage(String uuid) {
        return (ChangeMessageBO)getPOObject(Changemessage.class, uuid);
    }

    @Override
    public AppointmentBO getAppointment(String uuid) {
        return (AppointmentBO)getPOObject(Appointment.class, uuid);
    }

    @Override
    public Set<CategoryBO> getCategoriesFromSemester(String semesterUuid) {
        return getSemesterBO(semesterUuid).getCategorieBOs();
    }

    @Override
    public Set<AppointmentBO> getAppointmentsFromLecture(String LectureUuid) {
        return getLectureBO(LectureUuid).getAppointmentBOs();
    }

    @Override
    public Set<LectureBO> getLecturesFromCategory(String categoryUuid) {
        return getCategoryBO(categoryUuid).getLectureBOs();
    }

    @Override
    public Set<ChangeMessageBO> getChangeMessageFromLecture(String LectureUuid) {
        return getLectureBO(LectureUuid).getChangeMessageBOs();
    }

    @Override
    public void modify(CategoryBO category) {
        modifyObject(category);
    }

    @Override
    public void modify(LectureBO lecture) {
        modifyObject(lecture);

    }

    @Override
    public void modify(AppointmentBO appointment) {
        modifyObject(appointment);
    }

    @Override
    public void modify(ChangeMessageBO changemessage) {
        modifyObject(changemessage);

    }

    @Override
    public void delete(SemesterBO semester) {
        deleteObject(semester);
    }

    @Override
    public void delete(CategoryBO category) {
        deleteObject(category);
    }

    @Override
    public void delete(LectureBO lecture) {
        deleteObject(lecture);
    }

    @Override
    public void delete(ChangeMessageBO changemessage) {
        deleteObject(changemessage);
    }

    @Override
    public void delete(AppointmentBO appointment) {
        deleteObject(appointment);
    }

    private void modifyObject(Object object) {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(object);
        transaction.commit();
    }

    private void deleteObject(Object object) {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(object);
        transaction.commit();
    }

    private Object getPOObject(Class<?> clazz, String uuid) {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object object = session.get(clazz, uuid);
        transaction.commit();
        return object;
    }

}
