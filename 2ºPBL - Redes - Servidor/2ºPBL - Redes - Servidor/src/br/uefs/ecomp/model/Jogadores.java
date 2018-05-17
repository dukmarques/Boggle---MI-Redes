package br.uefs.ecomp.model;

public class Jogadores {
    private String nick;
    private String ip;
    private String porta;

    public Jogadores(String nick, String ip, String porta) {
        this.nick = nick;
        this.ip = ip;
        this.porta = porta;
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
}