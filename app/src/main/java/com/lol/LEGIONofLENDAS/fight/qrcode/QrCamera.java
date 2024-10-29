package com.lol.LEGIONofLENDAS.fight.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.VibratorManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.lol.LEGIONofLENDAS.client.User;
import com.lol.LEGIONofLENDAS.fight.ParseBatalha;
import com.lol.LEGIONofLENDAS.R;
import com.lol.LEGIONofLENDAS.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class QrCamera extends AppCompatActivity {
    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    User user;

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
                    Log.e("QRCamera->Permission","Não foi possivel abrir a camera",e);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_camera);
        Intent in = getIntent();
        user = User.ExtractUser(in);
        cameraPreview = findViewById(R.id.cameraPreview);
        txtResult = findViewById(R.id.txtResult);

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
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(QrCamera.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    Log.e("QRCamera->surfaceCreated","Não foi possivel abrir a camera",e);
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    txtResult.post(() -> {
                        //Create vibrate
                        var vibratorManager = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
                        var vibrator = vibratorManager.getDefaultVibrator();
                        vibrator.vibrate(VibrationEffect.createOneShot(1000,VibrationEffect.DEFAULT_AMPLITUDE));

                        txtResult.setText(qrcodes.valueAt(0).displayValue);
                        cameraSource.stop();
                        if (!txtResult.getText().toString().equals(getResources().getString(R.string.value_check))) {
                            Utils util = new Utils();
                            util.txtMessageServer(
                                    user.userId,
                                    "fightNonRandom",
                                    new ArrayList<>(Collections.singletonList(txtResult.getText().toString()))
                            );

                            String query = "SELECT texto FROM batalhaLOG WHERE iduser = " + user.userId + " AND visto = 0";
                            util.txtMessageServer(user.userId, "TESTINGADMIN", new ArrayList<>(Collections.singletonList(query)));

                            String batalha = util.output;
                            query = "UPDATE batalhaLOG SET visto = 1 WHERE iduser = " + user.userId + " AND visto = 0";
                            util.txtMessageServer(user.userId, "TESTINGADMIN", new ArrayList<>(Collections.singletonList(query)));

                            Intent out = new Intent(QrCamera.this, ParseBatalha.class);
                            out.putExtra("strluta", batalha);
                            out = user.SetUserNavigationData(out);
                            startActivity(out);
                        }
                    });
                }

            }
        });
    }
}