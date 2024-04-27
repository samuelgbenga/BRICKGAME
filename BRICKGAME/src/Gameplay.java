import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int noOfBricks;
    private Timer timer; // to monitor the game movement
    private int delay = 6;
    private int playerX = 100;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -1;
    private int ballDirY = -2;
    private MapGenerator map;

    //

    public Gameplay() {
        map = new MapGenerator(4, 12);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){

        //background
        g.setColor(Color.green);
        g.fillRect(1,1, 698, 570);

        //draw brick map
      //  map.draw((Graphics2D) g);

        // border
        g.setColor(Color.black);
        g.fillRect(0,0, 3, 570);
        g.fillRect(0,0, 698, 3);
        g.fillRect(698,0, 3, 570);
        g.fillRect(0,570, 698, 3);

        // come back later for the score


        //the paddle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(playerX, 545, 100, 20);


        // the ball

        g.setColor(Color.red);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // later for when you lose and when you win if condition

    }





   // to control the paddle actions on key pressed
    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 595){
                playerX = 595;
            }
            else{
                moveRight();
            }

        }

        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            if(playerX <= 11){
                playerX = 11;
            }
            else{
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballDirX = -1;
                ballDirY = -1;
                playerX = 100;

                repaint();
            }
        }

    }

public void moveRight(){
        play = true;
        playerX+=20;
        repaint();
}
public void moveLeft(){
        play = true;
        playerX-=20;
        repaint();

}

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    // the control the ball actions
    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();
        if(play){
            ballPosX += ballDirX;
            ballPosY += ballDirY;

            // intersection with the ball
            if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX, 527, 100,20))){
                ballDirY = -ballDirY;
                ballDirX = -2;
            }
            else  if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX+70, 527, 100,20))){
                ballDirY = -ballDirY;
                ballDirX = ballDirX + 1;
            }
            else if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX+30, 527, 100,20))){
                ballDirY = -ballDirY;
            }

            // intersections with frame borders
            if(ballPosX <= 0){
                ballDirX = -ballDirX;
            }
            if(ballPosY <= 0)
            {
                ballDirY = -ballDirY;
            }
            if(ballPosX > 680){
                ballDirX = -ballDirX;
            }

        }

        repaint();

    }
}
