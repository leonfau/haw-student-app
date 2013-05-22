package de.minimum.hawapp.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.res.StringRes;

import de.minimum.hawapp.app.R;
import de.minimum.hawapp.app.login.Login;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity {

    @ViewById
    EditText editLogin;

    @ViewById
    EditText editPassword;

    @ViewById
    CheckBox checkBoxPassword;

    @StringRes
    String failedLogin;

    @StringRes
    String succesfulLogin;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences settings = getSharedPreferences("config", Context.MODE_PRIVATE);

        String login;
        if ((login = settings.getString("username", "")) != "") {
            setLoadedLogin(Login.decrypt(login).trim());
        }
        String password;
        if ((password = settings.getString("password", "")) != "") {
            setLoadedPassword(Login.decrypt(password).trim());
        }

        if (password != "") {
            setCheckBoxChecked();
        }
    };

    @Click(R.id.buttonLogin)
    public void login() {
        login(editLogin.getText().toString(), editPassword.getText().toString());

        if (Login.loggedIn()) {
            MainActivity.enableStisysTab();
        }
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

        if (!logged) {
            Toast.makeText(getApplicationContext(), failedLogin, Toast.LENGTH_SHORT).show();
        }
        else {
            if (checkBoxPassword.isChecked()) {
                editor.putString("password", Login.getEncryptedPassword());
            }
            editor.commit();
            Toast.makeText(getApplicationContext(), succesfulLogin, Toast.LENGTH_SHORT).show();
        }

        if (!checkBoxPassword.isChecked()) {
            editPassword.setText("");
            editor.putString("password", "");
            editor.commit();
        }
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
}