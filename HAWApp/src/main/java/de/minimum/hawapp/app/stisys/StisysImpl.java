package de.minimum.hawapp.app.stisys;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class StisysImpl implements Stisys {
    static final String STISYS_URL = "https://stisys.informatik.haw-hamburg.de/stisys/";
    static final String LOGIN_URL = STISYS_URL + "login.do";
    static final String REPORT_URL = STISYS_URL + "viewExaminationData.do";

    @Override
    public String login(final String name, final String password) {
        // Log.i("StisysImpl login", "Login: " + login);
        // Log.i("StisysImpl login", "Password: " + password);
        HttpsURLConnection conn = null;
        List<String> cookies = null;

        try {
            final URL url = new URL(LOGIN_URL);
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

    @Override
    public String getReportPage(final String usercookie) {
        HttpsURLConnection conn;
        URL url;
        String html = "";

        try {
            url = new URL(REPORT_URL);
            conn = (HttpsURLConnection)url.openConnection();
            conn.setRequestProperty("Cookie", usercookie);
            conn.connect();

            final Scanner inStream = new Scanner(conn.getInputStream());
            while(inStream.hasNextLine()) {
                html += (inStream.nextLine());
            }
        }
        catch(final MalformedURLException e) {
            e.printStackTrace();
        }
        catch(final IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    @Override
    public void parseReportPage(final String html) {
        final Document doc = Jsoup.parse(html);

        final Elements tableframe = doc.select("#ergebnisuebersicht .tableframe");
        final Elements tablecontent = doc.select("#ergebnisuebersicht .tablecontent");

        for(int i = 0; i < tableframe.size(); i++) {
            System.out.println("Semester: " + tableframe.get(i).select("tr td").text());
            for(int j = 1; j < tablecontent.get(i).select("tr table tr").size(); j++) {
                System.out.println("Modul: "
                                + tablecontent.get(i).select("tr table tr").get(j).select("td").get(1).text());
                System.out.println("Prof: "
                                + tablecontent.get(i).select("tr table tr").get(j).select("td").get(3).text());
                System.out.println("Datum: "
                                + tablecontent.get(i).select("tr table tr").get(j).select("td").get(5).text());
                System.out.println("Ergebnis: "
                                + tablecontent.get(i).select("tr table tr").get(j).select("td").get(7).text());
            }
        }
    }

}
