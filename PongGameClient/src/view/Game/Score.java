/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Game;

import java.awt.*;
import model.Player;

/**
 *
 * @author de
 */
public class Score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    //Biến lưu điểm của 2 người chơi
    public int player1;
    public int player2;
    Player Player1;
    Player Player2;
    public Score(int GAME_WIDTH, int GAME_HEIGHT,Player Player1,Player Player2) {
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
        
        this.Player1=Player1;
        this.Player2=Player2;
        
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));

        g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
        
        g.drawString(Player1.getNickName(),(GAME_WIDTH / 2) - 400, 50);
        g.drawString(Player2.getNickName(),(GAME_WIDTH / 2) + 200, 50);
//        Vẽ số điểm
        g.drawString(String.valueOf(player1 / 10) + String.valueOf(player1 % 10), (GAME_WIDTH / 2) - 85, 50);
        g.drawString(String.valueOf(player2 / 10) + String.valueOf(player2 % 10), (GAME_WIDTH / 2) + 20, 50);
    }
}
