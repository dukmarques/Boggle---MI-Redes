package br.ecomp.uefs.util;

import java.util.Random;

public class LetrasDados {
    String[][] letras = {
            {"R", "I", "F", "O", "B", "X"},
            {"I", "F", "E", "H", "E", "Y"},
            {"D", "E", "N", "O", "W", "S"},
            {"U", "T", "O", "K", "N", "D"},
            {"H", "M", "S", "R", "A", "O"},
            {"L", "U", "P", "E", "T", "S"},
            {"A", "C", "I", "T", "O", "A"},
            {"Y", "L", "G", "K", "U", "E"},
            {"Qu", "B", "M", "J", "O", "A"},
            {"E", "H", "I", "S", "P", "N"},
            {"V", "E", "T", "I", "G", "N"},
            {"B", "A", "L", "I", "Y", "T"},
            {"E", "Z", "A", "V", "N", "D"},
            {"R", "A", "L", "E", "S", "C"},
            {"U", "W", "I", "L", "R", "G"},
            {"P", "A", "C", "E", "M", "D"}};

    public String[][] getLetras() {
        return letras;
    }
    
    public String[] letrasSort(){
        Random r = new Random();
        String[] sort = new String[16];
        for (int i = 0; i < sort.length; i++) {
            int j = r.nextInt(6);
            sort[i] = letras[i][j];
            //System.out.println(sort[i] +" "+ j);
        }
        return sort;
    }
}