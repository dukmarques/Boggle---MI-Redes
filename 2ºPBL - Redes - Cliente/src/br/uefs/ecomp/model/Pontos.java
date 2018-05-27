package br.uefs.ecomp.model;

public class Pontos implements Comparable{
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
    
    public String[] formatTabela(){
        String[] s = {"º", this.jogador, ""+this.pontos};
        return s;
    }

    @Override
    public int compareTo(Object t) {
        Pontos p = (Pontos) t;
        if (this.pontos < p.getPontos()) {
            return -1;
        }
        if (this.pontos > p.getPontos()) {
            return 1;
        }
        return 0;
    }
}