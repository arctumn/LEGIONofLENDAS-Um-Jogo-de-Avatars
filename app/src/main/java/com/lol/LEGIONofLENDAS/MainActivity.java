package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lol.LEGIONofLENDAS.character.login.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        splashScreen();
    }

    private void splashScreen() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                Log.e("MainActivity", "onCreate: Error on thread", e);
            }

            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        }).start();
    }
}