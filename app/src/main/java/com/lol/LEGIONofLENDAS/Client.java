package com.lol.LEGIONofLENDAS;

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
import java.util.Objects;


public class Client {
    //ip e port
    private static final String IP = "82.155.86.142";
    private static final int PORT  =  3003;

    /**
     * função de escrita utilitaria
     * @param param1 primeiro elemento
     * @param param2 segundo elemento
     * @param param3 terceiro elemento
     * @return param1 + "\n" + param2 + "\n" + param3
     */
    private String format(String param1, String param2, String param3){
        return param1 + "\n" + param2 + "\n" + param3;
    }
    /**
     * Permite o utilizador comprar ou vender itens
     * @param userId id do utilizador
     * @param operacaoAFazer comprar ou vender
     * @param items itens num array list
     * @return ver sendMessage
     */
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
    /**
     * mostra os itens da loja
     * @return ver sendMessage
     */
    public String showLOJA(){
        return sendMESSAGE(format("-1","DB","SELECT itemName,price,forca,magia,defesa,vida,level FROM loja;"));
    }
    /**
     * lutas não aleatorias
     * @param userId id do utilizador
     * @param oponenteID id do adversario em questão
     * @return ver sendMessage
     */
    public String fightNonRandom(String userId, String oponenteID){
        return sendMESSAGE(format(userId,"GAME",oponenteID));
    }
    /**
     * lutas aleatorias
     * @param userId id do utilizador
     * @param oponente pergunta se é do tipo player ou do tipo bot
     * @return ver sendMessage
     */
    public String fightRandom(String userId, String oponente){
        if (oponente.equals("Player")) return sendMESSAGE(format(userId,"GAME","Player"));
        return sendMESSAGE(format(userId,"GAME","Bot"));
    }
    /**
     * cria utilizadores
     * @param nome username da pessoa
     * @param password password pos transformada da pessoa
     * @param nivel nivel da pessoa
     * @param xp xp atual da pessoa
     * @param image boneco da pessoa
     * @return ver sendMessage
     */
    public String inserirUserNaDB(String nome, String password, String nivel,String xp, String image){
        password = getMd5Hash(password);
        String query = "INSERT INTO user (id,nome,password,level,xp,image) VALUES (NEWELE,"+"'"+nome+"','"+password+"',"+nivel+","+xp+","+image+");";
        return sendMESSAGE(format("0","DB",query));
    }
    /**
     * retorna o id o nome e a imagem do boneco, cada vez que faz login
     * @param username username da pessoa
     * @param password password pre transformada da pessoa
     * @return ver sendMessage
     */
    public String login(String username, String password){
        password = getMd5Hash(password);
        return sendMESSAGE(format("1","DB","SELECT id,nome,image,level,xp FROM user WHERE nome = '"+username+"' AND password = '"+password+"';"));
    }
    /**
     * atualiza os status do userID
     * @param id userID
     * @param forca status de forca do utilizador
     * @param magia status de magia do utilizador
     * @param defesa status de defesa do utilizador
     * @param defesaMagica status de defesaMagica do utilizador
     * @param vida status de vida do utilizador
     * @param vitorias numero de vitorias
     * @param derrotas numero de derrotas do utlizador
     * @return ver sendMessage
     */
    public String inserirStatusUserDB(String id, String forca, String magia, String defesa, String defesaMagica, String vida, String vitorias, String derrotas){
        String exists = showStatus(id);
        if(exists.equals("NENHUM ELEMENTO")){
            String query = "INSERT INTO status (statsid,forca,magia,defesa,vida,vitorias,derrotas,userid) VALUES "
                    + "("
                    + "NEWELE,"
                    + forca        + ","
                    + magia        + ","
                    + defesa       + ","
                    + defesaMagica + ","
                    + vida         + ","
                    + vitorias     + ","
                    + derrotas     + ","
                    + id
                    +");";
            return sendMESSAGE(format("0","DB",query));
        }
        String query = "UPDATE status SET "
                + "forca = "            + forca
                + ", magia = "          + magia
                + ", defesa = "         + defesa
                + ", defesaMagica = "   + defesaMagica
                + ", vida = "           + vida
                + ", vitorias = "       + vitorias
                + ", derrotas = "       + derrotas
                + " WHERE id = "        + exists
                + ";";
        return sendMESSAGE(format("1","DB",query));
    }
    /**
     * Mostra o top X de xp para motivos de comparação
     * @param quantidadeElementos numero de elementos que aparecem
     * @return ver sendMessage
     */
    public String rankingByXpTopN(int quantidadeElementos){
        return sendMESSAGE(format("1","DB","SELECT nome,level,xp FROM user WHERE id <> 0 ORDER BY xp DESC LIMIT "+quantidadeElementos+";"));
    }
    /**
     * Retorna uma string com os status da pessoa, necessario vir parsed
     * @param id id do user na base de dados
     * @return ver o sendMessage
     */
    public String showStatus(String id){
        return sendMESSAGE((format(id,"DB","SELECT statsid FROM status WHERE userid = "+id+";")));
    }
    /**
     * Retorna uma string com o inventario da pessoa, necessario vir parsed
     * @param id id do user na base de dados
     * @return ver o sendMessage
     */
    public String inventario(String id){
        String query = "SELECT nome, forca, magia, defesa, defesaMagica, vida FROM inventario WHERE userid = "+id+";";
        return sendMESSAGE(format(id,"DB",query));
    }
    /**
     * da Wrap do sendMessage para a base de dados
     * @param utilizadorExistenteUserID id do user na base de dados
     * @param operacaoAFazer query a base de dados
     * @return ver output de sendMessage
     */
    public String operacaoNaBD(String utilizadorExistenteUserID,String operacaoAFazer){
        return sendMESSAGE(format(utilizadorExistenteUserID,"DB", operacaoAFazer));
    }
    /**
     * Envia para através de um socket para o server uma mensagem que espera retorno
     * @param parsedMESSAGE mensagem formatada pronta para ser enviada para o server
     * @return mensagem recebida do server
     */
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
    /**
     * cria uma string em MD5
     * @param input string qualqer
     * @return uma string MD5
     */
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
    /**
     * Verifica se ja existe algum utilizador na BD
     * @param username nome a ser usado
     * @return DISPONIVEL ou NAO DISPONIVEL
     */
    public String checkIfExists(String username){
        String output = sendMESSAGE(format("1","DB","SELECT id FROM user WHERE nome ='"+username+"';"));
        Log.i("check", output );
        if (output.equals("NENHUM ELEMENTO")) return "DISPONIVEL";
        return "NAO DISPONIVEL";
    }
}
