package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lol.LEGIONofLENDAS.Client.User;
import com.lol.LEGIONofLENDAS.Client.UserImage;

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
        final SharedPreferences oSP = getPreferences(MODE_PRIVATE);
        username = findViewById(R.id.teditusername);
        password = findViewById(R.id.teditpassword);
        runOnUiThread(() -> {
                    if (!oSP.getString("username", "vazio").equals("vazio") && !oSP.getString("password", "vazio").equals("vazio")) {
                        username.setText(oSP.getString("username", "vazio"));
                        password.setText(oSP.getString("password", "vazio"));
                    }
                });
        btnlogin = findViewById(R.id.btn_login);

        btnlogin.setOnClickListener(this::loginMethod);

        tnovaconta = findViewById(R.id.criarconta);

        tnovaconta.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, CharCreationInfo.class);
            startActivity(intent);
            finish();
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences oSP = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor oEditor = oSP.edit();
        oEditor.putString("username",username.getText().toString());
        oEditor.putString("password",password.getText().toString());
        oEditor.apply();
    }

    private void loginMethod(View v) {
        var username = this.username.getText().toString();
        var password = this.password.getText().toString();
        var login = new ArrayList<>(Arrays.asList(username, password));
        util.txtMessageServer("1", "login", login);
        userinfo = util.output;
        if ((username.isEmpty()) || (password.isEmpty())) {
            Toast.makeText(Login.this,  R.string.ErrorLogin, Toast.LENGTH_SHORT).show();
        } else if (userinfo.equals("NENHUM ELEMENTO")) {
            Toast.makeText(Login.this,  R.string.ErrorLogin, Toast.LENGTH_SHORT).show();
        } else {
            userinfo = userinfo.substring(0, userinfo.length() - 1);
            args = userinfo.split(" ");

            Toast.makeText(Login.this, getResources().getString(R.string.SuccessLogin), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MenuPrincipal.class);

            var userImage = new UserImage(args[2],getResources(),this.getPackageName());
            var user = new User(args[0],args[1],args[4],args[3],userImage);

            intent = user.SetUserNavigationData(intent);
            startActivity(intent);
            finish();
        }
    }
}