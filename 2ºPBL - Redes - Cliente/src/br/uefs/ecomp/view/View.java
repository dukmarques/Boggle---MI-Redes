package br.uefs.ecomp.view;

import java.util.Iterator;
import java.util.LinkedList;

public class View {
    public static void main(String[] args) throws InterruptedException{
        LinkedList<String> s = new LinkedList<>();
        
        s.add("a");
        s.add("b");
        
        s.remove("a");
        
        Iterator itr = s.iterator();
        while (itr.hasNext()) {
            String c = (String) itr.next();
            System.out.println(c);
        }
    }        
}