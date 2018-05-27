package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.ControllerCliente;
import br.uefs.ecomp.model.Pontos;
import br.uefs.ecomp.util.ManipularArquivo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class View {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException{
        LinkedList<Pontos> lista = new LinkedList<>();
        
        Pontos p3 = new Pontos("GeL", 9862);
        Pontos p1 = new Pontos("Edu", 4234);
        Pontos p2 = new Pontos("DuK", 1234);
        
        lista.add(p3);
        lista.add(p1);
        lista.add(p2);
        
        Iterator itr = lista.iterator();
        while (itr.hasNext()) {
            Pontos p = (Pontos) itr.next();
            System.out.println(p.getJogador() + " " + p.getPontos());
        }
        
        Collections.sort(lista);
        
        System.out.println("\nOrdenado: ");
        Iterator itr2 = lista.iterator();
        while (itr2.hasNext()) {
            Pontos p = (Pontos) itr2.next();
            System.out.println(p.getJogador() + " " + p.getPontos());
        }
        
        System.out.println("\nDecrescete:");
        for (int i = lista.size()-1; i >= 0 ; i--) {
            System.out.println(lista.get(i).getJogador());
        }
    }        
}