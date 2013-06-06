package de.minimum.hawapp.app.login.gui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;

import de.minimum.hawapp.app.MainActivity;
import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.login.Login;
import de.minimum.hawapp.app.util.InternetConnectionUtil;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @ViewById
    EditText editLogin;

    @ViewById
    EditText editPassword;

    @ViewById
    CheckBox checkBoxPassword;

    @ViewById
    Button buttonLogin;

    @ViewById
    Button buttonLogout;

    @StringRes
    String failedLogin;

    @StringRes
    String succesfulLogin;

    @StringRes
    String noInternet;

    @StringRes
    String checkLogin;

    @StringRes
    String logout;

    @StringRes
    String loggedOut;

    public static final int DIALOG_DOWNLOAD_INFORMATION_PROGRESS = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences settings = getSharedPreferences("config", Context.MODE_PRIVATE);

        String login;
        if ((login = settings.getString("username", "")) != "") {
            setLoadedLogin(Login.decrypt(login));
        }
        String password;
        if ((password = settings.getString("password", "")) != "") {
            setLoadedPassword(Login.decrypt(password));
        }

        if (password != "") {
            setCheckBoxChecked();
        }
    };

    @Override
    protected Dialog onCreateDialog(final int id) {
        ProgressDialog mProgressDialog;
        switch(id) {
            case DIALOG_DOWNLOAD_INFORMATION_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage(checkLogin);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    @Click(R.id.buttonLogin)
    void loginClicked() {
        if (InternetConnectionUtil.hasInternetConnection(getApplicationContext())) {
            showDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
            login(editLogin.getText().toString(), editPassword.getText().toString());
            buttonLogin.setEnabled(false);
        }
        else {
            Toast.makeText(getApplicationContext(), noInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @Click(R.id.buttonLogout)
    void logoutClicked() {
        loggedOut();
        MainActivity.loggedOut();
        Login.logout();
    }

    @Click(R.id.checkBoxPassword)
    void checkboxClicked() {
        final SharedPreferences settings = getSharedPreferences("config", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();

        if (checkBoxPassword.isChecked()) {
            editor.putString("password", Login.getEncryptedPassword());
        }
        else {
            editor.putString("password", "");
        }

        editor.commit();
    }

    @Background
    void login(final String login, final String password) {
        loginToast(Login.login(login, password));
    }

    @UiThread
    void loginToast(final boolean logged) {
        final SharedPreferences settings = getSharedPreferences("config", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = settings.edit();

        editor.putString("username", Login.getEncryptedLogin());

        dismissDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);
        removeDialog(DIALOG_DOWNLOAD_INFORMATION_PROGRESS);

        if (!logged) {
            Toast.makeText(getApplicationContext(), failedLogin, Toast.LENGTH_SHORT).show();
        }
        else {
            loggedIn();
            MainActivity.loggedIn();
            Toast.makeText(getApplicationContext(), succesfulLogin, Toast.LENGTH_SHORT).show();
        }

        if (!checkBoxPassword.isChecked()) {
            editPassword.setText("");
        }

        editor.commit();
        buttonLogin.setEnabled(true);
    }

    @UiThread
    void setLoadedLogin(final String login) {
        editLogin.setText(login);
    }

    @UiThread
    void setLoadedPassword(final String password) {
        editPassword.setText(password);
    }

    @UiThread
    void setCheckBoxChecked() {
        checkBoxPassword.setChecked(true);
    }

    @UiThread
    void loggedIn() {
        buttonLogin.setVisibility(View.GONE);
        buttonLogout.setVisibility(View.VISIBLE);
    }

    @UiThread
    void loggedOut() {
        buttonLogin.setVisibility(View.VISIBLE);
        buttonLogout.setVisibility(View.GONE);

        if (!checkBoxPassword.isChecked()) {
            editPassword.setText("");
        }

        Toast.makeText(getApplicationContext(), loggedOut, Toast.LENGTH_SHORT).show();
    }
}