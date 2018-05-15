package br.uefs.ecomp.model;

import java.io.Serializable;
import java.net.InetAddress;

public class Salas implements Serializable{
    private int num;
    private InetAddress mcs;
    private int porta;
    private String jogadores[];

    public Salas(int num, InetAddress mcs, int porta) {
        this.num = num;
        this.mcs = mcs;
        this.porta = porta;
    }

    public void addJogador(String jogador){
        jogadores[jogadores.length] = jogador;
    }
    
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public InetAddress getMcs() {
        return mcs;
    }

    public void setMcs(InetAddress mcs) {
        this.mcs = mcs;
    }

    public String[] getJogadores() {
        return jogadores;
    }

    public void setJogadores(String[] jogadores) {
        this.jogadores = jogadores;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }
}