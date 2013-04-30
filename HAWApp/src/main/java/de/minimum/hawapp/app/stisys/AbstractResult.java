package de.minimum.hawapp.app.stisys;

public abstract class AbstractResult implements Result {
	private final int semester;
	private final String name;
	private final String prof;
	private final String date;

	protected AbstractResult(final int semester, final String name,
			final String prof, final String date) {
		this.semester = semester;
		this.name = name;
		this.prof = prof;
		this.date = date;
	}

	/**
	 * Gibt das Semester der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the semester
	 */
	@Override
	public int getSemester() {
		return semester;
	}

	/**
	 * Gibt den Namen der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Gibt den Professor der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the prof
	 */
	@Override
	public String getProf() {
		return prof;
	}

	/**
	 * Gibt das Datum der Pruefung / der Uebung / des Praktikums wieder
	 * 
	 * @return the date
	 */
	@Override
	public String getDate() {
		return date;
	}
}
