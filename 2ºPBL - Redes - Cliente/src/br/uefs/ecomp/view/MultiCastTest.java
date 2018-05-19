package br.uefs.ecomp.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiCastTest {
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        
        Scanner scan = new Scanner(System.in);
        int porta = 9999;
        int id;
        System.out.print("Informe o seu ID: ");
        id = scan.nextInt();
        
        MulticastSocket socket = new MulticastSocket(porta);
        InetAddress enderecoMulticast = InetAddress.getByName("236.52.65.8");
        socket.joinGroup(enderecoMulticast);
        
        new ThreadRecebe(socket, id).start();
        //byte dados[] = new byte[1];
        
        Mensagem m = new Mensagem(id, "Olá");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(m);
        oos.close();
        
        byte[] dados = baos.toByteArray();
        DatagramPacket datagrama = new DatagramPacket(dados, dados.length, enderecoMulticast, porta);
        
        while (true) {
            socket.send(datagrama);
            Thread.sleep(2000);
        }
    }
}

class Mensagem implements Serializable{
    private int id;
    private String msg;

    public Mensagem(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

class ThreadRecebe extends Thread{
    MulticastSocket socket;
    int id;
    public ThreadRecebe(MulticastSocket socket, int id) {
        this.socket = socket;
        this.id = id;
    }
    
    @Override
    public void run(){
        try{
            byte[] recebe = new byte[1024];
            
            while (true) {
                DatagramPacket datagrama = new DatagramPacket(recebe, recebe.length);
                socket.receive(datagrama);
                
                byte[] recebido = datagrama.getData();
                ByteArrayInputStream bais = new ByteArrayInputStream(recebido);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Mensagem m = (Mensagem) ois.readObject();
                ois.close();
                
                if (!(m.getId() == this.id)) {
                    System.out.println(m.getId() +" " + m.getMsg());
                }            
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadRecebe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadRecebe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}