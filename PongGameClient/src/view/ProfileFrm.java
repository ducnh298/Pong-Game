/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ClientCtr;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Player;
import model.ObjectWrapper;
import view.login.ChangePasswordFrm;

/**
 *
 * @author nguye
 */
public class ProfileFrm extends javax.swing.JFrame {
private Player player = new Player();
ClientCtr myControl;
ClientMainFrm cmf;
String newName;
    /**
     * Creates new form ProfileFrm
     */
    public ProfileFrm(ClientMainFrm cmf, ClientCtr socket,Player player) {
        super("Profile Player");
        this.player=player;
        this.cmf=cmf;
        myControl = socket;
        initComponents();
        positionWindow(this);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initPlayerInfo(player);
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_PLAYER_NAME, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_EDIT_PLAYER, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_PLAYER, this));
        myControl.sendData(new ObjectWrapper(ObjectWrapper.GET_PLAYER,player.getId()));
    }
 public static void positionWindow(Window frame) {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 8);
    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
    frame.setLocation(x, y);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("NickName");

        jTextField1.setEditable(false);

        jLabel1.setText("ID player");

        jTextField2.setEditable(false);
        jTextField2.setText("jTextField2");

        jLabel3.setText("Match Won ");

        jLabel4.setText("Match played");

        jTextField3.setEditable(false);
        jTextField3.setText("jTextField3");

        jTextField4.setEditable(false);
        jTextField4.setText("jTextField4");

        jButton1.setText("Change Nick name");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Win rate");

        jTextField5.setEditable(false);
        jTextField5.setText("0%");

        jLabel6.setText("Email");

        jTextField6.setEditable(false);

        jButton2.setText("Change email");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Change Password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                            .addComponent(jButton3)))
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void receivedData(ObjectWrapper data) {
        if(data.getPerformative()==ObjectWrapper.REPLY_PLAYER){
            if(data.getData() instanceof Player){
                this.player = (Player) data.getData();
                initPlayerInfo(player);
            }
        }
        if(data.getPerformative()==ObjectWrapper.REPLY_CHECK_PLAYER_NAME){
            if(data.getData().equals("false"))
                JOptionPane.showMessageDialog(this, "This nick name has been used!");
            else if(data.getData().equals("ok")) {   
                    Player newPlayer = this.player;
                    newPlayer.setNickName(newName);
                    myControl.sendData(new ObjectWrapper(ObjectWrapper.EDIT_PLAYER,newPlayer));
                } 
        }
        if(data.getPerformative()==ObjectWrapper.REPLY_EDIT_PLAYER){
                    if(data.getData().equals("ok"))
                {        
                    JOptionPane.showMessageDialog(this, "Edit successfully!");
                    cmf.updatePlayerList();
                    myControl.sendData(new ObjectWrapper(ObjectWrapper.GET_PLAYER,player.getId()));
            }
            else{
                JOptionPane.showMessageDialog(this, "Failed to edit player!");    
            }
                }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       
     newName = JOptionPane.showInputDialog(this, "Enter new nick name: ", "Change nick name", JOptionPane.INFORMATION_MESSAGE);
        if(newName instanceof String&&newName!=null){
            myControl.sendData(new ObjectWrapper(ObjectWrapper.CHECK_PLAYER_NAME,newName));  
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String newEmail = JOptionPane.showInputDialog(this, "Enter new email: ", "Change email", JOptionPane.INFORMATION_MESSAGE);
            
            if(newEmail instanceof String&&newEmail!=null){
                Player newPlayer = this.player;
                newPlayer.setEmail(newEmail);
                myControl.sendData(new ObjectWrapper(ObjectWrapper.EDIT_PLAYER,newPlayer));              
//                ObjectWrapper data= myControl.receiveData();
//                if(data.getPerformative()==ObjectWrapper.REPLY_EDIT_PLAYER){
//                    if(data.getData().equals("ok"))
//                {        
//                    JOptionPane.showMessageDialog(this, "Email Changed successfully!");
//                    cmf.updatePlayerList();
//                    this.player=cmf.getPlayer();
//                    initPlayerInfo(player);
//            }
//            else{
//                JOptionPane.showMessageDialog(this, "Failed to change player's email!");    
//            }
                }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ChangePasswordFrm cpf = new ChangePasswordFrm(myControl, player);
        cpf.setVisible(true);
        cpf.setAlwaysOnTop(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    public void initPlayerInfo(Player player){  

             jTextField1.setText(player.getNickName());
             jTextField2.setText(player.getId()+"");
             jTextField3.setText(player.getMatchWon()+"");
             jTextField4.setText(player.getMatchPlayed()+"");
             if(player.getMatchPlayed()>0)
                jTextField5.setText(player.getMatchWon()*100/player.getMatchPlayed()+"%");
             jTextField6.setText(player.getEmail());
    }
     
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables

    
}
