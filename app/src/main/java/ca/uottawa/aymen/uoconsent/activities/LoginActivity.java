package ca.uottawa.aymen.uoconsent.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;

public class LoginActivity extends AppCompatActivity {

    private View parent_view;
    TextInputEditText edtUser,edtPassword;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parent_view = findViewById(android.R.id.content);

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
        edtUser = findViewById(R.id.edt_user);
        edtUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        edtPassword = findViewById(R.id.edt_password);
        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        final Intent intent = new Intent(this,PhotoshootActivity.class);


        ((View) findViewById(R.id.sign_in)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Logged in", Snackbar.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
