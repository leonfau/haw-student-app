package de.minimum.hawapp.app.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Login {
    private static boolean loggedIn = false;
    private static String login;
    private static String password;
    private static String userFirstName;
    private static String userLastName;

    public static boolean login(final String login, final String password) {
        loggedIn = checkLogin(login, password);

        if (loggedIn) {
            final MCrypt mcrypt = new MCrypt();

            /* Encrypt */
            try {
                Login.login = MCrypt.bytesToHex(mcrypt.encrypt(login));
                Login.password = MCrypt.bytesToHex(mcrypt.encrypt(password));
            }
            catch(final Exception e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    public static void logout() {
        loggedIn = false;
    }

    private static boolean checkLogin(final String login, final String password) {
        HttpsURLConnection conn = null;
        String html = "";

        try {
            final URL url = new URL("https://stisys.informatik.haw-hamburg.de/stisys/login.do");
            final String param = "username=" + URLEncoder.encode(login, "UTF-8") + "&password="
                            + URLEncoder.encode(password, "UTF-8");

            conn = (HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            final PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();

            final Scanner inStream = new Scanner(conn.getInputStream(), "ISO-8859-1");
            while(inStream.hasNextLine()) {
                html += (inStream.nextLine());
            }
        }
        catch(final MalformedURLException e) {
            e.printStackTrace();
        }
        catch(final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch(final IOException e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        final Document doc = Jsoup.parse(html);
        userFirstName = doc.select("font").text().replaceAll(" .*", "");
        userLastName = doc.select("font").text().replaceAll("^[a-zA-Z]*", "").replaceAll(".-.*", "");
        return doc.select("title").text().contains("Benutzerdaten");
    }
    
    public static String getUserFirstName(){
    	return userFirstName;
    }
    
    public static String getUserLastName(){
    	return userLastName;
    }

    public static boolean loggedIn() {
        return loggedIn;
    }

    public static String getEncryptedLogin() {
        return login;
    }

    public static String getEncryptedPassword() {
        return password;
    }

    public static String decrypt(final String encrypted) {
        final MCrypt mcrypt = new MCrypt();
        String decrypted = null;
        try {
            decrypted = new String(mcrypt.decrypt(encrypted));
        }
        catch(final Exception e) {
            e.printStackTrace();
        }
        return (decrypted.trim());
    }
}
