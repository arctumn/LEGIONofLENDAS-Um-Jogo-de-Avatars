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
    // um wrapper para as funções do server
    public String callService(String sessionID,String pedidoAFazer,ArrayList<String> argumentos){
        StringBuilder posparsed = new StringBuilder();
        //Wrapper de funções, á possibilidade da necessidade de recriar  o if a baixo
        if(!pedidoAFazer.equals("criarUtilizador"))
            for(String arg : argumentos) posparsed.append(arg).append("\n");
        else
            for(String arg : argumentos) posparsed.append(arg).append(" ");
        // Start client
        Client client = new Client();
        // wrapper parser
        switch (pedidoAFazer){
            case "fightRandom":
                return client.fightRandom(sessionID,posparsed.toString());
            case "fightNonRandom":
                return client.fightNonRandom(sessionID,posparsed.toString());
            case "Comprar":
                return client.clientLOJA(sessionID,"Comprar",argumentos);
            case "Vender":
                return client.clientLOJA(sessionID,"Vender", argumentos);
            case "Mostrar Loja":
                return client.showLOJA();
            case "criarUtilizador":
                return client.inserirUserNaDB(argumentos.get(0),argumentos.get(1),argumentos.get(2),argumentos.get(3),argumentos.get(4));
            case "login":
                return client.login(argumentos.get(0),argumentos.get(1));
            case "inventario":
                return client.inventario(sessionID);
            case "status":
                return client.inserirStatusUserDB(sessionID,argumentos.get(0),argumentos.get(1),argumentos.get(2),argumentos.get(3),argumentos.get(4),argumentos.get(5));
            case "rankingxp":
                return client.rankingByXpTopN(Integer.parseInt(argumentos.get(0)));
            default:
                return "INVALID FUNCTION";
        }
    }
}
