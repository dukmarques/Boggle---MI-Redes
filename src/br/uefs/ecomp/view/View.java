/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.view;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class View {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int segundos = 60, minutos = 2;
        
        while (true) {
            try {
                segundos--;
                
                if (minutos == 0 && segundos == -1) {
                    return;
                }
                if (segundos == -1) {
                    minutos--;
                    segundos = 59;
                }
                System.out.println("Tempo: 0"+minutos + ":" + segundos);
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
