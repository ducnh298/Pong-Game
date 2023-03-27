/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.LobbyGame;

import control.ClientCtr;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Message;
import model.ObjectWrapper;
import model.Player;
import view.ClientMainFrm;

/**
 *
 * @author nguye
 */
public class PlayerListFrm extends javax.swing.JFrame {
ClientCtr myControl;
ClientMainFrm cmf;
ArrayList<Player> playerList;
Player mainPlayer;
    /**
     * Creates new form PlayerListFrm
     */
    public PlayerListFrm(ClientCtr myControl,ClientMainFrm cmf) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.myControl=myControl;
        this.cmf=cmf;
       
        cmf.updatePlayerList();
        this.playerList=cmf.getPlayerList();
         this.mainPlayer=cmf.getPlayer();
         fillTable(playerList);
    }
    public void initTable(int size){
       String[] columnNames = {"", "NickName","Match Won","Match Played", "Status"};
       DefaultTableModel tableModel = new DefaultTableModel(columnNames, size) {
    @Override
    public boolean isCellEditable(int row, int column) {
       //all cells false
       return false;
            }
        };
            jTable1.setModel(tableModel); 
    }
    
    public void fillTable(ArrayList<Player> playerList) { 
        initTable(this.playerList.size());        

            for(int i=0; i<playerList.size(); i++){
                jTable1.setValueAt(i+1+"", i, 0);
                jTable1.setValueAt(playerList.get(i).getNickName(), i, 1); 
                jTable1.setValueAt(playerList.get(i).getMatchWon(), i, 2); 
                jTable1.setValueAt(playerList.get(i).getMatchPlayed(), i, 3);
                if(!playerList.get(i).getStatus().equals("offline"))
                    jTable1.setValueAt(playerList.get(i).getStatus(), i, 4);
              }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(jButton1)
                .addContainerGap(181, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cmf.updatePlayerList();
        this.playerList=cmf.getPlayerList();
        fillTable(playerList);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int column = jTable1.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / jTable1.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
        if (playerList.get(row).getStatus()=="Online"&&row<playerList.size() &&row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0){
            ArrayList<Integer> challenge = new ArrayList<>();
                challenge.add(mainPlayer.getId());
                challenge.add(playerList.get(row).getId());
                myControl.sendData(new ObjectWrapper(ObjectWrapper.CHALLENGE, challenge));
                JOptionPane.showMessageDialog(this, "Invite sent!\nPlease wait a moment for response!");
                Message ms = new Message(mainPlayer.getId(), playerList.get(row).getId(), "invite");
                myControl.sendData(new ObjectWrapper(ObjectWrapper.MESSAGE_PLAYER, ms));
                this.dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof PlayerListFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
