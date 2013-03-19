package mensaPlan;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EatingMenuImpl implements EatingMenu {
	
	private Map<String, Double> menu;
	
	private EatingMenuImpl(){
		menu = new HashMap<String, Double>();
	}
	
	/**
	 * Factory Methode um einen neuen leeren Speiseplan zu erzeugen
	 * @return leeren Speiseplan
	 * @pre 
	 * @post leerer Speiseplan wurde erzeugt
	 */
	public static EatingMenu EatingMenu(){
		return new EatingMenuImpl();
	}
	
	
	
	@Override
	public int getMealCount() {
		return menu.size();
	}

	@Override
	public void addMeal(String name, Double price) {
		menu.put(name, price);

	}

	@Override
	public void removeMeal(String name) {
		menu.remove(name);

	}

	@Override
	public Double getMealPrice(String name) {
		return menu.get(name);
	}

	@Override
	public Map<String, Double> getMeals() {
		return menu;
	}
	
	@Override
	public String toString(){
		String s = "";
		for(Entry<String, Double> e : menu.entrySet()){
			s = (s+ "[" + e.getKey() + ", " + e.getValue() + "]");
		}
		return s;
	}

}
