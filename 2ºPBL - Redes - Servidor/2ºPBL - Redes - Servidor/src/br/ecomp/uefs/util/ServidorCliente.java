package br.ecomp.uefs.util;

import br.uefs.ecomp.controller.ControllerServidor;
import br.uefs.ecomp.model.Comunicacao;
import br.uefs.ecomp.model.Sala;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorCliente extends Thread{
    private Socket cliente;
    private ControllerServidor c;

    public ServidorCliente(ControllerServidor c, Socket cliente) {
        this.c = c;
        this.cliente = cliente;
    }
    
    @Override
    public void run(){
        try {
            //Recebe a requisição do cliente.
            ObjectInputStream r = new ObjectInputStream(cliente.getInputStream());
            Comunicacao com = (Comunicacao) r.readObject();
            
            //Caso o valor do atributo requisição da classe Comunicação for true, indica que o cliente está requisitando as salas
            //existentes no servidor!
            if (com.isRequisicao()) {
                System.out.println("Salas requisitadas!");
                LinkedList<Sala> s = c.getSalas();
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                oos.flush();
                oos.writeObject(s);
                oos.close();
            }
            
            //Caso o valor do atributo for false, indica que o cliente está requisitando a criação de uma nova sala.
            if (!com.isRequisicao()) {
                System.out.println("Criar sala requisitado!");
                Sala s = c.criarSalas(com.getJogador());
                
                ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
                oos.flush();
                oos.writeObject(s);
                oos.close();
            }
            cliente.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("Aconteceu algum erro na comunicação!");
        } catch (UnknownHostException ex) {
            System.out.println("Aconteceu algum erro na comunicação!");
            //Logger.getLogger(ServidorCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Aconteceu algum erro na comunicação!");
            //Logger.getLogger(ServidorCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}