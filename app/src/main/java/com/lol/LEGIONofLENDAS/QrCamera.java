package com.lol.LEGIONofLENDAS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class QrCamera extends AppCompatActivity {
    SurfaceView sv;
    CameraSource cs;
    BarcodeDetector bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_camera);
        sv = findViewById(R.id.surfaceView);

        bd = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        cs = new CameraSource.Builder(this, bd).setRequestedPreviewSize(640, 480).build();
        Utils util = new Utils();
        Intent in = getIntent();
        Intent out = new Intent(QrCamera.this, ParseBatalha.class);
        sv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ContextCompat.checkSelfPermission(QrCamera.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                    ActivityCompat.requestPermissions(QrCamera.this, new String[] {Manifest.permission.CAMERA}, 100);
                try {
                    cs.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cs.stop();
            }
        });

        bd.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if(qrCodes.size() != 0){
                     util.txtMessageServer(in.getStringExtra("id"),"fightNonRandom",new ArrayList<>(Collections.singletonList(qrCodes.get(0).displayValue)));
                     out.putExtra("RESULTADO",util.output);
                     startActivity(out);
                }
            }
        });
    }
}