package br.uefs.ecomp.model;

import java.io.Serializable;

public class Comunicacao implements Serializable{
    private int requisicao;
    private Jogadores jogador;
    private int numSala;

    public Comunicacao(int requisicao) {
        this.requisicao = requisicao;
    }
    
    public int getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(int requisicao) {
        this.requisicao = requisicao;
    }

    public Jogadores getJogador() {
        return jogador;
    }

    public void setJogador(Jogadores jogador) {
        this.jogador = jogador;
    }

    public int getNumSala() {
        return numSala;
    }

    public void setNumSala(int numSala) {
        this.numSala = numSala;
    }
}