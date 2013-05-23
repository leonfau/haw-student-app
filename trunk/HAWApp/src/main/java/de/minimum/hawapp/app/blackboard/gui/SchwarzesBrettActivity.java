package de.minimum.hawapp.app.blackboard.gui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.minimum.hawapp.app.R;

public class SchwarzesBrettActivity extends Activity {

    /*
     * ##########################################################################
     * ########################## Test Daten
     * ####################################
     * ################################################################
     */
    // Anfragen
    String[] titelAnfragen = { "Fahrrad zu verkaufen", "neuer Drucker", "Fernseher" };
    String[] autorenAnfragen = { "Alex", "Wlad", "Alex" };
    String[] datumAnfragen = { "10.04.2013", "04.03.2013", "29.03.2013" };
    String[] textAnfragen = {
                    "Guterhaltenes, wenig gefahrenes SPECIALIZED Mountainbike mit folgenden Komponenten: "
                                    + "Rehmenhöhe : 18 , 165 bis 180 cm Körpergröße Rahmen: M4 Alu, endverstärkt, ORE Ober-und Unterrohr, "
                                    + "Scheibenbremsauflage, austauschbares Schaltauge. GabeL: Fox FloatF-80 RLT,80mmLuftfederung,einstellbar,"
                                    + "lockout Bremsen: Avid SD-5 Schaltwerk: Shimano XTR, 27-Gang Schalthebel: Shimano LX Cassette: Shimano LX Kurbel: "
                                    + "Specialized Strongarm 7050 Pedale: Shimano 515 Klickpedale Kettenblätter: "
                                    + "4-Arm, 44/32/22 Sattelstütze: Alu schwarz gefedert Felgen: Mavic X225, 26 "
                                    + "Neupreis: 2000 Euro ", "Neuer Drucker zu verkaufen",
                    "Alter Fernseher zu verkaufen" };
    String[] kontaktAnfragen = { "Alex S. Tel. 040123456", "Str. Rosenstr 14", "Alex S. Tel. 040123456" };
    int anzahlAnfragen = titelAnfragen.length;

    // Angebote
    String[] titelAngebote = { "Nebenjob", "Mitbewohner", "Tutorium" };
    String[] autorenAngebote = { "Alex", "Wlad", "Alex" };
    String[] datumAngebote = { "10.04.2013", "04.03.2013", "29.03.2013" };
    String[] textAngebote = { "Aushilfe (W/M) Aufgaben: Programmieren ...", "Suche Mitbewohner",
                    "Java tutorium am 04.05.2013" };
    String[] kontaktAngebote = { "Alex S. Tel. 040123456", "Str. Rosenstr 14", "Berliner Tor R565" };
    int anzahlAngebote = titelAngebote.length;
    // ####################################################################################################

    // Nachrichten attribute
    private String[] titel = titelAnfragen;
    private String[] autor = autorenAnfragen;
    private String[] datum = datumAnfragen;
    private String[] text = textAnfragen;
    private String[] kontakt = kontaktAnfragen;

    // Button Edit im Menu
    private MenuItem menuitemEdit;
    // Modus Flag true = Nachrichten markieren false = Nachrichten lesen
    private boolean editFlag;
    // die Pinnwand
    private ListView pinnwand;
    // Markierte Nachrichten
    private final List<TextView> editNachricht = new ArrayList<TextView>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sb_main);

        createPinnwand();

        if (menuitemEdit != null) {
            editFlag = menuitemEdit.isChecked();
        }
        else {
            editFlag = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.sb_main_menu, menu);
        menuitemEdit = menu.getItem(1);
        menuitemEdit.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Menubuttons OnClick
        switch(item.getItemId()) {
            case R.id.sb_main_menu_delete:

                if (!editNachricht.isEmpty()) {

                    String str = "";
                    for(int i = 0; i < editNachricht.size(); i++) {
                        str += editNachricht.get(i).getText();
                        str += i == editNachricht.size() - 1 ? "" : ", ";
                    }

                    Toast.makeText(SchwarzesBrettActivity.this, "Delete = " + str, Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.sb_main_menu_edit:
                editFlag = item.isChecked();
                item.setChecked(!editFlag);

                if (editFlag) {
                    item.setIcon(R.drawable.ic_menu_edit);
                }
                else {
                    item.setIcon(R.drawable.ic_menu_edit_disable);
                }

                final String text = editFlag ? "an" : "aus";
                Toast.makeText(SchwarzesBrettActivity.this, "Markier Modus " + text, Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadNeueNachricht(final View view) {
        final Intent intent = new Intent(getBaseContext(), NeueNachrichtActivity.class);
        startActivity(intent);
        editNachricht.clear();

    }

    public void loadAnfragen(final View view) {
        setTitel(titelAnfragen);
        setAutor(autorenAnfragen);
        setDatum(datumAnfragen);
        setText(textAnfragen);
        setKontakt(kontaktAnfragen);
        editNachricht.clear();
        createPinnwand();
    }

    public void loadAngebote(final View view) {
        setTitel(titelAngebote);
        setAutor(autorenAngebote);
        setDatum(datumAngebote);
        setText(textAngebote);
        setKontakt(kontaktAngebote);
        editNachricht.clear();
        createPinnwand();
    }

    /**
     * erstellt die Pinnwand
     */
    private void createPinnwand() {

        pinnwand = (ListView)findViewById(R.id.sb_main_pinnwand_list_view);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.sb_main_pinnwand_elem, getTitel());
        pinnwand.setAdapter(adapter);
        pinnwand.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        pinnwand.setOnItemClickListener(new OnPinnwandClickListener());
    }

    // ####### GETTER & SETTER

    public String[] getTitel() {
        return titel;
    }

    public void setTitel(final String[] titel) {
        this.titel = titel;
    }

    public String[] getAutor() {
        return autor;
    }

    public void setAutor(final String[] autor) {
        this.autor = autor;
    }

    public String[] getDatum() {
        return datum;
    }

    public void setDatum(final String[] datum) {
        this.datum = datum;
    }

    public String[] getText() {
        return text;
    }

    public void setText(final String[] text) {
        this.text = text;
    }

    public String[] getKontakt() {
        return kontakt;
    }

    public void setKontakt(final String[] kontakt) {
        this.kontakt = kontakt;
    }

    // ########## Innere Klasse
    private class OnPinnwandClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {

            final CheckedTextView tv = (CheckedTextView)pinnwand.getChildAt(arg2);
            final String s = tv.getText().toString();
            int index = -1;
            for(int i = 0; i < titel.length; i++) {
                if (titel[i] == s) {
                    index = i;
                }
            }

            pinnwand.getCheckItemIds();
            if (editNachricht.contains(tv)) {
                editNachricht.remove(tv);
            }
            else {
                editNachricht.add(tv);
            }

            // Wenn Nicht im Markieren Modus
            if (!editFlag) {
                pinnwand.clearChoices();
                editNachricht.clear();
                final Intent intent = new Intent(SchwarzesBrettActivity.this, NachrichtActivity.class);
                final Bundle b = new Bundle();
                b.putString("titel", titel[index]);
                b.putString("autor", autor[index]);
                b.putString("datum", datum[index]);
                b.putString("text", text[index]);
                b.putString("kontakt", kontakt[index]);
                intent.putExtras(b);

                startActivity(intent);
            }
        }

    }

}
