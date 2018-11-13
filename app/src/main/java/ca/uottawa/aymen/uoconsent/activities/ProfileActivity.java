package ca.uottawa.aymen.uoconsent.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.aymen.uoconsent.ImageUtil;
import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;
import ca.uottawa.aymen.uoconsent.model.Person;

public class ProfileActivity extends AppCompatActivity {



    private ImageView imgFavorite,imgPerson;
    private Bitmap photoBitmap;
    FloatingActionButton btnSelfie;
    TextInputEditText edtName,edtEmail;
    String name,email;
    Intent intent;
    private static final int CAMERA_REQUEST = 5;
    private static final int SIGNATURE_REQUEST = 10;
    private List<Person> personList = new ArrayList<>();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();
        showTermServicesDialog();

        personList = Tools.getPersonsList(getApplicationContext());



        intent = new Intent(ProfileActivity.this, SignatureActivity.class);
        imgFavorite = findViewById(R.id.signature);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,SIGNATURE_REQUEST);
            }
        });

        btnSelfie = findViewById(R.id.btnSelfie);
        btnSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        imgPerson = findViewById(R.id.person);
        edtEmail= findViewById(R.id.edt_email);
        edtName=findViewById(R.id.edt_user);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SignatureActivity.bitmap != null)
            imgFavorite.setImageBitmap(SignatureActivity.bitmap);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SignatureActivity.bitmap = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!=null) {
            photoBitmap = (Bitmap) data.getExtras().get("data");
            imgPerson.setImageBitmap(photoBitmap);
        }
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
            finish();
        } else if (item.getItemId() == R.id.action_done) {
            if (photoBitmap != null && SignatureActivity.bitmap != null) {
                name = edtName.getText().toString();
                email = edtEmail.getText().toString();
                String profileStr = ImageUtil.convert(photoBitmap);
                String signatureStr = ImageUtil.convert(SignatureActivity.bitmap);
                Person person = new Person(name, email, profileStr, signatureStr);

                personList.add(person);
                Tools.savePersonsList(getApplicationContext(), personList);
                startActivity(new Intent(ProfileActivity.this, PersonListActivity.class));
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }



    private void showTermServicesDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_term_of_services);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Accept Clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Decline Clicked", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
