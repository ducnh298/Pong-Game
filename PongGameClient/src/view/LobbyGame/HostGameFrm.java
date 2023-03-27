/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.LobbyGame;

import control.ClientCtr;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.ObjectWrapper;
import model.Player;
import view.ClientMainFrm;
import view.Game.GameFrm;

/**
 *
 * @author nguye
 */
public class HostGameFrm extends javax.swing.JFrame {
ClientCtr myControl;
Player hostPlayer;
Player player;
int playerId;
ArrayList<Integer> hostGame=new ArrayList<>();
ClientMainFrm cmf;
    /**
     * Creates new form HostGame
     */
    public HostGameFrm(ClientCtr myControl,ClientMainFrm cmf,int playerId, Player hostPlayer, Player player) {
        super("Lobby");
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        centreWindow(this);
        this.hostPlayer=hostPlayer;
        this.myControl=myControl;
        this.player=player;
        this.playerId=playerId;
        this.cmf=cmf;
        init();
        hostGame.add(hostPlayer.getId());
        if(player!=null)
            hostGame.add(player.getId());
        else hostGame.add(0);
        hostGame.add(0);
        hostGame.add(0);
        initButton(hostGame);
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_HOST_GAME, this));
        myControl.sendData(new ObjectWrapper(ObjectWrapper.CLIENT_INFORM_INGAME_PLAYER, playerId));
    }
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
}
    public void receivedData(ObjectWrapper data) {
        if(data.getPerformative()==ObjectWrapper.REPLY_HOST_GAME){
            if(data.getData() instanceof ArrayList<?>){
                hostGame=(ArrayList<Integer>) data.getData();
                initButton(hostGame);
                if(hostGame.get(2)==1&&hostGame.get(3)==1){
                    //JOptionPane.showMessageDialog(this, "All ready! Entering game.....");
                    PlayGameFrm gf= new PlayGameFrm(cmf, myControl, playerId, hostPlayer.getId(), player.getId());
                    gf.setVisible(true);

                    for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof HostGameFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
                    this.dispose();
                }
            }
        }
    }
    public void init(){
        jLabel1.setText(hostPlayer.getNickName());
        jLabel2.setText("ID: "+hostPlayer.getId()+"");
        if(player!=null){
            jLabel3.setText(player.getNickName());
            jLabel4.setText("ID: "+player.getId()+"");
            jButton3.setVisible(false);
        }
        else {
            jLabel3.setText("");
            jLabel4.setText("");
            jButton3.setVisible(true);
        }
        
        if(playerId==hostPlayer.getId()){
            jButton2.setEnabled(false);
            jButton1.setEnabled(true);
            jButton3.setVisible(true);
        }
        else{
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
            jButton3.setVisible(false);
        }
    }
    public void initButton(ArrayList<Integer> hostGame){
    if(hostGame.get(0)!=0){    
        if(hostGame.get(2)==1){
            jLabel6.setText("READY");
            jButton1.setText("Unready");
        }
        else if(hostGame.get(2)==0){
            jLabel6.setText("");
            jButton1.setText("Ready");
        }
        if(hostGame.get(1)!=0){
            jButton3.setVisible(false);
            if(hostGame.get(3)==1){
                jLabel7.setText("READY");
                jButton2.setText("Unready");
            }
            else if(hostGame.get(3)==0){
                jLabel7.setText("");
                jButton2.setText("Ready");
            }
        }
        else{
            this.player=null;
            jLabel7.setText("");
            jButton2.setText("Ready");
            init(); 
        }
    }
    else{
        JOptionPane.showMessageDialog(this, "Host player left\nRoom closed!");
        myControl.sendData(new ObjectWrapper(ObjectWrapper.CLIENT_INFORM_OFFGAME,playerId));
        for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof HostGameFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
        this.dispose();
    }
    }
//    public void checkReady(){
//      if(hostPlayer.getId()==playerId)
//        if(jLabel6.getText().equals("Ready")&&jLabel7.getText().equals("Ready")){
//            ArrayList<Player> enterGame=new ArrayList<>();
//            enterGame.add(hostPlayer);
//            enterGame.add(player);
//            System.out.println("Entering game...");
//            myControl.sendData(new ObjectWrapper(ObjectWrapper.ENTER_GAME, enterGame));
//        }
//    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("jLabel1");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("jLabel3");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("jLabel4");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 204));
        jLabel5.setText("VS");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("Ready");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Ready");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 102));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 102));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Invite");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("Host player");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jButton3))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    if(jButton1.getText().equals("Ready")){   
        hostGame.set(2, 1);
    }
    else{
        hostGame.set(2, 0);
    }
        myControl.sendData(new ObjectWrapper(ObjectWrapper.HOST_GAME, hostGame));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    if(jButton2.getText().equals("Ready")){   
        hostGame.set(3, 1);
    }
    else{
        hostGame.set(3, 0);
    }
         myControl.sendData(new ObjectWrapper(ObjectWrapper.HOST_GAME, hostGame));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        PlayerListFrm plf = new PlayerListFrm(myControl, cmf);
        plf.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        int h = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","Exit?", JOptionPane.YES_NO_OPTION);
            if(h==JOptionPane.NO_OPTION){
                setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                return;
            }
            else if (h == JOptionPane.YES_OPTION) {
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            if(playerId==hostPlayer.getId()){
                hostGame.set(0, 0);  
                hostGame.set(2, 0);
                myControl.sendData(new ObjectWrapper(ObjectWrapper.HOST_GAME, hostGame));  
                }
            else if(player.getId()==playerId){
                    hostGame.set(1, 0);
                    hostGame.set(2, 0);
                    hostGame.set(3, 0);
                    this.player=null;
                    myControl.sendData(new ObjectWrapper(ObjectWrapper.HOST_GAME, hostGame));  
                }   
               myControl.sendData(new ObjectWrapper(ObjectWrapper.CLIENT_INFORM_OFFGAME,playerId));
               
               for(int i=0;i<myControl.getActiveFunction().size();i++){
                    if(myControl.getActiveFunction().get(i).getData() instanceof HostGameFrm) {
                        myControl.getActiveFunction().remove(i);
                    }
            }
        this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables

    
}
