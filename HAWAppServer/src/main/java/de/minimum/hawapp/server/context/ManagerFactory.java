package de.minimum.hawapp.server.context;

import java.util.HashMap;
import java.util.Map;

import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.intern.CalendarManagerImpl;
import de.minimum.hawapp.server.mensa.MensaManager;
import de.minimum.hawapp.server.mensa.MensaManagerImpl;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.HibernateSessionMgrImpl;

public class ManagerFactory {
    private static Map<Class<?>, Object> managerMap = new HashMap<>();

    static {
        // ManagerFactory.managerMap.put(key, value);
    	ManagerFactory.managerMap.put(MensaManager.class, new MensaManagerImpl());
    	
    	//Persistence Manager
    	ManagerFactory.managerMap.put(HibernateSessionMgr.class, new HibernateSessionMgrImpl());
    	ManagerFactory.managerMap.put(CalendarManager.class, new CalendarManagerImpl());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getManager(Class<?> mgrInterface) {
        return (T)ManagerFactory.managerMap.get(mgrInterface);
    }
}
