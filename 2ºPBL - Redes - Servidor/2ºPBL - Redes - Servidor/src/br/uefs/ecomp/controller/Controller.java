package br.uefs.ecomp.controller;

import br.ecomp.uefs.util.ServidorCliente;
import br.uefs.ecomp.model.Sala;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    int IdSala = 0, porta = 2000;
    //Lista para salvar as salas abertas no jogo.
    private LinkedList<Sala> listaSalas = new LinkedList<>();
    
    public void startServidorCliente(Controller c){
        try {
            ServerSocket servidor = new ServerSocket(1223);
            System.out.println("Socket para conexão de clientes aberta! Porta: 1223");
            
            while (true) {
                //Cria um socket de conexão para os clientes.
                Socket cliente = servidor.accept();
                
                //Cria uma Thread para cada cliente que se conectar ao servidor.
                new ServidorCliente(c, cliente).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LinkedList<Sala> getSalas(){
            return listaSalas;
    }
    
    public Sala criarSalas(String jogador) throws UnknownHostException{
        LinkedList<String> jogadores = new LinkedList<>();
        jogadores.add(jogador);
        
        Sala s = new Sala(IdSala, porta, jogadores);
        IdSala++;
        porta++;
        listaSalas.add(s);
        return s;
    }
}