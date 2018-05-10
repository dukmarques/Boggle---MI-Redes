/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 *
 * @author Eduardo
 */
public class View {
    public static void main(String[] args) {
        JFrame tela = criarTela();
    }
    
    public static JFrame criarTela(){
        JFrame tela = new JFrame();
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setSize(550, 500);
        tela.setLocationRelativeTo(null);
        tela.setTitle("Boggle");
        
        //Adiciona os componentes da tela.
        ImageIcon img = new ImageIcon("br.uefs.ecomp.icons/boggle1.png");
        JLabel icone = new JLabel(img);
        /*
        JComboBox comboOrigem = new JComboBox();
        JComboBox comboDestino = new JComboBox();
        JButton botao = new JButton();
        JLabel label = new JLabel();
        JLabel labeladm = new JLabel();
        
        criarComboBox(c, comboOrigem, comboDestino);
        editBotao(botao, comboOrigem, comboDestino, c);
        text(label, "Calculo de menor caminho:");
        
        JPanel painel = painelUsuario(comboOrigem, comboDestino, botao, label);
        JPanel painel2 = painelAdm(c);
        
        
        //Cria um painel com aba, sendo uma para calculo de menor caminho e outra para definir o tempo de parada.
        JTabbedPane painelP = new JTabbedPane();
        painelP.add("Menor caminho", painel);
        painelP.add("Tempo parada", painel2);
        painelP.setSize(550, 500);
        painel.setVisible(true);
        painel.setLayout(null);
        */
        
        JLabel text = new JLabel("Testando");
        text.setBounds(30,320 , 300, 30);
        
        JPanel painel  = new JPanel();
        painel.add(icone);
        painel.setVisible(true);
        painel.setSize(500, 450);
        
        tela.add(painel);
        tela.setLayout(null);
        tela.setVisible(true);
        return tela;
    }
}