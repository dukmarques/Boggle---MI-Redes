package br.uefs.ecomp.controller;

import br.ecomp.uefs.util.ServidorCliente;
import br.uefs.ecomp.model.Salas;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Controller {
    int IdSala = 0, contMulticast = 0, porta = 1221;
    String ipMulticast = "239.0.0.";
    //Lista para salvar as salas abertas no jogo.
    private LinkedList<Salas> listaSalas = new LinkedList<>();
    
    public void startServidorCliente(Controller c){
        new ServidorCliente(c).start();
    }
    
    public LinkedList<Salas> getSalas(){
        if (listaSalas.isEmpty()) {
            return null;
        }else{
            return listaSalas;
        }
    }
    
    public Salas criarSalas(String jogador) throws UnknownHostException{
        Salas s = new Salas(IdSala, InetAddress.getByName(ipMulticast+contMulticast), porta);
        s.addJogador(jogador);
        IdSala++; contMulticast++; porta++;
        listaSalas.add(s);
        return s;
    }
}