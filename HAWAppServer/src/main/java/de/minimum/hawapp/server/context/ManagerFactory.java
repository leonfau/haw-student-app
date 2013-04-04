package de.minimum.hawapp.server.context;

import java.util.HashMap;
import java.util.Map;

public class ManagerFactory {
    private static Map<Class<?>, ?> managerMap = new HashMap<>();

    static {
        // ManagerFactory.managerMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getManager(Class<?> mgrInterface) {
        return (T)ManagerFactory.managerMap.get(mgrInterface);
    }
}
