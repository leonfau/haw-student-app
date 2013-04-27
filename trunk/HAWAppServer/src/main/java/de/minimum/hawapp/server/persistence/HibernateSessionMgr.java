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
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

public interface HibernateSessionMgr {

	/* (non-Javadoc)
	 * @see com.testhibernate.HibernateSessionMgr#getSessionFactory()
	 */
	public SessionFactory getSessionFactory();

	public void close() throws HibernateException;

	public boolean containsFetchProfileDefinition(String arg0);

	public Map<String, ClassMetadata> getAllClassMetadata();

	@SuppressWarnings("rawtypes")
	public Map getAllCollectionMetadata();

	public Cache getCache();

	@SuppressWarnings("rawtypes")
	public ClassMetadata getClassMetadata(Class arg0);

	public ClassMetadata getClassMetadata(String arg0);

	public CollectionMetadata getCollectionMetadata(String arg0);

	public Session getCurrentSession() throws HibernateException;

	@SuppressWarnings("rawtypes")
	public Set getDefinedFilterNames();

	public FilterDefinition getFilterDefinition(String arg0)
			throws HibernateException;

	public Reference getReference() throws NamingException;

	public Statistics getStatistics();

	public TypeHelper getTypeHelper();

	public boolean isClosed();

	public Session openSession() throws HibernateException;

	public Session openSession(Connection arg0, Interceptor arg1);

	public Session openSession(Connection arg0);

	public Session openSession(Interceptor arg0) throws HibernateException;

	public StatelessSession openStatelessSession();

	public StatelessSession openStatelessSession(Connection arg0);

}