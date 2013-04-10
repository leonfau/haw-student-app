package de.minimum.hawapp.server.persistence;

import org.hibernate.Session;

import de.minimum.hawapp.server.context.ManagerFactory;

public abstract class AbstractDao <T,O> {
//	public class SemesterDao {
//
//	}
//
//	private static HibernateSessionMgr sessionMgr=ManagerFactory.getManager(HibernateSessionMgr.class);
//	
//	@SuppressWarnings("unchecked")
//	public void update(T persistenceObject){
//		Session session=sessionMgr.getCurrentSession();
//		session.beginTransaction();
//		session.update((O)persistenceObject);
//		session.getTransaction().commit();
//	}
//	@SuppressWarnings("unchecked")
//	public void delete(T persistenceObject){
//		Session session=sessionMgr.getCurrentSession();
//		session.beginTransaction();
//		session.delete((O)persistenceObject);
//		session.getTransaction().commit();
//	}
//	
//	abstract public T findByUuid(String uuid);
	
}
