package de.minimum.hawapp.app;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

@EActivity(R.layout.login)
public class LoginActivity extends Activity implements TabListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		
//	    ActionBar bar = getActionBar();
//
//	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//	    Tab tab = bar.newTab();
//	    tab.setText("tab1");
//	    tab.setTabListener(this);
//	    bar.addTab(tab);
//
//	    tab = bar.newTab();
//	    tab.setText("tab2");
//	    tab.setTabListener(this);
//	    bar.addTab(tab);
	};
	
	
	@Click(R.id.btnLogin)
	public void login(){
//		Context context = getApplicationContext();
//		CharSequence text = "Hello toast!";
//		int duration = Toast.LENGTH_SHORT;
//
//		Toast toast = Toast.makeText(context, text, duration);
//		toast.show();	
						//		Intent i = new Intent(LoginActivity.this, MainActivity.class);
						//        startActivity(i);
	}


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
}
