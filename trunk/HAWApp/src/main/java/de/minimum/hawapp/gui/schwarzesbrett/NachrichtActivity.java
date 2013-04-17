package de.minimum.hawapp.gui.schwarzesbrett;

import de.minimum.hawapp.app.R;
import android.app.Activity;
import android.os.Bundle; 
import android.view.View;
import android.widget.TextView;

public class NachrichtActivity extends Activity {
	private String titel;
	private String autor;
	private String datum;
	private String text;
	private String kontakt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sb_nachricht);
		
		Bundle b = getIntent().getExtras();
		titel = b.getString("titel");
		autor = b.getString("autor");
		datum = b.getString("datum");
		text = b.getString("text");
		kontakt = b.getString("kontakt");
			
		TextView vTitel = (TextView) findViewById(R.id.sb_nachricht_titel);
		TextView vAutor = (TextView) findViewById(R.id.sb_nachricht_autor);
		TextView vDatum = (TextView) findViewById(R.id.sb_nachricht_datum);
		TextView vText = (TextView) findViewById(R.id.sb_nachricht_text);
		TextView vKontakt = (TextView) findViewById(R.id.sb_nachricht_kontakt);
		
		vTitel.setText("Titel: "+titel);
		vAutor.setText("Autor: "+autor);
		vDatum.setText("Datum: "+datum);
		vText.setText("Text:\n"+text);
		vKontakt.setText(kontakt);
	}
	
	public void finish(View view) {
		this.finish();
	}
}
