package br.uefs.ecomp.view;

import br.uefs.ecomp.model.Pontos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class View {
    public static void main(String[] args) throws InterruptedException{
        
        ArrayList<Pontos> a = new ArrayList<>();
        
        Pontos p = new Pontos("DuK", 10);
        Pontos p2 = new Pontos("Edu", 20);
        
        a.add(p2);
        a.add(p);
        
    }        
}