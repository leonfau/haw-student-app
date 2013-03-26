package mensaPlan;

public interface Meal {
	
	/*
	 * Gibt die Beschreibung des Essens zurück
	 */
	public String getDescription();
	
	/*
	 * Gibt den Preis für Studierende zurück
	 */
	public double getStudentPrice();
	
	/*
	 * Gibt den Preis für nicht Studenten zurück
	 */
	public double getOthersPrice();
	
}
