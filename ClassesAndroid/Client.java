package com.LOL.LEGIONofLENDAS;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


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

    public String fightNonRandom(String userId, String oponenteID){
        return sendMESSAGE(format(userId,"GAME",oponenteID));
    }
    public String fightRandom(String userId, String oponente){
        if (oponente.equals("Player")) return sendMESSAGE(format(userId,"GAME","Player"));
        return sendMESSAGE(format(userId,"GAME","Bot"));
    }

    public String inserirUserNaDB(String nome, String password, String nivel,String xp){
        password = getMd5Hash(password);
        String query = "INSERT INTO user (id,nome,password,level,xp) VALUES (NEWELE,"+"'"+nome+"','"+password+"',"+nivel+","+xp+")";
        return sendMESSAGE(format("0","DB",query));
    }

    public String inserirStatusUserDB(String id, String forca, String magia, String defesa, String vida){
        String exists = sendMESSAGE((format(id,"DB","SELECT statsid FROM status WHERE userid = "+id+"")));
        if(exists.isEmpty()){
            String query = "INSERT INTO status (statsid,forca,magia,defesa,vida,userid) VALUES (NEWELE,"+forca+","+magia+","+defesa+","+vida+")";
            return sendMESSAGE(format("0","DB",query));
        }
        String query = "UPDATE status SET forca = "+forca+", magia = "+magia+", defesa = "+defesa+", vida = "+vida+" WHERE id = "+exists+";";
        return sendMESSAGE(format("0","DB",query));
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

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            StringBuilder md5 = new StringBuilder(number.toString(16));
            while (md5.length() < 32)
                md5.insert(0, "0");
            return md5.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", Objects.requireNonNull(e.getLocalizedMessage()));
            return null;
        }
    }
}
