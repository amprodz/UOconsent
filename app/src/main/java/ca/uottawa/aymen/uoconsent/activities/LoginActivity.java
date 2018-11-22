package ca.uottawa.aymen.uoconsent.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.List;

import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;
import ca.uottawa.aymen.uoconsent.model.Person;

public class LoginActivity extends AppCompatActivity {

    private View parent_view;
    TextInputEditText edtUser,edtPassword;
    TextInputLayout layoutPassword;
    Button btnSignIn;
    private String username,password;
    TextInputLayout layoutUser;

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
        layoutPassword= findViewById(R.id.layout_password);
        layoutUser= findViewById(R.id.layout_user);

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        btnSignIn= findViewById(R.id.sign_in);
        final Intent intent = new Intent(this,PhotoshootActivity.class);



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edtUser.getText().toString();
                password = edtPassword.getText().toString();
                Log.i("tagg",username+"  "+password);
                if (true) {
                    Snackbar.make(parent_view, "Logged in", Snackbar.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    if (TextUtils.isEmpty(username)) {
                        layoutUser.setError("User field cannot be a");
                    }
                    else if (TextUtils.isEmpty(password)) {
                        layoutUser.setError(null);
                        layoutPassword.setError("Password field cannot be empty");
                    }


                }
            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getApplicationContext().getSharedPreferences("MyPref", 0).edit().clear().commit();
        List<Person> list = Tools.getPersonsList(getApplicationContext());

    }
}
