package br.uefs.ecomp.controller;

import br.uefs.ecomp.model.Comunicacao;
import br.uefs.ecomp.model.ComunicacaoJogo;
import br.uefs.ecomp.model.Jogadores;
import br.uefs.ecomp.model.Sala;
import br.uefs.ecomp.util.ManipularArquivo;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ControllerCliente {
    private LinkedList<Sala> listaSalas;
    private String[] config;
    private MulticastSocket socketSS;
    
    //Métodos para comunicação com o servidor!
    public LinkedList<Sala> getSalas(Comunicacao c) throws ClassNotFoundException{
        try {
            if (config == null) {
                ManipularArquivo arq = new ManipularArquivo();
                config = arq.lerArquivo();
            }
            
            Socket cliente = new Socket(config[0], Integer.parseInt(config[1]));
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
    
    //Método utilizado para enviar uma mensagem ao servidor
    private Sala requisitaServer(Comunicacao c) throws ClassNotFoundException{
        try{
            if (config == null) {
                ManipularArquivo arq = new ManipularArquivo();
                config = arq.lerArquivo();
            }
            Socket cliente = new Socket(config[0], Integer.parseInt(config[1]));
            
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
    
    //Método utilizado para criar a sala através da comunicação com o servidor.
    public Sala criarSala(Jogadores jogador) throws ClassNotFoundException, UnknownHostException{
        Comunicacao c = new Comunicacao(2);
        //Jogadores jg = new Jogadores(jogador);
        c.setJogador(jogador);
        Sala s = requisitaServer(c);
        
        return s;
    }
    
    //Método utilizado para entrar na sala, caso o servidor a permita.
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
            
            if (config == null) {
                ManipularArquivo arq = new ManipularArquivo();
                config = arq.lerArquivo();
            }
            Socket cliente = new Socket(config[0], Integer.parseInt(config[1]));
            
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.flush();
            oos.writeObject(c);
            oos.close();
            cliente.close();
        } catch (IOException ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Utilizado para validar o nick do jogador, evitando que ele crie um nick vázio
    public boolean validaNick(javax.swing.JTextField nick){
        if (nick.getText().trim().length() == 0 || nick.getText().equals("")) {
            return false;
        }
        return true;
    }
    
    //Métodos da sala de jogo
    
    //Método utilizado para enviar datagramas para a sala multicast
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
    
    //Envia uma mensagem a sala informando que entrou na partida.
    public void informarEntradaJogo(MulticastSocket socket, Sala s, Jogadores j){
        ComunicacaoJogo c = new ComunicacaoJogo(1, j);
        comunicarSala(socket, s, c, "236.52.65.9");
    }
    
    //Enviar uma mensagem a sala informando que se desconectou da partida.
    public void informarSaidaJogo(MulticastSocket socket, Sala s, Jogadores j){
        ComunicacaoJogo c = new ComunicacaoJogo(2, j);
        comunicarSala(socket, s, c, "236.52.65.9");
    }

    //Método utilizado para o adm da sala enviar os dados da partida caso a mesma ja esteja em andamento.
    public void enviarDadosDaPartida(MulticastSocket socket, Sala s, Jogadores j, String[] letras, int min, int seg, int prog){
        ComunicacaoJogo c = new ComunicacaoJogo(3, j);
        c.setDados(letras);
        int t [] = new int[3];
        t[0] = min; t[1] = seg; t[2] = prog;

        c.setTempo(t);
        
        comunicarSala(socket, s, c, "236.52.65.9");
    }
    
    //Método utilizado pelo adm da sala para dar inicio a partida!
    public void iniciarPartida(MulticastSocket socket, Sala s, Jogadores j, String[] letras){
        ComunicacaoJogo c = new ComunicacaoJogo(4, j);
        c.setDados(letras);
        comunicarSala(socket, s, c, "236.52.65.9");
    }
    
    public void enviarLetras(MulticastSocket socket, Sala s, Jogadores j, Map<String, Integer> map, LinkedList<String> palavras){
        j.setCodPalavras(codificarLetras(map, palavras));
        ComunicacaoJogo c = new ComunicacaoJogo(5, j);
        addPontosJogador(s, j);
        
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
                jg.setCodPalavras(j.getCodPalavras());
            }
        }
    }
    
    //Método responsável por comparar todas as letras formadas pelos jogadores e remover as que forem iguais.
    public void anularPalavrasIguais(Sala s){
        LinkedList<Integer> iguais = new LinkedList<>();
        Iterator itr = s.getJogadores().iterator();
        
        while (itr.hasNext()) {
            Jogadores j = (Jogadores) itr.next();
            int[] p1 = j.getCodPalavras();
            
            Iterator itr2 = s.getJogadores().iterator();
            
            while (itr2.hasNext()) {
                Jogadores jg = (Jogadores) itr2.next();
                int[] p2 = jg.getCodPalavras();
                
                if (!jg.getIp().equals(j.getIp())) {
                    for (int i = 0; i < p1.length; i++) {
                        for (int k = 0; k < p2.length; k++) {
                            if (p1[i] == p2[k]) {
                                iguais.add(p1[i]);
                            }
                        }
                    }
                }
            }
        }
        
        //Remove todas codificações de letras que foram feitas igualmente por outro jogador.
        Iterator it = s.getJogadores().iterator();
        while (it.hasNext()) {
            Jogadores j = (Jogadores) it.next();
            int[] cod = j.getCodPalavras();
            for (int i = 0; i < cod.length; i++) {
                for (int k = 0; k < iguais.size(); k++) {
                    if (cod[i] == iguais.get(k)) {
                        cod[i] = 0;
                    }
                }
            }
        }
    }
    
    //Calcula os pontos do jogador com base no tamanho das palavras, para cada letra 1 ponto.
    public void calcularPontos(Sala s){
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores j = (Jogadores) itr.next();
            
            Iterator itr2 = j.getPalavras().iterator();
            int pts = 0;
            while (itr2.hasNext()) {
                String plv = (String) itr2.next();
                pts += plv.length();
            }
            j.setPontos(pts);
        }
    }
    
    //Recebe a lista de letras do jogador e transforma num array de inteiros, onde cada inteiro é o código da letra
    //Usado para diminuir a quantidade de dados enviados no multicast.
    public int[] codificarLetras(Map<String, Integer> map, LinkedList<String> palavras){
        int[] plv = new int[palavras.size()];
        
        Iterator itr = palavras.iterator();
        for (int i = 0; itr.hasNext(); i++) {
            String p = (String) itr.next();
            
            if (map.get(p.toLowerCase()) != null) {
                plv[i] = map.get(p.toLowerCase());
            }else{
                plv[i] = map.get(p.toUpperCase());
            }
        }
        return plv;
    }
    
    //Utilizado para decodificar as palavras enviadas pelos outros jogadores, utilizado os inteiros para procurar na HashMap a palavra.
    public LinkedList<String> decodificarLetras(Map<Integer, String> pam, int[] palavras){
        LinkedList<String> plv = new LinkedList<>();
        
        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i] !=0) {
                plv.add(pam.get(palavras[i]));
            }
        }
        return plv;
    }
}