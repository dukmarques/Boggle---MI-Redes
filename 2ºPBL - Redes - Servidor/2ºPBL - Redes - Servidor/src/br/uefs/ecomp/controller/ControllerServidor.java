package br.uefs.ecomp.controller;

import br.ecomp.uefs.util.ServidorCliente;
import br.uefs.ecomp.model.Jogadores;
import br.uefs.ecomp.model.Sala;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerServidor {
    int IdSala = 0, porta = 2000;
    //Lista para salvar as salas abertas no jogo.
    private LinkedList<Sala> listaSalas = new LinkedList<>();
    
    public void startServidorCliente(ControllerServidor c){
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
            Logger.getLogger(ControllerServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LinkedList<Sala> getSalas(){
            return listaSalas;
    }
    
    public Sala criarSalas(Jogadores jogador) throws UnknownHostException{
        //Cria uma lista de jogadores para a sala e adiocionar o jogador que a criou nela.
        LinkedList<Jogadores> jogadores = new LinkedList<>();
        jogadores.add(jogador);
        
        //Instancia uma sala.
        Sala s = new Sala(IdSala, porta, jogadores);
        
        IdSala++; //Incrementa o ID da Sala.
        porta++; //Incrementa a porta para o multicast da sala.
        listaSalas.add(s); //Adiciona a nova sala na lista de salas.
        return s;
    }
    
    public Sala entrarSala(int num, Jogadores j){
        Sala s = iteratorSalas(num);
        LinkedList<Jogadores> jogadores = s.getJogadores();
        
        if (jogadores.size() < 8) {
            jogadores.add(j);
        }else{
            s = new Sala(-1, -1, null);
        }
        
        return s;
    }
    
    public void removeJogadorSala(Jogadores j, int numSala){
        Sala s = iteratorSalas(numSala); //Recebe a sala informada.
        removeJogador(s, j);
        
        if (s.getJogadores().isEmpty()) { //Verifica se a sala ficou vazia, caso sim, exclui a sala.
            System.out.println("Sala ficou vazia e foi desfeita!");
            listaSalas.remove(s);
        }
    }
    
    private Sala iteratorSalas(int num){
        Sala s = null;
        Iterator itr = listaSalas.iterator();
        while (itr.hasNext()) {
            s = (Sala) itr.next();
            if (s.getNum() == num) {
                return s;
            }
        }
        return s;
    }
    
    private void removeJogador(Sala s, Jogadores j){
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores jg = (Jogadores) itr.next();
            if (jg.getNick().equals(j.getNick())) {
                s.getJogadores().remove(jg);
            }
        }
    }
}