package br.uefs.ecomp.controller;

import br.uefs.ecomp.model.Comunicacao;
import br.uefs.ecomp.model.ComunicacaoJogo;
import br.uefs.ecomp.model.Jogadores;
import br.uefs.ecomp.model.Sala;
import br.uefs.ecomp.view.SalaPlay;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ControllerCliente {
    private LinkedList<Sala> listaSalas;
    
    private MulticastSocket socket;
    
    public LinkedList<Sala> getSalas(Comunicacao c) throws ClassNotFoundException{
        try {
            Socket cliente = new Socket("127.0.0.1", 1223);
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.flush();
            oos.writeObject(c);
            
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            listaSalas = (LinkedList<Sala>) ois.readObject();
            
            ois.close();
            
            return listaSalas;
        } catch (IOException ex) {
            //Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Sala requisitaServer(Comunicacao c) throws ClassNotFoundException{
        try{
            Socket cliente = new Socket("127.0.0.1", 1223);
            
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.flush();
            oos.writeObject(c);
            
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            Sala s = (Sala) ois.readObject();
            ois.close();
            
            return s;
        } catch(IOException ex){
            return null;
        }
    }
    
    public Sala criarSala(String jogador) throws ClassNotFoundException, UnknownHostException{
        Comunicacao c = new Comunicacao(2);
        Jogadores jg = new Jogadores(jogador);
        c.setJogador(jg);
        Sala s = requisitaServer(c);
        
        return s;
    }
    
    public Sala entrarSala(String jogador, int numSala) throws ClassNotFoundException, UnknownHostException{
        Comunicacao c = new Comunicacao(3);
        Jogadores jg = new Jogadores(jogador);
        c.setJogador(jg);
        c.setNumSala(numSala);
        Sala s = requisitaServer(c);
        
        return s;
    }
    
    public void startMulticast(Sala s, javax.swing.JTable tabela, Jogadores j){
        try {
            socket = new MulticastSocket(s.getPorta());
            InetAddress enderecoMulticast = InetAddress.getByName("236.52.65.8");
            socket.joinGroup(enderecoMulticast);
            
            byte[] recebe = new byte[1024];
            DatagramPacket datagrama = new DatagramPacket(recebe, recebe.length);
            
            //Thread para conexão.
            new Thread(){
                @Override
                public void run(){
                    while (true) {
                        try {
                            socket.receive(datagrama);
                           
                            byte[] recebido = datagrama.getData();
                            ByteArrayInputStream bais = new ByteArrayInputStream(recebido);
                            ObjectInputStream ois = new ObjectInputStream(bais);
                            ComunicacaoJogo c = (ComunicacaoJogo) ois.readObject();
                            
                            trataComunicacao(s, c, tabela, j);
                        } catch (IOException ex) {
                            Logger.getLogger(SalaPlay.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();
            
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void trataComunicacao(Sala s, ComunicacaoJogo c, javax.swing.JTable tabela, Jogadores j){
        
        System.out.println("Mensagem recebida no Multicast do jogador :" + c.getJogador().getNick() + "info:" + c.getRequisicao());
        if (!c.getJogador().getNick().equals(j.getNick())) {
            //Se a codificação for 1. significa que um novo jogador se conectou a sala.
            if (c.getRequisicao() == 1) {
                //Adiciona o novo jogador na lista de salas.
                s.getJogadores().add(c.getJogador());
                addJGTabela(tabela, s);
                return;
            }
            //Codificação for 2, significa que um jogador saiu da sala.
            if (c.getRequisicao() == 2) {
                removeJogadorSala(s, j); //Remove o jogador da sala.
                addJGTabela(tabela, s); //Atualiza a tabela.
            }
        }
    }
    
    public void avisarEntrouSala(Sala s, Jogadores j){
        try {
            InetAddress enderecoMulticast = InetAddress.getByName("236.52.65.8");
            
            ComunicacaoJogo c = new ComunicacaoJogo(1, j);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(c);
            oos.close();
            
            byte[] dados = baos.toByteArray();
            DatagramPacket datagrama = new DatagramPacket(dados, dados.length, enderecoMulticast, s.getPorta());
            socket.send(datagrama);
            System.out.println("Avisou entrar sala 2");
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Informa ao servidor que ele se desconectou da sala.
    //Informa aos jogadores da sala que ele se desconectou da mesma.
    public void informarSaidaServer(Sala s, Jogadores j) throws ClassNotFoundException{
        try {
            Comunicacao c = new Comunicacao(4);
            c.setJogador(j);
            c.setNumSala(s.getNum());
            
            Socket cliente = new Socket("127.0.0.1", 1223);
            
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.flush();
            oos.writeObject(c);
            oos.close();
            cliente.close();
            //requisitaServer(c); //Envia para o servidor
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void informarSaidaSala(Sala s, Jogadores j){
        try {
            InetAddress enderecoMulticast = InetAddress.getByName("236.52.65.8");
            
            ComunicacaoJogo c = new ComunicacaoJogo(2, j);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(c);
            oos.close();
            
            byte[] dados = baos.toByteArray();
            DatagramPacket datagrama = new DatagramPacket(dados, dados.length, enderecoMulticast, s.getPorta());
            socket.send(datagrama);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addJGTabela(javax.swing.JTable tabela, Sala s){
        DefaultTableModel tbl = (DefaultTableModel) tabela.getModel();
        tbl.setRowCount(0);
        
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores j = (Jogadores) itr.next();
            String[] str = {j.getNick(), ""};
            tbl.addRow(str);
        }
    }
    
    public boolean validaNick(javax.swing.JTextField nick){
        if (nick.getText().trim().length() == 0 || nick.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    public void removeJogadorSala(Sala s, Jogadores j){
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores jg = (Jogadores) itr.next();
            if (jg.getNick().equals(j.getNick())) {
                s.getJogadores().remove(jg);
            }
        }
    }
}