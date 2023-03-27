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
import view.ClientMainFrm;
import view.InfoPlayerFrm;

/**
 *
 * @author nguye
 */
public class ClanFrm extends javax.swing.JFrame {
Player player=new Player();
ClientCtr myControl;
ClientMainFrm cmf;
ArrayList<Clan> clanList = new ArrayList<>();
ArrayList<Clan> searchClanList;
ArrayList<Player> friendList;
ArrayList<Player> playerList;
ArrayList<Player> clanMember=new ArrayList<>();
Clan myClan=new Clan();
Player leader=new Player();
int search=0;
    /**
     * Creates new form ClanFrm
     */
    public ClanFrm(ClientCtr mySocket,ClientMainFrm cmf,Player player,ArrayList<Player> playerList,ArrayList<Player> friendList ) {
        super("Clan");
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        positionWindow(this);
        this.player = player;
        this.myControl=mySocket;
        this.cmf=cmf;
        this.playerList=playerList;
        this.friendList=friendList;
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CLAN_LIST, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_CLAN, this));
        
        updateClanList();
        initButton();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }
    
    void initButton(){
        if(player.getInClan()!=null){
            jButton2.setVisible(false);
            jButton4.setVisible(false);
            jButton5.setEnabled(true);
            jButton6.setEnabled(true);   
        }
        else{
            jButton2.setVisible(true);
            jButton4.setVisible(false);
           // jButton5.setEnabled(false);
            jButton6.setEnabled(false);   
        }
        if(leader.getId()==player.getId())
            jButton4.setVisible(true);
    }
public static void positionWindow(Window frame) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 8);
    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
    frame.setLocation(x, y);
}
    public void updateClanList(){
        myControl.sendData(new ObjectWrapper(ObjectWrapper.CLAN_LIST,""));     
    }
     public void receivedData(ObjectWrapper data){
        if(data.getPerformative()==ObjectWrapper.REPLY_CLAN_LIST){
            if(data.getData() instanceof ArrayList<?>){
            clanList = (ArrayList<Clan>) data.getData(); 
            fillTable(clanList);
            }else
                JOptionPane.showMessageDialog(this, "Failed to load clan from server!"); 
        }
        if(data.getPerformative()==ObjectWrapper.REPLY_LEAVE_CLAN){
                if(data.getData().equals("ok")){
                    JOptionPane.showMessageDialog(this, "Leave Clan successfully!");
                    clanMember.clear();
                    this.myClan=new Clan();
                    this.leader= new Player();
                    cmf.updatePlayerList();
                    this.player=cmf.getPlayer();
                    initButton();
                    fillTable2(clanMember);
                }
                else
                    JOptionPane.showMessageDialog(this, "Failed to leave Clan!");
            }
     }
    public void initTable(int size){
        String[] columnNames = {"ID", "Name", "Number of Member","Type", "Desription"};
       DefaultTableModel tableModel = new DefaultTableModel(columnNames, size) {
             @Override
          public boolean isCellEditable(int row, int column) {
            //all cells false
                return false;
            }
    };
            jTable1.setModel(tableModel); 
        
    }
    public void fillTable(ArrayList<Clan> clanList){
        initTable(clanList.size());
        for(int i=0;i<clanList.size();i++){
            jTable1.setValueAt(clanList.get(i).getId(), i, 0);
            jTable1.setValueAt(clanList.get(i).getName(), i, 1);
            jTable1.setValueAt(clanList.get(i).getMaxMember(), i, 2);
            if(clanList.get(i).getType()==1)
                jTable1.setValueAt("Private", i, 3);
            else jTable1.setValueAt("Public", i, 3);
            jTable1.setValueAt(clanList.get(i).getDes(), i, 4);
        }
    }
    public ArrayList<Player> updatePlayerList(){
        cmf.updatePlayerList();
        playerList=cmf.getPlayerList();
        player=cmf.getPlayer();
        initButton();
        return playerList;
    }
    public void refresh1(){
        updatePlayerList();
        updateClanList();
        jTextField1.setText("");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
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

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Create new clan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jButton1)
                        .addGap(39, 39, 39)
                        .addComponent(jButton2)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clan", jPanel1);

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
        jScrollPane2.setViewportView(jTable2);

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
        jScrollPane3.setViewportView(jTextArea1);

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("Clan Management");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setText("Refresh");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setText("Leave");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Type: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5)
                                .addGap(31, 31, 31)
                                .addComponent(jButton6))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jButton4)
                                .addGap(15, 15, 15)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(14, 14, 14)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(jButton6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("MyClan", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CreateClanFrm cncf = new CreateClanFrm(myControl,this,player);
        cncf.setVisible(true);
   
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        myControl.sendData(new ObjectWrapper(ObjectWrapper.CLAN_LIST,jTextField1.getText()));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int column = jTable1.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
                int row = evt.getY() / jTable1.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
                if (row<clanList.size()&&row < jTable1.getRowCount() && row >= 0 && column < jTable1.getColumnCount() && column >= 0) {
                    for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof InfoClanFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
                    (new InfoClanFrm(myControl,this, clanList.get(row),player,playerList,friendList)).setVisible(true);
                }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        refresh1();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
            updateMyClan();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        for(ObjectWrapper func: myControl.getActiveFunction())
                    if(func.getData() instanceof ClanManagement) {
                        ((ClanManagement)func.getData()).setVisible(true);
                        return;
                    }
        ClanManagement edf =new ClanManagement(myControl,this, myClan,player,clanMember,playerList);
        edf.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
        updateMyClan();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int c=JOptionPane.showConfirmDialog(this, "Are you sure you want to leave this clan?","Leave clan" , JOptionPane.YES_NO_OPTION);
        if(c==JOptionPane.YES_OPTION){
            Player outPlayer = player;
            outPlayer.setInClan(null);
            myControl.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_CLAN,outPlayer));
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int column = jTable2.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / jTable2.getRowHeight(); // get the row of the button
 
                // *Checking the row or column is valid or not
        if (row<clanMember.size() &&row < jTable2.getRowCount() && row >= 0 && column < jTable2.getColumnCount() && column >= 0) {
            for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof InfoPlayerFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
            (new InfoPlayerFrm(myControl, (Player)clanMember.get(row),player,friendList,false,null)).setVisible(true);
            }
    }//GEN-LAST:event_jTable2MouseClicked
    public void updateMyClan(){
      if(player.getInClan()!=null){
        playerList=updatePlayerList();
        updateClanMember();
        initClan();
        fillTable2(clanMember);
        if(player.getId()==leader.getId()){ 
            jButton4.setVisible(true);
            jButton6.setEnabled(false);
        }
        else
            jButton4.setVisible(false); 
        }
    }
    public void updateClanMember(){
      if(player.getInClan()!=null){
        for(int i=0;i<clanList.size();i++){
            if(player.getInClan().getId()==clanList.get(i).getId())
                this.myClan=clanList.get(i);
        }
        clanMember.clear();
        for(int i =0;i<playerList.size();i++){
            if(playerList.get(i).getInClan()!=null){
                if(playerList.get(i).getInClan().getId()==myClan.getId())
                    clanMember.add(playerList.get(i));
            }
            if(myClan.getLeaderId()!=null){
            if(playerList.get(i).getId()==myClan.getLeaderId().getId()){
                this.leader=playerList.get(i);
                //jButton6.setEnabled(false);
            }
        }
        }
        }
    }
    public void initClan(){
        if(myClan.getId()!=0){
            jLabel1.setText("Clan: "+myClan.getName());
            jLabel2.setText("Member: "+clanMember.size()+"/"+myClan.getMaxMember());
            jLabel3.setText("Leader: "+leader.getNickName());
            if(myClan.getType()==1)
                jLabel5.setText("Type: Private");
            else
                jLabel5.setText("Type: Public");
            jTextArea1.setText(myClan.getDes());
        }
        else{
            jLabel1.setText("Clan: ");
            jLabel2.setText("Member: ");
            jLabel3.setText("Leader: ");
            jLabel5.setText("Type: ");
            jTextArea1.setText(" ");
        }
    }
    public void initTable2(int size){
         initClan();
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
     public void fillTable2(ArrayList<Player> clanMember){
       initTable2(clanMember.size());
       if(clanMember.size()>0){
         for(int i=0;i<clanMember.size();i++){
             jTable2.setValueAt(i+1+"", i, 0);
             jTable2.setValueAt(clanMember.get(i).getNickName(), i, 1);
             if(clanMember.get(i).getId()==leader.getId())
                jTable2.setValueAt("Leader", i, 2);
             else 
                 jTable2.setValueAt("Member", i, 2);
         }
         }
     }
     
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
