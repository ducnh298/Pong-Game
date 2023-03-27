/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Game;

import control.ClientCtr;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import model.Player;
import view.ClientMainFrm;

/**
 *
 * @author de
 */
public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = GAME_WIDTH * 5 / 9;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    NotificationFrm notify;
    ClientMainFrm cmf;
    ClientCtr myControl;
    int playerId;
    Player player1;
    Player player2;
    ArrayList<Player> playerList;
    GamePanel(ClientMainFrm cmf, ClientCtr socket,int playerId, int player1,int player2) {
        newPaddles();
        newBall();
        this.cmf=cmf;
        this.myControl=myControl;
        cmf.updatePlayerList();
        this.playerList=cmf.getPlayerList();
        for(int i=0;i<playerList.size();i++){
            if(player1==playerList.get(i).getId())
                this.player1=playerList.get(i);
            if(player2==playerList.get(i).getId())
                this.player2=playerList.get(i);
        }
        score = new Score(GAME_WIDTH, GAME_HEIGHT,this.player1,this.player2);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync(); 
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public boolean checkCollision() {

        //bóng đập vào phần trên và dưới của cửa sổ chạy game sẽ nảy lại
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        //bóng đập vào cái "vợt" sẽ nảy ra
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //tăng độ khó (tốc độ bóng)
            if (ball.yVelocity > 0) {
                ball.yVelocity++; //tăng độ khó (tốc độ bóng)
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //tăng độ khó (tốc độ bóng)
            if (ball.yVelocity > 0) {
                ball.yVelocity++; //tăng độ khó (tốc độ bóng)
            } else {
                ball.yVelocity--;
            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //"vợt" sẽ dừng lại khi gặp mép trên hoặc mép dưới của cửa sổ chạy game
        if (paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if (paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        //cộng 1đ cho người thắng và reset lại "vợt" và bóng để tiếp tục lượt mới
        if (ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2: " + score.player2);
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: " + score.player1);
        }
        if (score.player1 == 5) {
            System.out.println("Player 1 win!");
            notify = new NotificationFrm("Player 1 win!");
            return false;
        }

        if (score.player2 == 5) {
            System.out.println("Player 2 win!");
            notify = new NotificationFrm("Player 2 win!");
            return false;
        }
        return true;
    }

    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                boolean check = checkCollision();
                repaint();
                delta--;
                if (!check) {
                    break;
                }
            }
        }
    }

    public class AL extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
