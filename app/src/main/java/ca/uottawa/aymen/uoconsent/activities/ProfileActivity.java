package ca.uottawa.aymen.uoconsent.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.uottawa.aymen.uoconsent.ImageUtil;
import ca.uottawa.aymen.uoconsent.R;
import ca.uottawa.aymen.uoconsent.Tools;
import ca.uottawa.aymen.uoconsent.model.Person;

public class ProfileActivity extends AppCompatActivity {



    private ImageView imgFavorite;
    File photoFile ;
    private ImageView imgPerson;
    private Bitmap photoBitmap;
    FloatingActionButton btnSelfie;
    TextInputEditText edtName,edtEmail;
    String name,email;
    Intent intent;
    private static final int CAMERA_REQUEST = 5;
    private static final int SIGNATURE_REQUEST = 10;
    private List<Person> personList = new ArrayList<>();
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private  static int i=0;
    private String mCurrentPhotoPath;
    private Uri photoURI;
    Boolean modified=false;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();
        Bundle extras = getIntent().getExtras();
        personList = Tools.getPersonsList(getApplicationContext());
        intent = new Intent(ProfileActivity.this, SignatureActivity.class);
        imgFavorite = findViewById(R.id.signature);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        btnSelfie = findViewById(R.id.btnSelfie);
        imgPerson = findViewById(R.id.person);
        edtEmail= findViewById(R.id.edt_email);
        edtName=findViewById(R.id.edt_user);

        if (extras != null) {
            modified=true;
            position=extras.getInt("position");
            edtName.setText(personList.get(position).getName());
            edtEmail.setText(personList.get(position).getEmail());
            imgPerson.setImageBitmap(BitmapFactory.decodeFile(personList.get(position).getPicture()));
            imgFavorite.setImageBitmap((ImageUtil.convert(personList.get(position).getSignature())));
        }
        else{
            showTermServicesDialog();
            SignatureActivity.bitmap=null;

        }


        btnSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    takePhoto();
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                takePhoto();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK ) {
            setReducedSize(imgPerson.getWidth(),imgPerson.getHeight());
            //thumbnail();

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
            this.finish();
        } else if (item.getItemId() == R.id.action_done) {
            name = edtName.getText().toString();
            email = edtEmail.getText().toString();
            String profileStr = mCurrentPhotoPath;
            String signatureStr = ImageUtil.convert(SignatureActivity.bitmap);
            Person person = new Person(name, email, profileStr, signatureStr);
            if (!modified){
                if (photoBitmap != null && SignatureActivity.bitmap != null) {
                    personList.add(person);

                }
            }
            else{
                personList.get(position).setName(name);
                personList.get(position).setEmail(email);
                personList.get(position).setPicture(mCurrentPhotoPath);
                personList.get(position).setSignature(signatureStr);
            }


            Tools.savePersonsList(getApplicationContext(), personList);
            startActivity(new Intent(ProfileActivity.this, PersonListActivity.class));
            this.finish();

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
    public void takePhoto(){

        Intent cameraTintent = new Intent();

        String imageFileName = "JPEG_" + i + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        photoFile = new File(storageDir , imageFileName + ".jpg");
        mCurrentPhotoPath = photoFile.getAbsolutePath();

        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this, "ca.uottawa.aymen.uoconsent", photoFile);
        }
        cameraTintent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
        cameraTintent.putExtra("android.intent.extras.CAMERA_FACING",1);
        cameraTintent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraTintent,CAMERA_REQUEST);
    }

    private void rotateImage(Bitmap bitmap){
        ExifInterface exitInterface = null;
        try{
            exitInterface = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exitInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix= new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:

        }
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        imgPerson.setImageBitmap(rotatedBitmap);
    }
    private void setReducedSize(int targetW, int targetH){

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        photoBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath,bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;
        photoBitmap= BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgPerson.setImageBitmap(photoBitmap);
    }





}
