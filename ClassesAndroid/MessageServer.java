package com.LOL.LEGIONofLENDAS;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

public class MessageServer extends Service {

    /**
     *
     *
     */
    private final static String CLASS = "CLASS";
    private final BinderLocal oBinder = new BinderLocal();

    public class BinderLocal extends Binder{
        MessageServer getService(){
            return  MessageServer.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return oBinder;
    }
    public String callService(String sessionID,String pedidoAFazer,ArrayList<String> argumentos){
        StringBuilder posparsed = new StringBuilder();
        if(!pedidoAFazer.equals("criarUtilizador"))
            for(String arg : argumentos) posparsed.append(arg).append("\n");
        else
            for(String arg : argumentos) posparsed.append("");
        Client client = new Client();
        switch (pedidoAFazer){
            case "fightRandom":
                return client.fightRandom(sessionID,posparsed.toString());
            case "fightNonRandom":
                return client.fightNonRandom(sessionID,posparsed.toString());
            case "Comprar":
                return client.clientLOJA(sessionID,pedidoAFazer,argumentos);
            case "Vender":
                return client.clientLOJA(sessionID,pedidoAFazer, argumentos);
            case "criarUtilizador":
                return client.inserirUserNaDB(argumentos.get(0),argumentos.get(1),argumentos.get(2),argumentos.get(3));
            default:
                return "INVALID";
        }
    }
}
