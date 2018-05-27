package br.uefs.ecomp.controller;

import br.uefs.ecomp.model.Comunicacao;
import br.uefs.ecomp.model.ComunicacaoJogo;
import br.uefs.ecomp.model.Jogadores;
import br.uefs.ecomp.model.Sala;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControllerCliente {
    private LinkedList<Sala> listaSalas;
    
    private MulticastSocket socketSS;
    
    //Métodos para comunicação com o servidor!
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
    
    public Sala criarSala(Jogadores jogador) throws ClassNotFoundException, UnknownHostException{
        Comunicacao c = new Comunicacao(2);
        //Jogadores jg = new Jogadores(jogador);
        c.setJogador(jogador);
        Sala s = requisitaServer(c);
        
        return s;
    }
    
    public Sala entrarSala(Jogadores jogador, int numSala) throws ClassNotFoundException, UnknownHostException{
        Comunicacao c = new Comunicacao(3);
        //Jogadores jg = new Jogadores(jogador);
        c.setJogador(jogador);
        c.setNumSala(numSala);
        Sala s = requisitaServer(c);
        
        return s;
    }
    
    //Informa ao servidor que ele se desconectou da sala.
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
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validaNick(javax.swing.JTextField nick){
        if (nick.getText().trim().length() == 0 || nick.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    //Métodos da sala de jogo
    private void comunicarSala(MulticastSocket socket, Sala s, ComunicacaoJogo c, String endereco){
        try {
            InetAddress enderecoMulticast = InetAddress.getByName(endereco);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(c);
            oos.close();
            
            byte[] dados = baos.toByteArray();
            DatagramPacket datagrama = new DatagramPacket(dados, dados.length, enderecoMulticast, s.getPorta());
            socket.send(datagrama);
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void informarEntradaJogo(MulticastSocket socket, Sala s, Jogadores j){
        ComunicacaoJogo c = new ComunicacaoJogo(1, j);
        comunicarSala(socket, s, c, "236.52.65.9");
    }
    
    public void informarSaidaJogo(MulticastSocket socket, Sala s, Jogadores j){
        ComunicacaoJogo c = new ComunicacaoJogo(2, j);
        comunicarSala(socket, s, c, "236.52.65.9");
    }

    public void enviarDadosDaPartida(MulticastSocket socket, Sala s, Jogadores j, String[] letras, int min, int seg, int prog){
        ComunicacaoJogo c = new ComunicacaoJogo(3, j);
        c.setDados(letras);
        int t [] = new int[3];
        t[0] = min; t[1] = seg; t[2] = prog;

        c.setTempo(t);
        
        comunicarSala(socket, s, c, "236.52.65.9");
    }
    
    public void iniciarPartida(MulticastSocket socket, Sala s, Jogadores j, String[] letras){
        ComunicacaoJogo c = new ComunicacaoJogo(4, j);
        c.setDados(letras);
        comunicarSala(socket, s, c, "236.52.65.9");
    }
    
    public void enviarLetras(MulticastSocket socket, Sala s, Jogadores j, Map<String, Integer> map, LinkedList<String> palavras){
        ComunicacaoJogo c = new ComunicacaoJogo(5, j);
        c.setPalavras(codificarLetras(map, palavras));
        
        comunicarSala(socket, s, c, "236.52.65.9");
    }

    //Métodos de apoio --------------------------------------------------------
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

    public void removeJogadorSala(Sala s, Jogadores j){
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores jg = (Jogadores) itr.next();
            if (jg.getNick().equals(j.getNick())) {
                s.getJogadores().remove(jg);
            }
        }
    }
    
    public void addPontosJogador(Sala s, Jogadores j){
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores jg = (Jogadores) itr.next();
            if (jg.getNick().equals(j.getNick())) {
                jg.setPalavras(j.getPalavras());
                break;
            }
        }
    }
    
    private int[] codificarLetras(Map<String, Integer> map, LinkedList<String> palavras){
        int[] plv = new int[palavras.size()];
        
        Iterator itr = palavras.iterator();
        for (int i = 0; itr.hasNext(); i++) {
            String p = (String) itr.next();
            plv[i] = map.get(p);
        }
        return plv;
    }
    
    public String[] decodificarLetras(Map<String, Integer> map, Map<Integer, String> pam, int[] palavras){
        String[] plv = new String[palavras.length];
        
        for (int i = 0; i < palavras.length; i++) {
            plv[i] = pam.get(palavras[i]);
        }
        return plv;
    }
}