package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.Controller;
import java.net.UnknownHostException;

public class View {

    public static void main(String[] args) throws UnknownHostException {
        Controller c = new Controller();
        
        c.criarSalas("DuK");
        c.criarSalas("PHP");
        c.criarSalas("Java");
        c.criarSalas("CSS");
        c.criarSalas("HTML5");
        c.criarSalas("Redes");
        c.startServidorCliente(c);
    }
}