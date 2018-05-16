package br.uefs.ecomp.model;

import java.io.Serializable;
import java.util.LinkedList;

public class Sala implements Serializable{
    private int num;
    private int porta;
    private LinkedList<String> jogadores;

    public Sala(int num, int porta, LinkedList<String> jogadores) {
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

    public LinkedList<String> getJogadores() {
        return jogadores;
    }

    public void setJogadores(LinkedList<String> jogadores) {
        this.jogadores = jogadores;
    }
}