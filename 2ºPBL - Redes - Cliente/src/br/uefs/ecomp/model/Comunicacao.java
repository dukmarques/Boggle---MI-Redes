package br.uefs.ecomp.model;

import java.io.Serializable;

public class Comunicacao implements Serializable{
    private boolean requisicao;
    private Jogadores jogador;

    public Comunicacao(boolean requisicao) {
        this.requisicao = requisicao;
    }

    public boolean isRequisicao() {
        return requisicao;
    }

    public void setRequisicao(boolean requisicao) {
        this.requisicao = requisicao;
    }

    public Jogadores getJogador() {
        return jogador;
    }

    public void setJogador(Jogadores jogador) {
        this.jogador = jogador;
    }
}