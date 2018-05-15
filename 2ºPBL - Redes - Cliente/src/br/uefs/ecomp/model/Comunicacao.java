package br.uefs.ecomp.model;

import java.io.Serializable;

public class Comunicacao implements Serializable{
    private boolean requisicao;
    private String jogador;

    public Comunicacao(boolean requisicao) {
        this.requisicao = requisicao;
    }

    public boolean isRequisicao() {
        return requisicao;
    }

    public void setRequisicao(boolean requisicao) {
        this.requisicao = requisicao;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }
}