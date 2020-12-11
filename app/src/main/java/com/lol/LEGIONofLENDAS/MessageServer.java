package com.lol.LEGIONofLENDAS;

import java.util.ArrayList;
/**
 * CLASS MESSAGESERVER
 * por vias de problemas a atualizar a UI (maybe passar uma view como argumento?)
 * a class servoce serve apenas para inicar o serviço
 * a thread é criada do lado do cliente
 * do lado de cada janela é required implementar onStart e onStop do bind e um service connection
 * como uma variavel para classe e um boolean de vinculo
 */

public class MessageServer {


    /**
     *
     * @param sessionID Id do utilizador
     * @param pedidoAFazer Task necessaria a ser feita check switch
     * @param argumentos Conteudo que precisa ser enviado check como esta referida na classe client
     * @return Retorna o output do server
     */
    public static String callService(String sessionID,String pedidoAFazer,ArrayList<String> argumentos){

        StringBuilder posparsed = new StringBuilder();
        //Wrapper de funções, á possibilidade da necessidade de recriar  o if a baixo
        for(String arg : argumentos) posparsed.append(arg).append(" ");
        // Start client
        Client client = new Client();
        /*
           wrapper parser
          ver a definição de cada função para perceber o input
          operações com o user -1 retornam executed, em vez do output em concreto
          o USER -1 serve so para controlo administrativo de toda a base de dados
          usem apenas os metodos seguros, ou seja que não usam o user -1 diretamente,
          ou seja o metodo TESTINGADMIN como esta descrito explicitamente

        */
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
                return client.inserirStatusUserDB(sessionID,argumentos.get(0),argumentos.get(1),argumentos.get(2),argumentos.get(3),argumentos.get(4),argumentos.get(5),argumentos.get(6));
            case "rankingxp":
                return client.rankingByXpTopN(Integer.parseInt(argumentos.get(0)));
            case "TESTINGADMIN":
                return client.operacaoNaBD("-1",argumentos.get(0));
            default:
                return "INVALID FUNCTION";
        }
    }
}
