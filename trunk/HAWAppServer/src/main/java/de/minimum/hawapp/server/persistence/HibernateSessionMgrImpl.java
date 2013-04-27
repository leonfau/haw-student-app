package de.minimum.hawapp.server.persistence;

import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.TypeHelper;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.CategoryPO;
import de.minimum.hawapp.server.persistence.calendar.ChangeMessagePO;
import de.minimum.hawapp.server.persistence.calendar.LecturePO;
import de.minimum.hawapp.test.util.CleanUpHelper;

public class HibernateSessionMgrImpl implements HibernateSessionMgr {

    private static final SessionFactory sessionFactory = HibernateSessionMgrImpl.buildSessionFactory();

    private static Configuration conf = null;

    private static Configuration getConfiguration() {
        if (HibernateSessionMgrImpl.conf == null)
            HibernateSessionMgrImpl.conf = new Configuration().addAnnotatedClass(AppointmentPO.class).addAnnotatedClass(CategoryPO.class)
                            .addAnnotatedClass(ChangeMessagePO.class).addAnnotatedClass(LecturePO.class)
                            .setInterceptor(CleanUpHelper.CLEANUPHELPER_INSTANCE);
        return HibernateSessionMgrImpl.conf;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return HibernateSessionMgrImpl.getConfiguration().configure().buildSessionFactory();
        }
        catch(Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.testhibernate.HibernateSessionMgr#getSessionFactory()
     */
    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getSessionFactory
     * ()
     */
    @Override
    public SessionFactory getSessionFactory() {
        return HibernateSessionMgrImpl.sessionFactory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#close()
     */
    @Override
    public void close() throws HibernateException {
        HibernateSessionMgrImpl.sessionFactory.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#
     * containsFetchProfileDefinition(java.lang.String)
     */
    @Override
    public boolean containsFetchProfileDefinition(String arg0) {
        return HibernateSessionMgrImpl.sessionFactory.containsFetchProfileDefinition(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getAllClassMetadata
     * ()
     */
    @Override
    public Map<String, ClassMetadata> getAllClassMetadata() {
        return HibernateSessionMgrImpl.sessionFactory.getAllClassMetadata();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#
     * getAllCollectionMetadata()
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Map getAllCollectionMetadata() {
        return HibernateSessionMgrImpl.sessionFactory.getAllCollectionMetadata();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getCache()
     */
    @Override
    public Cache getCache() {
        return HibernateSessionMgrImpl.sessionFactory.getCache();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getClassMetadata
     * (java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public ClassMetadata getClassMetadata(Class arg0) {
        return HibernateSessionMgrImpl.sessionFactory.getClassMetadata(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getClassMetadata
     * (java.lang.String)
     */
    @Override
    public ClassMetadata getClassMetadata(String arg0) {
        return HibernateSessionMgrImpl.sessionFactory.getClassMetadata(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#
     * getCollectionMetadata(java.lang.String)
     */
    @Override
    public CollectionMetadata getCollectionMetadata(String arg0) {
        return HibernateSessionMgrImpl.sessionFactory.getCollectionMetadata(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getCurrentSession
     * ()
     */
    @Override
    public Session getCurrentSession() throws HibernateException {
        return HibernateSessionMgrImpl.sessionFactory.getCurrentSession();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#
     * getDefinedFilterNames()
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Set getDefinedFilterNames() {
        return HibernateSessionMgrImpl.sessionFactory.getDefinedFilterNames();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getFilterDefinition
     * (java.lang.String)
     */
    @Override
    public FilterDefinition getFilterDefinition(String arg0) throws HibernateException {
        return HibernateSessionMgrImpl.sessionFactory.getFilterDefinition(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getReference()
     */
    @Override
    public Reference getReference() throws NamingException {
        return HibernateSessionMgrImpl.sessionFactory.getReference();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getStatistics()
     */
    @Override
    public Statistics getStatistics() {
        return HibernateSessionMgrImpl.sessionFactory.getStatistics();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#getTypeHelper()
     */
    @Override
    public TypeHelper getTypeHelper() {
        return HibernateSessionMgrImpl.sessionFactory.getTypeHelper();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#isClosed()
     */
    @Override
    public boolean isClosed() {
        return HibernateSessionMgrImpl.sessionFactory.isClosed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#openSession()
     */
    @Override
    public Session openSession() throws HibernateException {
        return HibernateSessionMgrImpl.sessionFactory.openSession();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#openSession
     * (java.sql.Connection, org.hibernate.Interceptor)
     */
    @Override
    public Session openSession(Connection arg0, Interceptor arg1) {
        return HibernateSessionMgrImpl.sessionFactory.openSession(arg0, arg1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#openSession
     * (java.sql.Connection)
     */
    @Override
    public Session openSession(Connection arg0) {
        return HibernateSessionMgrImpl.sessionFactory.openSession(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.minimum.hawapp.server.persistence.HibernateSessionMgrd#openSession
     * (org.hibernate.Interceptor)
     */
    @Override
    public Session openSession(Interceptor arg0) throws HibernateException {
        return HibernateSessionMgrImpl.sessionFactory.openSession(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#
     * openStatelessSession()
     */
    @Override
    public StatelessSession openStatelessSession() {
        return HibernateSessionMgrImpl.sessionFactory.openStatelessSession();
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.minimum.hawapp.server.persistence.HibernateSessionMgrd#
     * openStatelessSession(java.sql.Connection)
     */
    @Override
    public StatelessSession openStatelessSession(Connection arg0) {
        return HibernateSessionMgrImpl.sessionFactory.openStatelessSession(arg0);
    }

}
