package de.minimum.hawapp.app.stisys;

public interface Stisys {

    /**
     * Login-Methode fuer StISys, es wird der Cookie in Form von einer
     * Session-ID zurueck gegeben. Sollte kein Cookie gesetzt sein ist die
     * rueckgabe "null".
     * 
     * @param login
     * @param password
     * @return cookie
     */
    public String login(String login, String password);

    public String getReportPage(String cookie);

    public void parseReportPage(String html);

}
