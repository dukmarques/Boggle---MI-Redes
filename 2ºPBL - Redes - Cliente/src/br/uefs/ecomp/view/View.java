package br.uefs.ecomp.view;

import java.util.HashMap;
import java.util.Map;

public class View {
    public static void main(String[] args) throws InterruptedException{
        
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            if (i == 5) {
                break;
            }
        }
        
        
//        Map<String, Integer> map = new HashMap<>();
//        Map<Integer, String> pam = new HashMap<>();
//        
//        map.put("Cuscuz", 10);
//        pam.put(10, "Cuscuz");
//        
//        map.put("Android", 17);
//        pam.put(17, "Android");
//        
//        System.out.println(map.get("Cuscuz") + " " + pam.get(map.get("Cuscuz")));
//        System.out.println(map.get("Android") + " " + pam.get(map.get("Android")));
    }        
}