import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Brick Game");
        Gameplay game = new Gameplay();
        frame.setBounds(300, 100, 700, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);




        //System.out.println("Hello world!");
    }
}   