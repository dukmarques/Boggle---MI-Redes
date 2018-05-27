package br.uefs.ecomp.model;

public class Pontos {
    private String jogador;
    private int pontos;

    public Pontos(String jogador, int pontos) {
        this.jogador = jogador;
        this.pontos = pontos;
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}