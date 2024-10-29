package com.lol.LEGIONofLENDAS.character.creation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lol.LEGIONofLENDAS.character.login.Login;
import com.lol.LEGIONofLENDAS.MainMenu;
import com.lol.LEGIONofLENDAS.R;
import com.lol.LEGIONofLENDAS.client.User;
import com.lol.LEGIONofLENDAS.client.UserImage;
import com.lol.LEGIONofLENDAS.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharCreationInfo extends AppCompatActivity {
    List<Integer> listImages = new ArrayList<>();
    EditText username, password;
    String userinfo;
    Button btnreg;
    TextView tcontaexistente;
    Utils util = new Utils();
    String user, name, pass, verif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation_info);
        initData();

        ViewPager pager = findViewById(R.id.pagerCreate);
        CharacterCreationCarrossel adapter = new CharacterCreationCarrossel(listImages, getBaseContext());
        pager.setAdapter(adapter);

        username = findViewById(R.id.teditnewuser);
        password = findViewById(R.id.teditnewpass);
        btnreg = findViewById(R.id.btn_reg);
        tcontaexistente = findViewById(R.id.temconta);


        btnreg.setOnClickListener(v -> {
            name = username.getText().toString();
            pass = password.getText().toString();

            ArrayList<String> newuser = new ArrayList<>();
            newuser.add(name);
            newuser.add(pass);
            newuser.add("0");
            newuser.add("0");
            if((name.isEmpty())||(pass.isEmpty())) {
                Toast.makeText(CharCreationInfo.this, "ERRO! USERNAME OU PASSWORD INVALIDOS", Toast.LENGTH_SHORT).show();
            }
            else {

                int imagem = getResources().getIdentifier("ava"+pager.getCurrentItem(), "drawable", getPackageName());
                newuser.add(getResources().getResourceName(imagem));
                util.txtMessageServer("0", "verificar", newuser);
                verif =  util.output;

                    if(verif.equals("NAO DISPONIVEL")) {
                        Toast.makeText(CharCreationInfo.this, "USERNAME JÁ EXISTE", Toast.LENGTH_SHORT).show();
                        name = "";
                        pass = "";
                    }
                    else{

                        util.txtMessageServer("0", "criarUtilizador", newuser);
                        user =  util.output;
                        util.txtMessageServer("1", "login", new ArrayList<>(Arrays.asList(name, pass)));
                        userinfo = util.output;
                        userinfo = userinfo.substring(0,userinfo.length()-1);
                        String[] args = userinfo.split(" ");
                        Intent intent = new Intent(CharCreationInfo.this, MainMenu.class);
                        var _image = getResources().getResourceName(imagem);
                        var userImage = new UserImage(_image,this.getResources(),this.getPackageName());
                        var user = new User(args[0],name,"0","0",userImage);
                        intent = user.SetUserNavigationData(intent);
                        startActivity(intent);
                        finish();
                    }


            }
        });

        tcontaexistente.setOnClickListener(v -> {
            Intent intent = new Intent(CharCreationInfo.this, Login.class);
            startActivity(intent);
        });

    }
    private void initData() {
        listImages.add(R.drawable.ava0);
        listImages.add(R.drawable.ava1);
        listImages.add(R.drawable.ava2);
        listImages.add(R.drawable.ava3);
        listImages.add(R.drawable.ava4);
        listImages.add(R.drawable.ava5);
        listImages.add(R.drawable.ava6);
        listImages.add(R.drawable.ava7);
        listImages.add(R.drawable.ava8);
        listImages.add(R.drawable.ava9);
    }
}