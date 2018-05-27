package br.uefs.ecomp.model;

import java.io.Serializable;

public class ComunicacaoJogo implements Serializable {
    private int requisicao;
    private Jogadores jogador;
    private String[] dados;
    private int[] tempo;
    private int[] palavras;

    public ComunicacaoJogo(int requisicao, Jogadores jogador) {
        this.requisicao = requisicao;
        this.jogador = jogador;
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

    public String[] getDados() {
        return dados;
    }

    public void setDados(String[] dados) {
        this.dados = dados;
    }

    public int[] getTempo() {
        return tempo;
    }

    public void setTempo(int[] tempo) {
        
        
        this.tempo = tempo;
    }

    public int[] getPalavras() {
        return palavras;
    }

    public void setPalavras(int[] palavras) {
        this.palavras = palavras;
    }
}