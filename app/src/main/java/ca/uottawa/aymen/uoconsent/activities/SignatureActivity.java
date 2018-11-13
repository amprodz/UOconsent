package ca.uottawa.aymen.uoconsent.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.PathRedoUndoCountChangeListener;
import com.rm.freedrawview.ResizeBehaviour;

import ca.uottawa.aymen.uoconsent.R;

public class SignatureActivity extends AppCompatActivity {
    Button btnClear, btnValidate;
    ImageButton btnClose;
    FreeDrawView mSignatureView;
    static Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        btnValidate = findViewById(R.id.bt_accept);
        btnClear = findViewById(R.id.bt_decline);
        btnClose =  findViewById(R.id.bt_close);
        mSignatureView =  findViewById(R.id.your_id);

        // Setup the View
        mSignatureView.setPaintColor(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
        mSignatureView.setPaintWidthPx(getResources().getDimensionPixelSize(R.dimen.paint_width));
        //mSignatureView.setPaintWidthPx(12);
        mSignatureView.setPaintWidthDp(getResources().getDimension(R.dimen.paint_width));
        //mSignatureView.setPaintWidthDp(6);
        mSignatureView.setPaintAlpha(255);// from 0 to 255
        mSignatureView.setResizeBehaviour(ResizeBehaviour.FIT_XY);// Must be one of ResizeBehaviour


        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Accept Clicked", Toast.LENGTH_SHORT).show();
                mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
                    @Override
                    public void onDrawCreated(Bitmap draw) {
                        // The draw Bitmap is the drawn content of the View
                        bitmap = draw;


                    }

                    @Override
                    public void onDrawCreationError() {
                        // Something went wrong creating the bitmap, should never
                        // happen unless the async task has been canceled
                    }
                });

                onBackPressed();

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignatureView.clearDraw();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    private void signatureIntent(Bitmap draw){


    }

    private void killActivity() {
        finish();
    }
}
