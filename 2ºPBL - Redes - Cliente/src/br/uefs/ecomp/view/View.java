package br.uefs.ecomp.view;

import br.uefs.ecomp.util.ManipularArquivo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


public class View {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        TelaPrincipal tela = new TelaPrincipal();
        tela.setVisible(true);
    }
}