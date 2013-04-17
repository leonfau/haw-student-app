package de.minimum.hawapp.gui.schwarzesbrett;

import de.minimum.hawapp.app.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ButtonListMainFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sb_main_btnlist_fragment, container, false);
	}
}
