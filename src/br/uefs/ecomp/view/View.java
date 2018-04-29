/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.view;

import java.util.Random;

/**
 *
 * @author Eduardo
 */
public class View {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Random r = new Random();
        char l = (char)(65+r.nextInt(16));
        System.out.println(l);
    }
    
}
