package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.ControllerServidor;
import java.net.UnknownHostException;

public class View {

    public static void main(String[] args) throws UnknownHostException {
        ControllerServidor c = new ControllerServidor();
        
        //Iniciar o servidor para requisição dos clientes.
        c.startServidorCliente(c);
    }
}