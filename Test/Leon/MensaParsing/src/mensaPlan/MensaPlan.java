package mensaPlan;


import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface MensaPlan {
	
	/**
	 * Updatet aktuell gespeicherten Speiseplan
	 * Bei einem Error, wie Keine Verbindung wird eine Exception geworfen
	 * @throws IOException 
	 */
	public void update() throws IOException;

	/**
	 * 
	 * @return Letztes Update Datum und Zeit, Falls bisher kein Update durchgeführt wurde Exception
	 */
	public Date getLastUpdateTime();
	
	/**
	 * Gibt alle gespeicherten Speisepläne zurück
	 * @return Map die als Key den Wochentag als String hat und als Value den dazugehörigen Speiseplan beinhaltet
	 */
	public Map<String,EatingMenu> getMenus();
}
