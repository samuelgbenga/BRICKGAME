import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int noOfBricks = 48;
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
        map.draw((Graphics2D) g);

        // border
        g.setColor(Color.black);
        g.fillRect(0,0, 3, 570);
        g.fillRect(0,0, 698, 3);
        g.fillRect(698,0, 3, 570);
        g.fillRect(0,570, 698, 3);

        // come back later for the score
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 10,30);



        //the paddle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(playerX, 545, 100, 20);


        // the ball

        g.setColor(Color.red);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // later for when you lose and when you win if condition
        // when the player won
        if(noOfBricks <= 0){
            play = false;

            ballDirY = 0;
            ballDirX = 0;
            g.setColor(Color.blue);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won", 260, 300);

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }

        if(ballPosY > 545){
            play = false;

            ballDirY = 0;
            ballDirX = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Loss! Score: "+score, 260, 300);

            g.setColor(Color.blue);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press (Enter) to Restart", 230,350);
        }

        g.dispose();
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
                score = 0;
                noOfBricks = 48;
                map = new MapGenerator(4, 12);

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
            if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX, 545, 100,20))){
                ballDirY = -ballDirY;
                ballDirX = -2;
            }
            else  if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX+70, 545, 100,20))){
                ballDirY = -ballDirY;
                ballDirX = 2;
            }
            else if(new Rectangle(ballPosX, ballPosY, 20,20).intersects(new Rectangle(playerX+30, 545, 100,20))){
                ballDirY = -ballDirY;
            }


            // ball collision with brick
        B: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if (map.map[i][j] > 0) {
                        int brickX = j*map.brickWidth+80;
                        int brickY = i*map.brickHeight+50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;
                        // check for intersection using rect.intersect

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);


                        if(ballRect.intersects(rect)){
                            map.setBrickValue(0, i, j);
                            score+= 5;
                            noOfBricks--;


                            // for when the brick hit the side to change x direction
                            if(ballPosX + 19 <= rect.x || ballPosX + 1 >= rect.x+rect.width){
                                ballDirX = -ballDirX;
                            }
                            else{
                                ballDirY = -ballDirY;
                            }

                            break B;
                        }
                    }
                }
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
