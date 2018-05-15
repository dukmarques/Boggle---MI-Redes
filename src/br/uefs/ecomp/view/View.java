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