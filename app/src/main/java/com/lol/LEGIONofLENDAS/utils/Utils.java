package com.lol.LEGIONofLENDAS.utils;

import android.app.Activity;

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
    /**
     * numeroDEPessoasAMostrar = 15
     * util.txtMEssageServer("1","rankingxp",""+numeroDEPessoasAMostrar);
     *
     * recebido = util.output
     * "infoU1\ninfoU2\ninfoU3\n....\ninfoU15\n"
     * lista = new ArrayList<String>(Arrays.asList(recebido.split(\n))
     * lista ["VALUEnome VALUElvl VALUExp",...,"infoU15"]
     */
}
