package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        new Thread(() -> {
            try {
                //5 segundos talvez seja um pouco demais
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }).start();
    }
}