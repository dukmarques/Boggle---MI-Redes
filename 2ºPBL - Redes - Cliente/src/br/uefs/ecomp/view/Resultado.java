package br.uefs.ecomp.view;

import br.uefs.ecomp.controller.ControllerCliente;
import br.uefs.ecomp.model.Jogadores;
import br.uefs.ecomp.model.Pontos;
import br.uefs.ecomp.model.Sala;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Resultado extends javax.swing.JDialog {
    private ControllerCliente c;
    private Sala s;
    private LinkedList<Pontos> resultado = new LinkedList<>();
    Map<Integer, String> pam;
    boolean calculando;
    
    /**
     * Creates new form Resultado
     */

    public Resultado(java.awt.Frame parent, boolean modal, ControllerCliente c, Map<Integer, String> pam, Sala s) {
        super(parent, modal);
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/br/uefs/ecomp/icons/b.png")).getImage());
        this.c = c;
        this.pam = pam;
        this.s = s;
        configs();
        calculaPontos();
    }
    
    public Resultado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fecharDialogo();
    }
    
    //Inicia a tela informando que está sendo feito o calculo de pontos.
    private void configs(){
        calculando = true;
        new Thread(){
            @Override
            public void run(){
                for (int i = 0; calculando; i++) {
                    try {
                        if (i == 0) {
                            info.setText("Calculando pontuações .");
                        }else if(i == 1){
                            info.setText("Calculando pontuações ..");
                        }else{
                            info.setText("Calculando pontuações ...");
                            i = -1;
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Resultado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        
    }
    
    //cria um Thread para anular as palavras iguais usando os códigos das letras
    //Em seguida decodifica as letras de cada jogador e por fim calcula os pontos.
    //Depois é feita a ordenação da lista e a listagem de ordem decrescente de modo que o jogador com maior ponto seja o primeiro.
    public void calculaPontos(){
        new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(5000);
                    c.anularPalavrasIguais(s);
                    decodificarPalavras();
                    calculando = false;
                    listaVencedor();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Resultado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    
    //Método utilizado para decodificar todas as letras formadas pelos jogadores!
    private void decodificarPalavras(){
        Iterator itr = s.getJogadores().iterator();
        while (itr.hasNext()) {
            Jogadores j = (Jogadores) itr.next();
            j.setPalavras(c.decodificarLetras(pam, j.getCodPalavras()));
        }
        //Após decodificar as palavras é feito o calculo de pontos com as palavras formadas!
        c.calcularPontos(s);
    }
    
    //Lista os jogadores na tabela com colocação, nome e pontos.
    private void listaVencedor(){
        Iterator itera = s.getJogadores().iterator();
        while (itera.hasNext()) {
            Jogadores j = (Jogadores) itera.next();
            Pontos p = new Pontos(j.getNick(), j.getPontos());
            resultado.add(p);
        }
        
        Collections.sort(resultado);
        
        DefaultTableModel tabela = (DefaultTableModel) vencedores.getModel();
        vencedor.setText(resultado.getLast().getJogador());
        
        for (int i = resultado.size()-1; i >= 0; i--) {
            String[] s = resultado.get(i).formatTabela();
            s[0] = (tabela.getRowCount()+1)+s[0];
            tabela.addRow(s);
        }
        
        fecharDialogo();
    }
    
    //Após a listagem de colocação, é iniciada uma contagem para fechar a janela de resultador e iniciar nova partida.
    public void fecharDialogo(){
        boolean fechar = false;
        new Thread(){
            @Override
            public void run(){
                for (int i = 10; i >= 0; i--) {
                    try {
                        info.setText("Iniciando nova partida em " + i + " segundos");
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Resultado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                dispose();
            }
        }.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        vencedores = new javax.swing.JTable();
        vencedor = new javax.swing.JLabel();
        info = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Boggle");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/uefs/ecomp/icons/boggle1.png"))); // NOI18N

        vencedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Posição", "Nick", "Pontos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        vencedores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(vencedores);
        if (vencedores.getColumnModel().getColumnCount() > 0) {
            vencedores.getColumnModel().getColumn(0).setResizable(false);
            vencedores.getColumnModel().getColumn(0).setPreferredWidth(10);
            vencedores.getColumnModel().getColumn(1).setResizable(false);
            vencedores.getColumnModel().getColumn(2).setResizable(false);
            vencedores.getColumnModel().getColumn(2).setPreferredWidth(10);
        }

        vencedor.setFont(new java.awt.Font("Maiandra GD", 1, 18)); // NOI18N
        vencedor.setForeground(new java.awt.Color(255, 255, 255));
        vencedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/uefs/ecomp/icons/win.png"))); // NOI18N
        vencedor.setText("Jogador");

        info.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        info.setForeground(new java.awt.Color(255, 255, 255));
        info.setText("test");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(vencedor)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vencedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(info)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Resultado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Resultado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Resultado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Resultado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Resultado dialog = new Resultado(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel vencedor;
    private javax.swing.JTable vencedores;
    // End of variables declaration//GEN-END:variables
}
