package com.lol.LEGIONofLENDAS;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Utils extends Activity {
    private String get;

    public String output;

    public void txtMessageServer(String id, String pedidoafazer, ArrayList <String> arg){

        Thread thread = new Thread (() ->{
            try {
                get = MessageServer.callService(id, pedidoafazer, arg);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
            output = get;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
