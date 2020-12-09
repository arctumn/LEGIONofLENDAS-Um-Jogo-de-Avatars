package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
            Intent intent = new Intent(Login.this, CharCreationInfo.class);
            startActivity(intent);

      //  Intent intent = new Intent(Login.this, MenuPrincipal.class);
      //  startActivity(intent);
    }
}