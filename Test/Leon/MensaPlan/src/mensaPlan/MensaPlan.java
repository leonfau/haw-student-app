package mensaPlan;

import java.util.List;
import java.util.Map;
import java.io.IOException;

public interface MensaPlan {
	
	/**
	 * Parst die Webseite neu
	 * @throws IOException, bei keiner Verbindung zur Mensa Seite
	 */
	public void update() throws IOException;

	/**
	 * Gibt den Essensplan eines speziellen Tages als Liste zurück.
	 * @param Enum day
	 * @return Liste der Gerichte
	 */
	public List<Meal> getDayPlan(String day);
	
	/**
	 * Gibt die aktuelle Essensliste der Woche zurück;
	 * @return Map mit Enums für den Tag und Listen der Gerichte
	 */
	public Map<String, List<Meal>> getWeekPlan();
}
