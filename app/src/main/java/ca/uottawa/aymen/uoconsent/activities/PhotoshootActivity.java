package ca.uottawa.aymen.uoconsent.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.ArrayList;

import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;

public class PhotoshootActivity extends AppCompatActivity {
    Button bt_validate;
    TextInputEditText edtEvent,edtVideographer,edtClient,edtLocation,edtContact;
    ArrayList<String> info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoshoot);
        info=new ArrayList<String>();
        initToolbar();
        bt_validate = findViewById(R.id.vaildate);
        final Intent intent = new Intent(this, PersonListActivity.class);
        edtEvent = findViewById(R.id.edt_event);
        edtEvent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        edtVideographer = findViewById(R.id.edt_producer);
        edtVideographer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        edtClient = findViewById(R.id.edt_client);
        edtClient.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        edtLocation = findViewById(R.id.edt_location);
        edtLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        edtContact = findViewById(R.id.edt_contact);
        edtContact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    if (edtContact.getText().toString() == null ) {
                        setError(edtContact);
                    }
                }


            }
        });

        bt_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtContact.getText().toString() != null){
                    info.add(edtEvent.getText().toString());
                    info.add(edtVideographer.getText().toString());
                    info.add(edtClient.getText().toString());
                    info.add(edtLocation.getText().toString());
                    info.add(edtContact.getText().toString());
                    Tools.saveShootInfo(getApplicationContext(),info);
                    startActivity(intent);
                }

            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.garnet_400);
    }
    private void setError(TextInputEditText text){
        String simple = text.getHint().toString();
        String colored = "  *";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        text.setHint(builder);
    }

}
