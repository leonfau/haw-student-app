package de.minimum.hawapp.server.mensa;

import java.io.IOException;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.istack.logging.Logger;

public class MensaPlanImpl implements MensaPlan {

	private Map<String, List<Meal>> weekPlan;
	private Map<String, List<Meal>> weekPlanUpdated;
	private Map<UUID, Meal> mealList;
	private List<String> dayList;
	private static Logger LOGGER = Logger.getLogger(MensaPlanImpl.class);

	private Date updateTime;

	private MensaPlanImpl() {
		dayList = new ArrayList<String>(Arrays.asList("Montag", "Dienstag",
				"Mittwoch", "Donnerstag", "Freitag"));
		this.weekPlan = new HashMap<String, List<Meal>>();

		for (String day : this.dayList) {
			this.weekPlan.put(day, new ArrayList<Meal>());
		}

		this.weekPlanUpdated = new HashMap<String, List<Meal>>();
		for (String day : this.dayList) {
			this.weekPlanUpdated.put(day, new ArrayList<Meal>());
		}

		mealList = new HashMap<UUID, Meal>();
	}

	public static MensaPlanImpl MensaPlan() {
		return new MensaPlanImpl();
	}

	@Override
	public synchronized void update() throws IOException {
		this.updateTime = new Date();
		updateWithoutMerge();
		mergeWeekPlans();
		LOGGER.info("update and merge");
	}

	private void updateWithoutMerge() throws IOException {
		String url = "http://speiseplan.studwerk.uptrade.de";
		Document doc = Jsoup.connect(url + "/de/cafeteria/show/id/530").get();
		Elements link = doc.getElementsContainingText("Diese Woche");// doc.select("a");
		// System.out.println(link);
		String relHref = link.attr("href");
		url += relHref;
		doc = Jsoup.connect(url).get();
		// Tabelle suchen
		Element table = doc.getElementById("week-menu");
		// Tage raussuchen
		Elements days = table.getElementsByClass("day");

		int dayIndex = 0;
		for (Element meal : days) {
			if (dayIndex >= 5) {
				dayIndex = 0;
			}
			// String[] test = meal.text().replaceAll("\\(((\\d|, ))*\\)",
			// "").split(" \\/ \\d,\\d\\d.€\\s");

			if (meal.hasText() && !this.dayList.contains(meal.text())) {

				String mealStr = meal.text()
						.replaceAll("\\(((\\d|, ))*\\)", "");
				int splitIndex = mealStr.indexOf("€", mealStr.indexOf("€") + 1);
				// System.out.println(splitIndex);
				String mealOne = mealStr.substring(0, splitIndex);
				String mealTwo = mealStr.substring(splitIndex + 1);
				List<String> mealLst = new ArrayList<String>();
				mealLst.add(mealOne);
				if (!mealTwo.isEmpty())
					mealLst.add(mealTwo);
				for (String m : mealLst) {
					String mPrice = m.substring(m.indexOf("€") - 5)
							.replaceAll("€", "").replaceAll(",", ".");
					double studentPrice = Double.parseDouble(mPrice.substring(
							0, 3));
					double othersPrice = Double.parseDouble(mPrice.substring(8,
							11));
					String description = m.replaceAll("..\\d,.*", "");
					this.weekPlanUpdated.get(this.dayList.get(dayIndex)).add(
							MealImpl.Meal(description, studentPrice,
									othersPrice));
				}
			}
			dayIndex++;
		}
	}

	private void mergeWeekPlans() {
		for (Map.Entry<String, List<Meal>> dayPlanEntry : weekPlan.entrySet()) {
			List<Meal> oldDayPlan = dayPlanEntry.getValue();
			List<Meal> updatedDayPlan = weekPlanUpdated.get(dayPlanEntry
					.getKey());
			// Alte nicht mehr vorhandene Gerichte entfernen
			for (Meal oldMeal : oldDayPlan) {
				if (!updatedDayPlan.contains(oldMeal)) {
					// oldDayPlan.remove(oldMeal);
					mealList.remove(oldMeal.getID());
				}
			}
			// Neue Gerichte einfügen
			for (Meal updatedMeal : updatedDayPlan) {
				if (!oldDayPlan.contains(updatedMeal)) {
					oldDayPlan.add(updatedMeal);
					mealList.put(updatedMeal.getID(), updatedMeal);
				}

			}
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
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public Meal getMealByID(UUID id) {
		return mealList.get(id);
	}

}
