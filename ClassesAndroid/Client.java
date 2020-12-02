package com.LOL.LEGIONofLENDAS;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Client {

    private static final String IP = "82.155.86.142";
    private static final int PORT  =  3003;

    private String format(String param1, String param2, String param3){
        return param1 + "\n" + param2 + "\n" + param3;
    }

    public String clientLOJA(String userId, String operacaoAFazer, ArrayList<String> items){
        StringBuilder elementos = new StringBuilder();
        if(items == null) elementos.append("");
        else for (String item : items) elementos.append(item).append(" ");

        switch (operacaoAFazer){
            case "Vender":
                return sendMESSAGE(format(userId ,"SHOP", "Vender " + elementos));
            case "Comprar":
                return sendMESSAGE(format(userId ,"SHOP", "Comprar " + elementos));
            default:
                return sendMESSAGE(format(userId ,"SHOP", "Nada"));
        }
    }

    public String fight(String userId, String oponente){
            if (oponente.equals("Player")) return sendMESSAGE(format(userId,"GAME","Player"));
        return sendMESSAGE(format(userId,"GAME","Bot"));
    }

    public String inserirNaBD(String tabela,String camposComID, String elementosSemOId){
         String newquery = "INSERT INTO "+tabela + " ("+camposComID+ ") VALUES ("+"NEWELE,"+elementosSemOId+")";
        return sendMESSAGE(format("0","DB",newquery));
    }

    public String operacaoNaBD(String utilizadorExistenteUserID,String operacaoAFazer){
        return sendMESSAGE(format(utilizadorExistenteUserID,"DB", operacaoAFazer));
    }

    private String sendMESSAGE(String parsedMESSAGE){
        Log.i("SERVERLOL","try CONNECT");
        try {
            //abre o socket
            Socket socket = new Socket(IP,PORT);

            Log.i("SERVERLOL","ENTREI NO SERVER");
            //prepara o input
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //escreve para o socket
            out.write(parsedMESSAGE);
            out.flush();
            String tester = "";
            StringBuilder resultado = new StringBuilder();
            while (true){
                tester = in.readLine();
                if (tester == null) break;
                resultado.append(tester).append("\n");
            }
            in.close();
            //fecha o socket
            if(resultado.length() == 0) Log.i("SERVERLOL","ERROR RECEIVING");
            Log.i("SERVERLOL", resultado.toString());

            out.close();

            socket.close();
            //retorna
            return resultado.toString();

        } catch (UnknownHostException e){
                //retorna caso nao encontre host
            return "NO HOST FOUND";
        } catch ( IOException e){
                //retorna caso tenha problemas de leitura
            return e.toString();
        }

    }
}
