package com.LOL.LEGIONofLENDAS;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.PasswordAuthentication;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Client {
    //ip e port
    private static final String IP = "82.155.86.142";
    private static final int PORT  =  3003;


    //função de controlo utilitaria
    private String format(String param1, String param2, String param3){
        return param1 + "\n" + param2 + "\n" + param3;
    }
    //trata de permitir compras e vendas
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
    // mostra todos os itens da loja
    public String showLOJA(){
        return sendMESSAGE(format("-1","DB","SELECT itemName,price,forca,magia,defesa,vida,level FROM SHOP;"));
    }

    //lutas não aleatorias
    public String fightNonRandom(String userId, String oponenteID){
        return sendMESSAGE(format(userId,"GAME",oponenteID));
    }
    //lutas aleatorias
    public String fightRandom(String userId, String oponente){
        if (oponente.equals("Player")) return sendMESSAGE(format(userId,"GAME","Player"));
        return sendMESSAGE(format(userId,"GAME","Bot"));
    }
    //cria um utilizador na BD
    public String inserirUserNaDB(String nome, String password, String nivel,String xp, String image){
        password = getMd5Hash(password);
        String query = "INSERT INTO user (id,nome,password,level,xp,image) VALUES (NEWELE,"+"'"+nome+"','"+password+"',"+nivel+","+xp+","+image+");";
        return sendMESSAGE(format("0","DB",query));
    }

    //retorna o id o nome e a imagem do boneco, cada vez que faz login
    public String login (String username, String password){
        password = getMd5Hash(password);
        return sendMESSAGE(format("1","DB","SELECT id,nome,image FROM user WHERE nome = '"+username+"' AND password = '"+password+"';"));
    }
    //Serve para armazenar e atualizar a informação do player no lado do cliente
    public String inserirStatusUserDB(String id, String forca, String magia, String defesa, String vida, String vitorias, String derrotas){
        String exists = showStatus(id);
        if(exists.equals("NENHUM ELEMENTO")){
            String query = "INSERT INTO status (statsid,forca,magia,defesa,vida,vitorias,derrotas,userid) VALUES "
                    + "("
                    + "NEWELE,"
                    + forca    + ","
                    + magia    + ","
                    + defesa   + ","
                    + vida     + ","
                    + vitorias + ","
                    + derrotas + ","
                    + id
                    +");";
            return sendMESSAGE(format("0","DB",query));
        }
        String query = "UPDATE status SET "
                + "forca = "+forca
                + ", magia = "+magia
                + ", defesa = "+defesa
                + ", vida = "+vida
                + ", vitorias = "+vitorias
                +", derrotas = "+derrotas
                +" WHERE id = "+exists+";";
        return sendMESSAGE(format("0","DB",query));
    }
    public String rankingByXpTopN(int quantidadeElementos){
        return sendMESSAGE(format("-1","DB","SELECT nome,level,xp FROM user ORDER BY xp DESC LIMIT "+quantidadeElementos+";"));
    }
    public String showStatus(String id){
        return sendMESSAGE((format(id,"DB","SELECT statsid FROM status WHERE userid = "+id+";")));
    }
    //Retorna uma string com o inventario da pessoa, necessario vir parsed
    public String inventario(String id){
        String query = "SELECT nome, forca, magia, defesa, vida FROM inventario WHERE userid = "+id+";";
        return sendMESSAGE(format(id,"DB",query));
    }
    //query test
    public String operacaoNaBD(String utilizadorExistenteUserID,String operacaoAFazer){
        return sendMESSAGE(format(utilizadorExistenteUserID,"DB", operacaoAFazer));
    }
    //envia para o server e retorna uma mesagem
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
            String aux = resultado.toString();
            aux = "["+resultado.toString()+"]";
            //fecha o socket
            //if(resultado.length() == 0) Log.i("SERVERLOL","ERROR RECEIVING");
            Log.i("SERVERLOL", aux);

            out.close();

            socket.close();
            //retorna
            if (aux.equals("[]")) return "NENHUM ELEMENTO";
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
