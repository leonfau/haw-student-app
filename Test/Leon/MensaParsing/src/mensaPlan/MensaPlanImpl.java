package mensaPlan;

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
	
	private Map<String, EatingMenu> plan;
	List<String> dayList = new ArrayList<String>();
	private Date updateTime;
	
	private MensaPlanImpl(){
		plan = new HashMap<String, EatingMenu>();
		
		dayList.add("Montag");
		dayList.add("Dienstag");
		dayList.add("Mittwoch");
		dayList.add("Donnerstag");
		dayList.add("Freitag");
        dayList.add("Samstag");
        dayList.add("Sonntag");
		for(String day : dayList){
			plan.put(day, EatingMenuImpl.EatingMenu());
		}
		
	}
	
	/**
	 * Factory Methode
	 * @return neu Instanz eins Mensa Plans
	 */
	public static MensaPlan MensaPlan(){
		return new MensaPlanImpl();
	}

	@Override
	public void update() throws IOException {
        updateTime = new Date();
		String url = "http://speiseplan.studwerk.uptrade.de";
		Document doc = Jsoup.connect(url + "/de/cafeteria/show/id/530").get();
        Element link = doc.select("a").get(7);
        String relHref = link.attr("href");

        url += relHref;
        doc = Jsoup.connect(url).get();
		//Tabelle suchen
        Element table = doc.getElementById("week-menu");
		//Tage raussuchen
		Elements days = table.getElementsByClass("day");
		int dayIndex = 0;

		for(Element meal : days){
			if(dayIndex >= 5){
				dayIndex = 0;
			}

			if(meal.hasText() && !dayList.contains(meal.text())){
                double price = Double.parseDouble(meal.getElementsByClass("price").text().replaceAll(".€.*", "").replace(",", "."));
				plan.get(dayList.get(dayIndex)).addMeal(meal.getElementsByTag("strong").text()
						.replaceAll("\\((.*?)\\)", "")
						.replaceAll(" +", " ")
						.replaceAll(" , ", ", "),
						price);	//Preise entfernen
			}
			dayIndex++;
		}
	}

	@Override
	public Date getLastUpdateTime() {
		return updateTime;
	}

	@Override
	public Map<String, EatingMenu> getMenus() {
		return plan;
	}

}
