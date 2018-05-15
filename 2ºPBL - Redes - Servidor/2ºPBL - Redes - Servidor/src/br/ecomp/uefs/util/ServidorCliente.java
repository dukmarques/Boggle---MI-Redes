package br.ecomp.uefs.util;

import br.uefs.ecomp.controller.Controller;
import br.uefs.ecomp.model.Comunicacao;
import br.uefs.ecomp.model.Salas;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServidorCliente extends Thread{
    private Socket cliente;
    private Controller c;

    public ServidorCliente(Controller c) {
        this.c = c;
    }
    
    @Override
    public void run(){
        try {
            ServerSocket servidor = new ServerSocket(1223);
            System.out.println("Servidor para conexão direta com o cliente aberta!");
            
            while (true) {
                cliente = servidor.accept();
                
                ObjectInputStream r = new ObjectInputStream(cliente.getInputStream());
                Comunicacao com = (Comunicacao) r.readObject();
                if (com.isRequisicao()) {
                    ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                    oos.flush();
                    oos.writeObject(c.getSalas());
                    oos.close();
                }
                if (!com.isRequisicao()) {
                    Salas s = c.criarSalas(com.getJogador());
                    ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                    oos.flush();
                    oos.writeObject(s);
                    oos.close();
                }
                cliente.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServidorCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}