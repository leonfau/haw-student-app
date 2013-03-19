package mensaPlan;

import java.util.Map;

public interface EatingMenu {
	/**
	 * @return Anzahl der gespeicherten Essen
	 */
	public int getMealCount();
	
	/**
	 * F�gt dem Men� ein Essen hinzu
	 * @param name --> Name des Essens
	 * @param price --> Preis des Essens
	 */
	public void addMeal(String name, Double price);

	/**
	 * Entfernt ein Essen aus dem Men� 
	 * @param name --> Name des zu entfernendes Essens
	 */
	public void removeMeal(String name);
	
	/**
	 * @param name --> Name des gewollten Essens
	 * @return Preis des Essens f�r Studierende als Double
	 */
	public Double getMealPrice(String name);
	
	/**
	 * Gibt alle Essen zur�ck
	 * @return Alle Essen als Map mit Key als Essensnahme und Value als Preis f�r Studierende
	 */
	public Map<String, Double> getMeals();
}
