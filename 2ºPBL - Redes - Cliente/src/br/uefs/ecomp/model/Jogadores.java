package br.uefs.ecomp.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class Jogadores implements Serializable{
    private String nick;
    private String ip;
    private String porta;
    private int pontos;
    private int[] codPalavras;
    private LinkedList<String> palavras;

    public Jogadores(String nick) throws UnknownHostException {
        this.nick = nick;
        this.ip = InetAddress.getLocalHost().getHostAddress();
        this.porta = "1221";
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public int[] getCodPalavras() {
        return codPalavras;
    }

    public void setCodPalavras(int[] codPalavras) {
        this.codPalavras = codPalavras;
    }

    public LinkedList<String> getPalavras() {
        return palavras;
    }

    public void setPalavras(LinkedList<String> palavras) {
        this.palavras = palavras;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public String[] stringResultado() {
        String[] s = {"",this.nick, ""+this.pontos};
        
        return s;
    }
}