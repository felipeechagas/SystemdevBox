/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.systemdevbox.telas;

import java.sql.*;
import br.com.systemdevbox.dao.ModuloConexao;
import javax.swing.JOptionPane;
//Importar recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Felipe Chagas
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Criação novo formulário TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //Método para inserir usuários
    private void adicionar() {
        String sql = "insert into tbclientes(nomeCli, cpfCli, endClli, bairroCli, cidadeCli, foneCli, emailCli) values(?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCli.getText());
            pst.setString(2, txtCpfCli.getText());
            pst.setString(3, txtEndClli.getText());
            pst.setString(4, txtBairroCli.getText());
            pst.setString(5, txtCidadeCli.getText());
            pst.setString(6, txtFoneCli.getText());
            pst.setString(7, txtEmailCli.getText());

            //validação dos campos obrigatórios
            if ((txtNomeCli.getText().isEmpty()) || (txtCpfCli.getText().isEmpty()) || (txtEndClli.getText().isEmpty()) || (txtBairroCli.getText().isEmpty()) || (txtCidadeCli.getText().isEmpty()) || (txtFoneCli.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {

                int adicionado = pst.executeUpdate(); //Atualiza a tabela com os dados do formulário
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");

                    //Limpar os campos
                    txtNomeCli.setText(null);
                    txtCpfCli.setText(null);
                    txtEndClli.setText(null);
                    txtBairroCli.setText(null);
                    txtCidadeCli.setText(null);
                    txtFoneCli.setText(null);
                    txtEmailCli.setText(null);

                }
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    //Método pesquisar clientes pelo nome com filtro
    private void pesquisar_cliente() {
        String sql = "select * from tbclientes where nomeCli like ?";
        try {
            pst = conexao.prepareStatement(sql);

            //Passando o conteúdo da caixa pesquisa para o ?
            //atenção ao '%' continuação da string sql
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();

            //Utilizar biblioteca  rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }

    //Método para setar os campos do formulário com o conteúdo da tabela
    public void setar_campos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtNomeCli.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCpfCli.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtEndClli.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtBairroCli.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtCidadeCli.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
        txtFoneCli.setText(tblClientes.getModel().getValueAt(setar, 6).toString());
        txtEmailCli.setText(tblClientes.getModel().getValueAt(setar, 7).toString());
        
        //Desabilita o botão adicionar
        btnAdicionar.setEnabled(false);
    }

    //Criando o médoto para alterar os dados do cliente
    private void alterar() {
        String sql = "update tbclientes set nomecli=?, cpfcli=?, endclli=?, bairrocli=?, cidadecli=?, fonecli=?, emailcli=? where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCli.getText());
            pst.setString(2, txtCpfCli.getText());
            pst.setString(3, txtEndClli.getText());
            pst.setString(4, txtBairroCli.getText());
            pst.setString(5, txtCidadeCli.getText());
            pst.setString(6, txtFoneCli.getText());
            pst.setString(7, txtEmailCli.getText());
            pst.setString(8, txtCliId.getText());
            
            //validação dos campos obrigatórios
            if ((txtNomeCli.getText().isEmpty()) || (txtCpfCli.getText().isEmpty()) || (txtFoneCli.getText().isEmpty()) || (txtEndClli.getText().isEmpty()) || (txtBairroCli.getText().isEmpty()) || (txtCidadeCli.getText().isEmpty()) || (txtEmailCli.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {

                int adicionado = pst.executeUpdate();
                //Confirma a Atualização da tabela com os dados do formulário
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterado com sucesso!");

                    //Limpar os campos
                    txtNomeCli.setText(null);
                    txtCpfCli.setText(null);
                    txtEndClli.setText(null);
                    txtBairroCli.setText(null);
                    txtCidadeCli.setText(null);
                    txtFoneCli.setText(null);
                    txtEmailCli.setText(null);
                    
                    //habilita o botão Adicionar
                    btnAdicionar.setEnabled(true);
                }
            }
        } catch (Exception error) {
            JOptionPane.showMessageDialog(null, error);
        }
    }
    
    private void deletar(){
        
        //Estrutura confirma a remoção do Clientes
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este cliente", "Atenção", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_NO_OPTION){
            String sql="delete from tbclientes where idcli=?";
            try {
                pst=conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());
                //variável para que possa ser utilizado na condição postivia.
                int apagado = pst.executeUpdate();
                //Condição para exibit mensagem ao usuário
                if (apagado>0){
                    JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                    //Limpar os campos
                    txtNomeCli.setText(null);
                    txtCpfCli.setText(null);
                    txtEndClli.setText(null);
                    txtBairroCli.setText(null);
                    txtCidadeCli.setText(null);
                    txtFoneCli.setText(null);
                    txtEmailCli.setText(null);
                    
                    //habilita o botão Adicionar
                    btnAdicionar.setEnabled(true);
                    
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, error);
            }
        } 
    }
    

    /**
     * Esse método é chamado de dentro do construtor para inicializar o
     * formulário. AVISO: NÃO modifique este código. O conteúdo deste método é
     * sempre regenerado pelo Editor de formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNomeCli = new javax.swing.JTextField();
        txtEndClli = new javax.swing.JTextField();
        txtEmailCli = new javax.swing.JTextField();
        txtFoneCli = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtBairroCli = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCidadeCli = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCpfCli = new javax.swing.JTextField();
        txtCliPesquisar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(635, 465));

        jLabel1.setText("* Campos Obrigatórios");

        jLabel2.setText("* Nome:");

        jLabel3.setText("* Endereço:");

        jLabel4.setText("Telefone:");

        jLabel5.setText("E-mail:");

        jLabel9.setText("* Bairro:");

        jLabel10.setText("* Cidade:");

        jLabel11.setText("* CPF:");

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/systemdevbox/icones/file_new.png"))); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/systemdevbox/icones/edit_file.png"))); // NOI18N
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/systemdevbox/icones/delete_file.png"))); // NOI18N
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        jLabel6.setText("ID:");

        txtCliId.setEnabled(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/systemdevbox/icones/Search.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEndClli, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtCpfCli, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFoneCli))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBairroCli, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCidadeCli, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmailCli)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdicionar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAlterar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnDeletar))))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel7)
                            .addGap(21, 21, 21)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNomeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCpfCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtFoneCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtEndClli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtBairroCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCidadeCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmailCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAlterar)
                    .addComponent(btnAdicionar)
                    .addComponent(btnDeletar))
                .addGap(58, 58, 58))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // chamando o método adicionar clientes
        adicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtCliPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisarKeyPressed

    //este evento é do tipo "enquanto for digitando, execute o comando abaixo"
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Chamando método pesquisar cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    //Evento usado para setar os campos da tabela clicando com o botão direito do mouse
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Chamando o método setar campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        // chamando o método alterar
        alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // chamando o método deletar
        deletar();
    }//GEN-LAST:event_btnDeletarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBairroCli;
    private javax.swing.JTextField txtCidadeCli;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCpfCli;
    private javax.swing.JTextField txtEmailCli;
    private javax.swing.JTextField txtEndClli;
    private javax.swing.JTextField txtFoneCli;
    private javax.swing.JTextField txtNomeCli;
    // End of variables declaration//GEN-END:variables
}
