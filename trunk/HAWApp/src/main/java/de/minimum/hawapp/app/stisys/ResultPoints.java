package de.minimum.hawapp.app.stisys;

public class ResultPoints extends AbstractResult {
	int points;

	public ResultPoints(final int semester, final String name,
			final String prof, final String date, final int points) {
		super(semester, name, prof, date);
		this.points = points;
	}

	@Override
	public String getErgebnis() {
		return String.valueOf(points);
	}
}
