package de.minimum.hawapp.app.stisys;

public interface Result {

	/**
	 * Gibt das Semester der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the semester
	 */
	public int getSemester();

	/**
	 * Gibt den Namen der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Gibt den Professor der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the prof
	 */
	public String getProf();

	/**
	 * Gibt das Datum der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the date
	 */
	public String getDate();

	public String getErgebnis();

}
