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
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.CategoryPO;
import de.minimum.hawapp.server.persistence.calendar.ChangeMessagePO;
import de.minimum.hawapp.server.persistence.calendar.LecturePO;

public class CalendarManagerImpl implements CalendarManager {
    private static HibernateSessionMgr hibernateSessionMgr = ManagerFactory.getManager(HibernateSessionMgr.class);

    @Override
    public CategoryBO createCategoryBO(final String categoryName) {
        final CategoryPO category = new CategoryPO();
        category.setName(categoryName);
        return createCategoryBO(category);
    }

    @Override
    public CategoryBO createCategoryBO(final CategoryBO transientCategoryBO) {
        final CategoryPO category = (CategoryPO)transientCategoryBO;
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        final String uuid = UUID.randomUUID().toString();
        category.setUuid(uuid);
        category.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(category);
        transaction.commit();
        return getCategoryBO(uuid);
    }

    @Override
    public LectureBO createLectureBO(final CategoryBO category, final String lectureName) {
        final LecturePO lecture = new LecturePO();
        lecture.setName(lectureName);
        lecture.setCategory((CategoryPO)category);
        return createLectureBO(lecture);
    }

    @Override
    public LectureBO createLectureBO(final LectureBO transientLecture) {
        final LecturePO lecture = (LecturePO)transientLecture;
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        final String uuid = UUID.randomUUID().toString();
        lecture.setUuid(uuid);
        lecture.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(lecture);
        transaction.commit();
        return getLectureBO(uuid);
    }

    @Override
    public AppointmentBO createAppointment(final LectureBO lecture, final String appoinmentName, final Date begin,
                    final Date end) {
        final AppointmentPO appointment = new AppointmentPO();
        appointment.setLecture((LecturePO)lecture);
        appointment.setBegin(begin);
        appointment.setEnd(end);
        appointment.setName(appoinmentName);
        return createAppointment(appointment);
    }

    @Override
    public AppointmentBO createAppointment(final AppointmentBO transientAppointment) {
        final AppointmentPO appointment = (AppointmentPO)transientAppointment;
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        final String uuid = UUID.randomUUID().toString();
        appointment.setUuid(uuid);
        appointment.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(appointment);
        transaction.commit();
        return getAppointment(uuid);
    }

    @Override
    public ChangeMessageBO createChangeMessage(final LectureBO lecture, final Date changeAt, final String reason,
                    final String what, final String fromPerson) {
        final ChangeMessagePO changemsg = new ChangeMessagePO();
        changemsg.setChangeat(changeAt);
        changemsg.setLecture((LecturePO)lecture);
        changemsg.setReason(reason);
        changemsg.setWhat(what);
        changemsg.setPerson(fromPerson);
        return createChangeMessage(changemsg);
    }

    @Override
    public ChangeMessageBO createChangeMessage(final ChangeMessageBO transientMessage) {
        final ChangeMessagePO changemessage = (ChangeMessagePO)transientMessage;
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        final String uuid = UUID.randomUUID().toString();
        changemessage.setUuid(uuid);
        changemessage.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(changemessage);
        transaction.commit();
        return getChangeMessage(uuid);
    }

    @Override
    public Set<CategoryBO> getAllCategories() {
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        @SuppressWarnings("unchecked")
        final Set<CategoryBO> set = new HashSet<CategoryBO>(session.createQuery("from CategoryPO").list());
        transaction.commit();
        return set;
    }

    @Override
    public CategoryBO getCategoryBO(final String uuid) {
        return (CategoryBO)getPOObject(CategoryPO.class, uuid);
    }

    @Override
    public LectureBO getLectureBO(final String uuid) {
        return (LectureBO)getPOObject(LecturePO.class, uuid);
    }

    @Override
    public ChangeMessageBO getChangeMessage(final String uuid) {
        return (ChangeMessageBO)getPOObject(ChangeMessagePO.class, uuid);
    }

    @Override
    public AppointmentBO getAppointment(final String uuid) {
        return (AppointmentBO)getPOObject(AppointmentPO.class, uuid);
    }

    @Override
    public Set<? extends AppointmentBO> getAppointmentsFromLecture(final String lectureUuid) {
        final LectureBO lecture = getLectureBO(lectureUuid);
        if (lecture == null) {
            return null;
        }
        return lecture.getAppointments();
    }

    @Override
    public Set<? extends LectureBO> getLecturesFromCategory(final String categoryUuid) {
        final CategoryBO category = getCategoryBO(categoryUuid);
        if (category == null) {
            return null;
        }
        return category.getLectures();
    }

    @Override
    public Set<? extends ChangeMessageBO> getChangeMessageFromLecture(final String lectureUuid) {
        final LectureBO lecture = getLectureBO(lectureUuid);
        if (lecture == null) {
            return null;
        }
        return lecture.getChangeMessages();
    }

    @Override
    public void modify(final CategoryBO category) {
        final CategoryPO categoryPO = (CategoryPO)category;
        categoryPO.setLastModified(new Date(System.currentTimeMillis()));
        modifyObject(categoryPO);
    }

    @Override
    public void modify(final LectureBO lecture) {
        final LecturePO lecturePO = (LecturePO)lecture;
        lecturePO.setLastModified(new Date(System.currentTimeMillis()));
        modifyObject(lecturePO);

    }

    @Override
    public void modify(final AppointmentBO appointment) {
        final AppointmentPO appointmentPO = (AppointmentPO)appointment;
        appointmentPO.setLastModified(new Date(System.currentTimeMillis()));
        modifyObject(appointmentPO);
    }

    @Override
    public void modify(final ChangeMessageBO changemessage) {
        final ChangeMessagePO changeMessagePO = (ChangeMessagePO)changemessage;
        changeMessagePO.setLastModified(new Date(System.currentTimeMillis()));
        modifyObject(changeMessagePO);

    }

    @Override
    public void delete(final CategoryBO category) {
        deleteObject(category);
    }

    @Override
    public void delete(final LectureBO lecture) {
        deleteObject(lecture);
    }

    @Override
    public void delete(final ChangeMessageBO changemessage) {
        deleteObject(changemessage);
    }

    @Override
    public void delete(final AppointmentBO appointment) {
        deleteObject(appointment);
    }

    private void modifyObject(final Object object) {
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(object);
        transaction.commit();
    }

    private void deleteObject(final Object object) {
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(object);
        transaction.commit();
    }

    private Object getPOObject(final Class<?> clazz, final String uuid) {
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        final Object object = session.get(clazz, uuid);
        transaction.commit();
        return object;
    }

    @Override
    public void deleteAllCalendarDataFromDB() {
        final Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        final Transaction transaction = session.getTransaction();
        transaction.begin();
        session.createQuery("DELETE FROM AppointmentPO").executeUpdate();
        session.createQuery("DELETE FROM ChangeMessagePO").executeUpdate();
        session.createQuery("DELETE FROM LecturePO").executeUpdate();
        session.createQuery("DELETE FROM CategoryPO").executeUpdate();
        transaction.commit();

    }

}
