package com.lol.LEGIONofLENDAS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Thread.sleep;

public class QrCamera extends AppCompatActivity {
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCameraPermissionID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_camera);
        Intent in = getIntent();
        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1280, 720)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(QrCamera.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0) {
                    txtResult.post((Runnable) () -> {
                        //Create vibrate
                        Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(1000);
                        txtResult.setText(qrcodes.valueAt(0).displayValue);
                        cameraSource.stop();
                        if(!txtResult.getText().toString().equals(getResources().getString(R.string.value_check))){
                            Utils util = new Utils();
                            util.txtMessageServer(
                                    in.getStringExtra("id"),
                                    "fightNonRandom",
                                    new ArrayList<>(Collections.singletonList(txtResult.getText().toString()))
                            );
                            String query = "SELECT texto FROM batalhaLOG WHERE iduser ="+in.getStringExtra("id")+" AND visto = 0";
                            util.txtMessageServer(in.getStringExtra("id"),"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
                            Log.i("SERVERFIGHT",util.output);
                            // recebe o texto da batalha
                            String batalha = util.output;
                            // atualiza a informação do lado do server
                            query = "UPDATE batalhaLOG SET visto = 1 WHERE iduser = "+in.getStringExtra("id")+" AND visto = 0";
                            util.txtMessageServer(in.getStringExtra("id"),"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
                            Intent out = new Intent(QrCamera.this, ParseBatalha.class);
                            out.putExtra("RESULTADO",batalha);
                            startActivity(out);
                        }
                    });
                }

            }
        });


    }

}