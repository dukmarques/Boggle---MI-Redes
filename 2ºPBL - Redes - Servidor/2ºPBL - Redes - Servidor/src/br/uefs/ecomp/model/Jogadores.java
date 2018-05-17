package br.uefs.ecomp.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Jogadores {
    private String nick;
    private String ip;
    private String porta;

    public Jogadores(String nick, String porta) throws UnknownHostException {
        this.nick = nick;
        this.ip = InetAddress.getLocalHost().getHostAddress();
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