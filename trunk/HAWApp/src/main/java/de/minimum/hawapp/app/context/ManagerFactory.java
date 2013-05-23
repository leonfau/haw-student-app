package de.minimum.hawapp.app.context;

import java.util.HashMap;
import java.util.Map;

import de.minimum.hawapp.app.mensa.management.CalendarManager;
import de.minimum.hawapp.app.mensa.management.CalendarManagerImpl;
import de.minimum.hawapp.app.mensa.management.MensaManager;
import de.minimum.hawapp.app.mensa.management.MensaManagerImpl;

public class ManagerFactory {
    private static Map<Class<?>, Object> managerMap = new HashMap<Class<?>, Object>();

    static {
        // ManagerFactory.managerMap.put(key, value);
        ManagerFactory.managerMap.put(MensaManager.class, new MensaManagerImpl());
        ManagerFactory.managerMap.put(CalendarManager.class, new CalendarManagerImpl());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getManager(final Class<?> mgrInterface) {
        return (T)ManagerFactory.managerMap.get(mgrInterface);
    }
}
