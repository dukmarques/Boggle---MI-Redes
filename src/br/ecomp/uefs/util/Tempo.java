package br.ecomp.uefs.util;

import br.uefs.ecomp.view.View;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


public class Tempo extends Thread{
    private javax.swing.JLabel tempo;

    public Tempo(JLabel tempo) {
        this.tempo = tempo;
    }
    
    @Override
    public void run(){
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
                if (segundos < 10) {
                    tempo.setText("0"+minutos+":"+"0"+segundos);
                }else{
                    tempo.setText("0"+minutos+":"+segundos);
                }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}