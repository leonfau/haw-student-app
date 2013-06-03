package de.minimum.hawapp.app.context;

import java.util.HashMap;
import java.util.Map;


import de.minimum.hawapp.app.blackboard.DefaultBlackboardManager;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.calendar.intern.CalendarManager;
import de.minimum.hawapp.app.calendar.intern.CalendarManagerImpl;
import de.minimum.hawapp.app.mensa.management.MensaManager;
import de.minimum.hawapp.app.mensa.management.MensaManagerImpl;
import de.minimum.hawapp.app.pub.management.PubManager;
import de.minimum.hawapp.app.pub.management.PubManagerImpl;

public class ManagerFactory {
    private static Map<Class<?>, Object> managerMap = new HashMap<Class<?>, Object>();

    static {
        // ManagerFactory.managerMap.put(key, value);
        ManagerFactory.managerMap.put(MensaManager.class, new MensaManagerImpl());
        ManagerFactory.managerMap.put(CalendarManager.class, new CalendarManagerImpl());
        ManagerFactory.managerMap.put(BlackboardManager.class, new DefaultBlackboardManager());
        ManagerFactory.managerMap.put(PubManager.class, new PubManagerImpl());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getManager(final Class<?> mgrInterface) {
        return (T)ManagerFactory.managerMap.get(mgrInterface);
    }
}
