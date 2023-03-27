/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.clan;

import control.ClientCtr;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
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
public class ClanManagement extends javax.swing.JFrame {
ClientCtr myControl;
Clan clan;
ArrayList<Player> clanMember;
Player leader;
Player kickedMember;
int row1;
int row2;
ClanFrm cf;
ArrayList<Player> joinRequestList=new ArrayList<>();
ArrayList<Player> playerList;

    public ClanManagement(ClientCtr mySocket,ClanFrm cf,Clan clan,Player player, ArrayList<Player> clanMember, ArrayList<Player> playerList) {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        this.setAlwaysOnTop(true);
        positionWindow(this);
        this.myControl=mySocket;
        this.clan=clan;
        this.clanMember=clanMember;
        this.leader=player;
        this.cf=cf;
        this.playerList=playerList;
        initClan();
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_CLAN, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_KICK_MEMBER, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_REQUEST_LIST,this));   
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_RESPONSE_REQUEST_JOIN_CLAN,this));   
        myControl.sendData(new ObjectWrapper(ObjectWrapper.JOIN_REQUEST_LIST,clan.getId())); 
        fillTable1(clanMember);
    }
    public static void positionWindow(Window frame) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - frame.getWidth())*8 / 9);
    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
    frame.setLocation(x, y);
}
    public void receivedData(ObjectWrapper data){
        if(data.getPerformative()==ObjectWrapper.REPLY_JOIN_REQUEST_LIST){
             if(data.getData() instanceof ArrayList<?>){
                 ArrayList<Integer> list = (ArrayList<Integer>) data.getData();
                 System.out.println("Join request:"+list);
            if(list.size()>0){
                joinRequestList.clear();
                for(int i=0;i<list.size();i++){
                    for(int j=0;j<playerList.size();j++){
                        if(list.get(i)==playerList.get(j).getId()){
                            joinRequestList.add(playerList.get(j));
                            break;
                        }
                    }
                }
                System.out.println("Join request size: "+joinRequestList.size());
                fillTable2(joinRequestList);
            }
             }
             else   JOptionPane.showMessageDialog(this, "Failed to load join request from server!");
         }
         if(data.getPerformative()==ObjectWrapper.REPLY_EDIT_CLAN){
            if(data.getData().equals("ok")){
                JOptionPane.showMessageDialog(this, "Update clan successfully!");
                cf.updateMyClan();
                cf.updateClanList();
            }
            else
                JOptionPane.showMessageDialog(this, "Failed to update clan!");
         }
         if(data.getPerformative()==ObjectWrapper.REPLY_KICK_MEMBER){
            if(data.getData().equals("ok")){
                JOptionPane.showMessageDialog(this, "Kick "+kickedMember.getNickName()+" successfully!");
                clanMember.remove(row1);
                fillTable1(clanMember);
                cf.updatePlayerList();
                cf.updateMyClan();
                }
                else
                    JOptionPane.showMessageDialog(this, "Failed to kick "+kickedMember.getNickName());
        }
         
         if(data.getPerformative()==ObjectWrapper.REPLY_RESPONSE_REQUEST_JOIN_CLAN){
             if(data.getData().equals("ok")){
                JOptionPane.showMessageDialog(this, "This player is your clan's member now!");
                clanMember.add(joinRequestList.get(row2));
                 fillTable1(clanMember);
                joinRequestList.remove(row2);
                 fillTable2(joinRequestList);
                 cf.updatePlayerList();
                 cf.updateMyClan();
            }
             else if(data.getData()=="false")
                JOptionPane.showMessageDialog(this, "Failed to update clan from server!");
         }
     }
    public void initClan(){
        jTextField1.setText(clan.getName());
        jTextField2.setText(clan.getMaxMember()+"");
        jTextArea1.setText(clan.getDes()); 
        if(clan.getType()==1)
            jRadioButton2.setSelected(true);
        else
            jRadioButton1.setSelected(true);
    }
    public void initTable1(int size){
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
     public void fillTable1(ArrayList<Player> clanMember){
         
         initTable1(clanMember.size());
         for(int i=0;i<clanMember.size();i++){
             jTable1.setValueAt(i+1+"", i, 0);
             jTable1.setValueAt(clanMember.get(i).getNickName(), i, 1);
             if(clanMember.get(i).getId()==leader.getId())
                jTable1.setValueAt("Leader", i, 2);
             else 
                 jTable1.setValueAt("Member", i, 2);
         }
     }
     public void initTable2(int size){
        String[] columnNames = {" ", "NickName"," " };
       DefaultTableModel tableModel = new DefaultTableModel(columnNames, size) {
             @Override
          public boolean isCellEditable(int row, int column) {
            //all cells false
                return false;
            }
    };
            jTable2.setModel(tableModel); 
        
    }
     public void fillTable2(ArrayList<Player> joinRequestList){        
         initTable2(joinRequestList.size());
         for(int i=0;i<joinRequestList.size();i++){
             jTable2.setValueAt(i+1+"", i, 0);
             jTable2.setValueAt(joinRequestList.get(i).getNickName(), i, 1);
             jTable2.setValueAt("Join Request", i, 2);
             
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Number of member");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Description");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Update");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
        jScrollPane2.setViewportView(jTable1);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Kick member");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Type");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Public ");
        jRadioButton1.setEnabled(false);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jRadioButton2.setText("Private");
        jRadioButton2.setEnabled(false);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Edit Clan");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("Join request");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)
                                        .addComponent(jRadioButton1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                        .addComponent(jTextField2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jRadioButton2)
                                        .addGap(40, 40, 40))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(jButton3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(147, 147, 147)
                        .addComponent(jButton2)
                        .addGap(81, 81, 81))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Clan newClan = new Clan();
        newClan.setId(clan.getId());
        newClan.setName(jTextField1.getText());
        newClan.setMaxMember(Integer.parseInt(jTextField2.getText()));
        if(jRadioButton1.isSelected())
            newClan.setType(0);
        else if(jRadioButton2.isSelected())
            newClan.setType(1);
        
        newClan.setDes(jTextArea1.getText());
        
        myControl.sendData(new ObjectWrapper(ObjectWrapper.EDIT_CLAN,newClan));
        this.clan=newClan;
        
        jTextField1.setEditable(false);
        jTextField2.setEditable(false);
        jTextArea1.setEditable(false);
        jRadioButton1.setEnabled(false);
        jRadioButton2.setEnabled(false);
        jButton3.setEnabled(true);
        jButton1.setEnabled(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        fillTable1(clanMember);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int column = jTable1.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
                row1 = evt.getY() / jTable1.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row1 < jTable1.getRowCount() && row1 >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                    //search and delete all existing previous view
                if(leader.getId()!=clanMember.get(row1).getId()){    
                    int c = JOptionPane.showConfirmDialog(this, "Are you sure you want to kick "+clanMember.get(row1).getNickName()+"?", "Kick member", JOptionPane.YES_NO_OPTION);
                    if(c==JOptionPane.YES_OPTION){
                        kickedMember = clanMember.get(row1);
                        kickedMember.setInClan(null);
                        myControl.sendData(new ObjectWrapper(ObjectWrapper.KICK_MEMBER,kickedMember));
                        }
                    }
                else
                    JOptionPane.showMessageDialog(this, "You cannot kick yourself!");
                }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        jTextField1.setEditable(true);
        jTextField2.setEditable(true);
        jTextArea1.setEditable(true);
        jRadioButton1.setEnabled(true);
        jRadioButton2.setEnabled(true);
        jButton3.setEnabled(false);
        jButton1.setEnabled(true);
        jTextField1.requestFocus();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int column = jTable2.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
                 row2 = evt.getY() / jTable2.getRowHeight(); // get the row of the button
                // *Checking the row or column is valid or not
                if (row2<joinRequestList.size() &&row2 < jTable2.getRowCount() && row2 >= 0 && column < jTable2.getColumnCount() && column >= 0) {
                     String message = "Clan join request from "+joinRequestList.get(row2).getNickName();
                    int option = JOptionPane.showConfirmDialog(this,message, null, JOptionPane.YES_NO_CANCEL_OPTION);
                    if(option==JOptionPane.YES_OPTION){
                        ArrayList<Integer> response = new ArrayList<>();
                        response.add(joinRequestList.get(row2).getId());
                        response.add(clan.getId());
                        response.add(1);
                        myControl.sendData(new ObjectWrapper(ObjectWrapper.RESPONSE_REQUEST_JOIN_CLAN, response));   
                    }
                    else if(option==JOptionPane.NO_OPTION){
                        ArrayList<Integer> response = new ArrayList<>();
                        response.add(joinRequestList.get(row2).getId());
                        response.add(clan.getId());
                        response.add(0);
                        myControl.sendData(new ObjectWrapper(ObjectWrapper.RESPONSE_REQUEST_JOIN_CLAN, response));   
                        
                    }
                }
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        myControl.sendData(new ObjectWrapper(ObjectWrapper.JOIN_REQUEST_LIST,clan.getId()));   
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
