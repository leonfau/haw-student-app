package de.minimum.hawapp.test.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;

public class CleanUpHelper extends EmptyInterceptor{
	/**
	 * 
	 */
	public static final CleanUpHelper CLEANUPHELPER_INSTANCE=new CleanUpHelper();
	private static final long serialVersionUID = 1L;
	
	private List<Object> createdObjects=new ArrayList<Object>();
	private boolean registerObjects=false;
	
	protected CleanUpHelper(){
		
	}
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		if(registerObjects){
			createdObjects.add(entity);
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}
	
	public void startAutoCleanUp(){
		registerObjects=true;
	}
	public void cleanUpAndStop(){
		registerObjects=false;
		cleanUp();
	}
	
	private void cleanUp(){
		 HibernateSessionMgr hibernateMgr= ManagerFactory.getManager(HibernateSessionMgr.class);
		Session session=hibernateMgr.getCurrentSession();
		Transaction trx=session.getTransaction();
		trx.begin();
		Collections.reverse(createdObjects);
		for(Object object:createdObjects){
			session.delete(object);
		}
		trx.commit();
		createdObjects.clear();
	}
	
	
}