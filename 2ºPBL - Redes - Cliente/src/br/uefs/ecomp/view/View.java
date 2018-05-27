package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.ControllerCliente;
import br.uefs.ecomp.model.Pontos;
import br.uefs.ecomp.util.ManipularArquivo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class View {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException{
        Map<String, Integer> map = new HashMap<>();
        ControllerCliente c = new ControllerCliente();
        ManipularArquivo arq = new ManipularArquivo();
        
        map = arq.lerDicSerializado();
        
        LinkedList<String> list = new LinkedList<>();
        list.add("NICE");
        list.add("nice");
        
        int[] a = c.codificarLetras(map, list);
        
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }        
}