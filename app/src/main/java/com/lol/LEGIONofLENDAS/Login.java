package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Login extends AppCompatActivity {
    EditText username, password;
    String userinfo = "";
    String[] args;
    Button btnlogin;
    TextView tnovaconta, treporpass;
    Utils util = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.teditusername);
        password = findViewById(R.id.teditpassword);

        btnlogin = findViewById(R.id.btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                util.txtMessageServer("1", "login", new ArrayList<>(Arrays.asList(username.getText().toString(), password.getText().toString())));
                userinfo =  util.output;
               if((username.getText().toString().equals(""))||(password.getText().toString().equals(""))) {
                   Toast.makeText(Login.this, "ERRO! USERNAME OU PASSWORD INCORRETOS", Toast.LENGTH_SHORT).show();
               }
               else {
                   if (userinfo.equals("NENHUM ELEMENTO")) {
                       Toast.makeText(Login.this, "ERRO! USERNAME OU PASSWORD INCORRETOS", Toast.LENGTH_SHORT).show();
                   }
                   else {
                      userinfo = userinfo.substring(0,userinfo.length()-1);
                       args = userinfo.split(" ");
                       Toast.makeText(Login.this, "LOGIN COM SUCESSO", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(Login.this, MenuPrincipal.class);
                       intent.putExtra("id",args[0]);
                       intent.putExtra("nome", args[1]);
                       intent.putExtra("imagem", args[2]);
                       intent.putExtra("nivel", args[3]);
                       intent.putExtra("exp", args[4]);
                       startActivity(intent);
                   }
               }
                finish();
            }
        });

        tnovaconta = findViewById(R.id.criarconta);

        tnovaconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CharCreationInfo.class);
                startActivity(intent);
                finish();
            }
        });


    }
}