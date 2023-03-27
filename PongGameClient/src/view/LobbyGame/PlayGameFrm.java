/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.LobbyGame;

import control.ClientCtr;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Match;
import model.Player;
import model.ObjectWrapper;
import model.PlayerMatch;
import model.Result;
import view.ClientMainFrm;

/**
 *
 * @author nguye
 */
public class PlayGameFrm extends javax.swing.JFrame {
ClientMainFrm cmf;
ClientCtr myControl;
ArrayList<Player> playerInGame;
int playerId;
Player player1;
Player player2;
ArrayList<Player> playerList;
PlayGameFrm gf;
countTime ct = new countTime();
playing1 p1 = new playing1();
playing2 p2 = new playing2();
Match match = new Match();
Result result = new Result();
    /**
     * Creates new form GameFrm
     */
    public PlayGameFrm(ClientMainFrm cmf, ClientCtr socket,int playerId, int player1,int player2) {
        super("Ping pong game");
        initComponents();
        centreWindow(this);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.cmf=cmf;
        this.myControl=socket;
        this.playerId=playerId;

        cmf.updatePlayerList();
        this.playerList=cmf.getPlayerList();
        this.gf=this;
        for(int i=0;i<playerList.size();i++){
            if(player1==playerList.get(i).getId())
                this.player1=playerList.get(i);
            if(player2==playerList.get(i).getId())
                this.player2=playerList.get(i);
        }
        jLabel4.setText(cmf.getPlayer().getNickName());
        jLabel2.setText(this.player1.getNickName()+" and "+this.player2.getNickName());
        jLabel1.setOpaque(true);
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_RESULT_MATCH, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_MATCH, this));
        myControl.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_PLAYER_MATCH, this));
        if(playerId==this.player1.getId()){
            Match newMatch = new Match();
            newMatch.setTournament(null);
            Date date = null;
            newMatch.setDate(date);
            myControl.sendData(new ObjectWrapper(ObjectWrapper.NEW_MATCH, newMatch ));
        }
        
                ct.start();
                p1.start(); 
                p2.start();   
                
    }
      public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
}
      public void receivedData(ObjectWrapper data) {
        if(data.getPerformative()==ObjectWrapper.REPLY_MATCH){
            if(data.getData() instanceof Match){
                match = (Match) data.getData();
                System.out.println("Match id: "+ match.getId());
                PlayerMatch pm1 = new PlayerMatch(match, player1, null);
                PlayerMatch pm2 = new PlayerMatch(match, player2, null);
                myControl.sendData(new ObjectWrapper(ObjectWrapper.NEW_PLAYER_MATCH, pm1 ));
                myControl.sendData(new ObjectWrapper(ObjectWrapper.NEW_PLAYER_MATCH, pm2 ));
            }
            else
                JOptionPane.showMessageDialog(this, "Failed to create new match from server!");
        }
        if(data.getPerformative()==ObjectWrapper.REPLY_PLAYER_MATCH){
            PlayerMatch pm = (PlayerMatch) data.getData();
            this.match = pm.getMatch();
        }
        if(data.getPerformative()==ObjectWrapper.REPLY_RESULT_MATCH){
            if(data.getData().equals("ok")){
                if(result.getIsWin()==1){
                    JOptionPane.showMessageDialog(this, "YOU WIN!!!");
                }
                else 
                    JOptionPane.showMessageDialog(this, "YOU LOSE!!!");
            }
            else
                JOptionPane.showMessageDialog(this, "Failed update result from server!");
        }
      }
      class countTime extends Thread{
          @Override
    public void run(){
        int time=5;
        while(true){
                time--;
                jLabel3.setText("Time: "+time);
                if(time==0)
                {   
                    myControl.sendData(new ObjectWrapper(ObjectWrapper.CLIENT_INFORM_OFFGAME,playerId));

                if(playerId==player1.getId()){
                    result.setMatch(match);
                    result.setPlayer(player1);
                    result.setTournament(null);
                    result.setIsWin(1);
                    System.out.println(player1.getNickName()+" win! ");  
                }
                else {
                    result.setMatch(match);
                    result.setPlayer(player2);
                    result.setTournament(null);
                    result.setIsWin(0);
                     System.out.println(player2.getNickName()+" lose ! ");
                }
                myControl.sendData(new ObjectWrapper(ObjectWrapper.RESULT_MATCH,result));
                    jLabel1.setText("End Game!");
                    this.stop();
                    p1.interrupt();
                    p2.interrupt();
                }
            try{
                sleep(1000);//milisecond
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
      }
    }
    class playing1 extends Thread{
        @Override
    public void run(){
        while(true){
            jLabel1.setForeground(Color.red);
            jLabel1.setBackground(Color.gray);
            try{
                sleep(350);//milisecond
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
    }
    }class playing2 extends Thread{
        @Override
    public void run(){
        
        while(true){
            jLabel1.setForeground(Color.white);
            jLabel1.setBackground(Color.blue);
            try{
                sleep(500);//milisecond
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 204, 204));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Playing Game!!!");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Time left: ");

        jLabel4.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int h = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?","Closing", JOptionPane.YES_NO_OPTION);
        
        if (h == JOptionPane.YES_OPTION) {
                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                
                ct.stop();
                p1.stop();
                p2.stop();
                 this.dispose();
                cmf.setVisible(true); 
        }
            else if(h == JOptionPane.NO_OPTION){
               setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            }
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
