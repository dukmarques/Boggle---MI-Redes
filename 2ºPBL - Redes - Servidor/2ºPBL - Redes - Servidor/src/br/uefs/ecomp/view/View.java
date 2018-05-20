package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.ControllerServidor;
import br.uefs.ecomp.model.Jogadores;
import java.net.UnknownHostException;

public class View {

    public static void main(String[] args) throws UnknownHostException {
        ControllerServidor c = new ControllerServidor();
        
//        Jogadores jg1 = new Jogadores("DuK");
//        Jogadores jg2 = new Jogadores("PHP");
//        Jogadores jg3 = new Jogadores("Java");
//        Jogadores jg4 = new Jogadores("CSS");
//        Jogadores jg5 = new Jogadores("HTML5");
//        
//        
//        c.criarSalas(jg1);
//        c.criarSalas(jg2);
//        c.criarSalas(jg3);
//        c.criarSalas(jg4);
//        c.criarSalas(jg5);
        c.startServidorCliente(c);
    }
}