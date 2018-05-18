package br.uefs.ecomp.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Sala implements Serializable{
    private int num;
    private int porta;
    private LinkedList<Jogadores> jogadores;

    public Sala(int num, int porta, LinkedList<Jogadores> jogadores) {
        this.num = num;
        this.porta = porta;
        this.jogadores = jogadores;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public LinkedList<Jogadores> getJogadores() {
        return jogadores;
    }

    public void setJogadores(LinkedList<Jogadores> jogadores) {
        this.jogadores = jogadores;
    }
    
    public String[] stringInfo(){
        String jg = "";
        
        Iterator itr = jogadores.iterator();
        while (itr.hasNext()) {
            Jogadores j = (Jogadores) itr.next();
            jg += j.getNick() + " ";
        }
        
        String[] sl = {"Sala " + num, jg};
        return sl;
    }
}