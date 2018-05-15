package br.uefs.ecomp.view;

import br.uefs.ecomp.util.ManipularArquivo;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


public class View {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
//        Map<String, String> map = new HashMap<>();
        //Map<String, String> map = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        //HashSet<String> hset = new HashSet<>();
        
        ManipularArquivo arq = new ManipularArquivo();
        
        map2 = arq.lerDicSerializado();
        System.out.println(map2.size());
        
        System.out.println(map2.get("a"));
        
//        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
//            System.out.println(entry.getKey()+" : " +entry.getValue());
//        }
        
//        hset.add("Comida");
//        hset.add("Cuscuz");
//        hset.add("Fitness");
//        
//        System.out.println(hset.contains("Fitness"));
//        
//        Iterator itr = hset.iterator();
//        while (itr.hasNext()) {
//            String s = (String) itr.next();
//            System.out.println(s);
//        }
        
//        map2.put("teclado", 2);
//        map2.put("Voce", 3);
//        map2.put("Mouse", 4);
//        map2.put("Miojo", 1);
//        
//        System.out.println(map2.get("teclado"));
//        System.out.println(map2.get("Miojo"));

//        for (String key : map.values()) {
//            System.out.println(key);
//        }
//        
//        for (HashMap.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() +" "+ entry.getValue());
//        }
    }
}