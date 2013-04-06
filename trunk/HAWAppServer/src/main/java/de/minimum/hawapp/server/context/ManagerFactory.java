package de.minimum.hawapp.server.context;

import java.util.HashMap;
import java.util.Map;

import de.minimum.hawapp.server.mensa.MensaManager;
import de.minimum.hawapp.server.mensa.MensaManagerImpl;

public class ManagerFactory {
    private static Map<Class<?>, Object> managerMap = new HashMap<>();

    static {
        // ManagerFactory.managerMap.put(key, value);
    	ManagerFactory.managerMap.put(MensaManager.class, new MensaManagerImpl());
    }

    @SuppressWarnings("unchecked")
    public static <T> T getManager(Class<?> mgrInterface) {
        return (T)ManagerFactory.managerMap.get(mgrInterface);
    }
}
