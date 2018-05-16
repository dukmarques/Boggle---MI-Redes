package br.uefs.ecomp.controller;

import br.uefs.ecomp.model.Comunicacao;
import br.uefs.ecomp.model.Sala;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class Controller {
    private LinkedList<Sala> listaSalas;
    
    public LinkedList<Sala> getSalas(Comunicacao c) throws ClassNotFoundException{
        try {
            Socket cliente = new Socket("127.0.0.1", 1223);
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            oos.flush();
            oos.writeObject(c);
            oos.reset();
            
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            listaSalas = (LinkedList<Sala>) ois.readObject();
            
            ois.close();
            
            return listaSalas;
        } catch (IOException ex) {
            return null;
        }
    }
}