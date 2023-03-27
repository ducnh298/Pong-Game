/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.clan;

import control.ClientCtr;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Clan;
import model.ObjectWrapper;
import model.Player;
import view.InfoPlayerFrm;
/**
 *
 * @author nguye
 */
public class InfoClanFrm extends javax.swing.JFrame {
ClientCtr myControl;
Clan clan;
Player player= new Player();
ArrayList<Player> playerList=new ArrayList<>();
ArrayList<Player> friendList= new ArrayList<>();
ArrayList<Player> clanMember=new ArrayList<>();
Player leader=new Player();
ClanFrm cf;
    /**
     * Creates new form InfoClanFrm
     */
    public InfoClanFrm(ClientCtr mySocket,ClanFrm cf,Clan clan,Player player,ArrayList<Player> playerList,ArrayList<Player> friendList) {
        super("Info Clan");
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.clan=clan;
        this.player = player;
        this.myControl=mySocket;
        this.cf=cf;
        this.playerList=playerList;
        this.friendList=friendList;
        updateClanMember();
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_CLAN, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_JOIN_CLAN, this));
    }
    public void receivedData(ObjectWrapper data){
        if(data.getPerformative()==ObjectWrapper.REPLY_JOIN_CLAN){
            if(data.getData().equals("ok")){
                JOptionPane.showMessageDialog(this, "Join clan successfully!");
                player.setInClan(clan); 
                clanMember.add(player);
                fillTable(clanMember);
                initClan();
                playerList=cf.updatePlayerList();
            }
            else
                JOptionPane.showMessageDialog(this, "Failed to join clan!");
        }
        if(data.getPerformative()==ObjectWrapper.REPLY_REQUEST_JOIN_CLAN){
            int result = (int) data.getData();
            if(result==1){
                JOptionPane.showMessageDialog(this, "You've already request to join this clan.");
                jButton1.setEnabled(false);
            }
            else if(result==2){
                JOptionPane.showMessageDialog(this, "join request sent!");
            }
            else 
                JOptionPane.showMessageDialog(this, "Failed to send join request!");
        }     
    }
    public void initClan(){
        jLabel1.setText("Clan: "+clan.getName());
        jLabel2.setText("Member: "+clanMember.size()+"/"+clan.getMaxMember());
        jLabel3.setText("Leader: "+leader.getNickName());
        if(clan.getType()==1){
                jLabel5.setText("Type: Private");
                jButton1.setText("Request to join");
        }
        else{
                jLabel5.setText("Type: Public");
                jButton1.setText("Join");
        }
        jTextArea1.setText(clan.getDes());
        if(player.getInClan()==null&&clanMember.size()<clan.getMaxMember())
            jButton1.setVisible(true);
        else
            jButton1.setVisible(false);
    }
    public void updateClanMember(){
        clanMember.clear();
        for(int i=0;i<playerList.size();i++){
          if(playerList.get(i).getInClan()!=null){
            if(playerList.get(i).getInClan().getId()==clan.getId()){
                clanMember.add(playerList.get(i));
            }
          }
            if(playerList.get(i).getId()==clan.getLeaderId().getId())
                this.leader=playerList.get(i);
        }
//        System.out.println("clan member: "+clanMember.size() );
        initClan();
        if(clanMember.size()>0)
            fillTable(clanMember);
    }
     public void initTable(int size){
         
        String[] columnNames = {" ", "NickName"," " };
       DefaultTableModel tableModel = new DefaultTableModel(columnNames, size) {
             @Override
          public boolean isCellEditable(int row, int column) {
            //all cells false
                return false;
            }
    };
            jTable1.setModel(tableModel); 
        
    }
     public void fillTable(ArrayList<Player> clanMember){
         initTable(clanMember.size());
         for(int i=0;i<clanMember.size();i++){
             jTable1.setValueAt(i+1+"", i, 0);
             jTable1.setValueAt(clanMember.get(i).getNickName(), i, 1);
             if(clanMember.get(i).getId()==leader.getId())
                jTable1.setValueAt("Leader", i, 2);
             else 
                 jTable1.setValueAt("Member", i, 2);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Clan: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Max Member: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Leader: ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Description");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Join");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Type: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(23, 23, 23)
                        .addComponent(jLabel4))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int column = jTable1.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
                int row = evt.getY() / jTable1.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {

                    for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof InfoPlayerFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
                    (new InfoPlayerFrm(myControl, (Player)clanMember.get(row),player,friendList,false,null)).setVisible(true);
                    
                }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:   
    if(clanMember.size()<clan.getMaxMember()){
        int c = JOptionPane.showConfirmDialog(this, "Are you sure want to join this clan", "Joining clan", JOptionPane.YES_NO_OPTION);
        if(c==JOptionPane.YES_OPTION){
            if(clan.getType()==0){
                player.setInClan(clan);
                myControl.sendData(new ObjectWrapper(ObjectWrapper.JOIN_CLAN,player));
            }
            else if(clan.getType()==1){
                Player joinPlayer =player;
                joinPlayer.setInClan(clan);  
                myControl.sendData(new ObjectWrapper(ObjectWrapper.REQUEST_JOIN_CLAN,joinPlayer));
            }
        }
    }
    else
        JOptionPane.showMessageDialog(this, "Max member reached!");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        playerList=cf.updatePlayerList();
        updateClanMember();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof InfoPlayerFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
        this.dispose();
    }//GEN-LAST:event_formWindowClosed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
