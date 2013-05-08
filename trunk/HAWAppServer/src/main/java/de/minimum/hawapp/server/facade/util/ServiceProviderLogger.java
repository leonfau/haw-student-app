package de.minimum.hawapp.server.facade.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

public class ServiceProviderLogger {
    private ServiceProviderLogger() {
    }

    /**
     * Logt die Anfrage als info in der Form "&ltmsg&gt by IP: &ltremoteIP&gt
     * 
     * @param msg
     *            Nachricht die vorne angefügt werden soll
     * @param logger
     *            logger mit dessen Hilfe geloggt werden soll
     * @param req
     *            Request Object von dem die IP kommt
     */
    public static void logRequest(String msg, Logger logger, HttpServletRequest req) {
        logger.info(msg + " by IP: " + req.getRemoteAddr());
    }
}
