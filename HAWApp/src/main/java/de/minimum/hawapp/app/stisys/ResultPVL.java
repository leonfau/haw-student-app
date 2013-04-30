package de.minimum.hawapp.app.stisys;

public class ResultPVL extends AbstractResult {
	String pvl;

	public ResultPVL(final int semester, final String name, final String prof,
			final String date, final String pvl) {
		super(semester, name, prof, date);
		this.pvl = pvl;
	}

	@Override
	public String getErgebnis() {
		return pvl;
	}
}
