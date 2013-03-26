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
	 * Gibt den Essensplan eines speziellen Tages als Liste zur�ck.
	 * @param Enum day
	 * @return Liste der Gerichte
	 */
	public List<Meal> getDayPlan(String day);
	
	/**
	 * Gibt die aktuelle Essensliste der Woche zur�ck;
	 * @return Map mit Enums f�r den Tag und Listen der Gerichte
	 */
	public Map<String, List<Meal>> getWeekPlan();
}
