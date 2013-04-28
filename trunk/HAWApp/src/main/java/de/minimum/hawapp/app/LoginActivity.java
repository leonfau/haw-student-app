package de.minimum.hawapp.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.login)
public class LoginActivity extends Activity {
    @ViewById
    EditText editLogin;

    @ViewById
    EditText editPassword;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    };

    @Click(R.id.buttonLogin)
    public void login() {
        // final Intent i = new Intent(LoginActivity.this,
        // StisysActivity.class);
        // i.putExtra("login", editLogin.getText().toString());
        // i.putExtra("password", editPassword.getText().toString());
        // Log.i("LoginActivity", "Login: " + login.getText());
        // Log.i("LoginActivity", "Password: " + password.getText());
        // startActivity(i);

        StisysActivity.login = editLogin.getText().toString();
        StisysActivity.password = editPassword.getText().toString();

        Toast.makeText(getApplicationContext(), "Logindata setted", Toast.LENGTH_SHORT).show();

        editLogin.setText("");
        editPassword.setText("");
    }

}
