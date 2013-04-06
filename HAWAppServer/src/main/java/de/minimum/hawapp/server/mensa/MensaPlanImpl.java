package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MensaPlanImpl implements MensaPlan {

    Map<String, List<Meal>> weekPlan;
    List<String> dayList = new ArrayList<String>();
    private Date updateTime;

    private MensaPlanImpl() {
        this.weekPlan = new HashMap<String, List<Meal>>();
        this.dayList.add("Montag");
        this.dayList.add("Dienstag");
        this.dayList.add("Mittwoch");
        this.dayList.add("Donnerstag");
        this.dayList.add("Freitag");
        for(String day : this.dayList) {
            this.weekPlan.put(day, new ArrayList<Meal>());
        }
    }

    public static MensaPlanImpl MensaPlan() {
        return new MensaPlanImpl();
    }

    @Override
    public void update() throws IOException {
        this.updateTime = new Date();
        String url = "http://speiseplan.studwerk.uptrade.de";
        Document doc = Jsoup.connect(url + "/de/cafeteria/show/id/530").get();
        Elements link = doc.getElementsContainingText("Diese Woche");//doc.select("a");
        //System.out.println(link);
        String relHref = link.attr("href");
        url += relHref;
        System.out.println("URL: " + url);
        doc = Jsoup.connect(url).get();
        // Tabelle suchen
        Element table = doc.getElementById("week-menu");
        // Tage raussuchen
        Elements days = table.getElementsByClass("day");

        int dayIndex = 0;
        for(Element meal : days) {
            if (dayIndex >= 5) {
                dayIndex = 0;
            }

            if (meal.hasText() && !this.dayList.contains(meal.text())) {
                double studentPrice = Double.parseDouble(meal.getElementsByClass("price").text()
                                .replaceAll(".€.\\/.[0-9],[0-9][0-9].€", "").replace(",", "."));
                double othersPrice = Double.parseDouble(meal.getElementsByClass("price").text()
                                .replaceAll("[0-9],[0-9][0-9].€.\\/.", "").replaceAll(".€", "").replace(",", "."));
                String description = meal.getElementsByTag("strong").text().replaceAll("\\((.*?)\\)", "")
                                .replaceAll(" +", " ").replaceAll(" , ", ", ");
                this.weekPlan.get(this.dayList.get(dayIndex))
                                .add(MealImpl.Meal(description, studentPrice, othersPrice));
            }
            dayIndex++;
        }
    }

    @Override
    public List<Meal> getDayPlan(String day) {
        return this.weekPlan.get(day);
    }

    @Override
    public Map<String, List<Meal>> getWeekPlan() {
        return this.weekPlan;
    }
    
    @Override
    public Date getUpdateTime(){
    	return this.updateTime;
    }

}
