package ca.uottawa.aymen.uoconsent.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.se.omapi.Session;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.Properties;

import ca.uottawa.aymen.uoconsent.ImageUtil;
import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;
import ca.uottawa.aymen.uoconsent.adapters.PersonsListAdapter;
import ca.uottawa.aymen.uoconsent.model.Person;

public class PersonListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PersonsListAdapter adapter;
    private FloatingActionButton btnAdd;
    Session session;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        initToolbar();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new PersonsListAdapter(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        btnAdd = findViewById(R.id.fbAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (PersonListActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.garnet_400);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        } else if (item.getItemId() == R.id.action_done) {

            onCreateDialog();


        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.create_email, null);
        final EditText receiver = view.findViewById(R.id.to);
        final EditText cc = view.findViewById(R.id.cc1);


        builder.setTitle("Send email");
        builder.setView(view);
        builder.setPositiveButton("Send",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String receiverText = receiver.getText().toString();
                String ccText = cc.getText().toString();

                if (true) {


                    dialog.dismiss();
                    Intent intent = new Intent (PersonListActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }



}
