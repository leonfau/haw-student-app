package de.minimum.hawapp.app.stisys;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class StisysImpl implements Stisys {
    static final String STISYS_URL = "https://stisys.informatik.haw-hamburg.de/stisys/";

    @Override
    public String login(final String name, final String password) {
        // Log.i("StisysImpl login", "Login: " + login);
        // Log.i("StisysImpl login", "Password: " + password);
        HttpsURLConnection conn = null;
        List<String> cookies = null;

        try {
            final URL url = new URL(STISYS_URL + "login.do");
            final String param = "username=" + URLEncoder.encode(name, "UTF-8") + "&password="
                            + URLEncoder.encode(password, "UTF-8");

            conn = (HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setFixedLengthStreamingMode(param.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            final PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.close();
            cookies = conn.getHeaderFields().get("Set-Cookie");
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
        return cookies.toString();
    }

}
