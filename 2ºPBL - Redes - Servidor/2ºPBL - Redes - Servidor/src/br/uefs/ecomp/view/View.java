package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.Controller;
import java.net.UnknownHostException;

public class View {

    public static void main(String[] args) throws UnknownHostException {
        Controller c = new Controller();
        
        c.startServidorCliente(c);
        c.criarSalas("DuK");
    }
}