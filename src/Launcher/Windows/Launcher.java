package Launcher.Windows;

import Algorithms.Algorithm;
import Launcher.UI.UIButton;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class Launcher extends JFrame {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    private JLabel label;
    private Runnable onPlay;

    public Launcher(Function<Algorithm, Boolean> onCreateNewGame){
        super("Dragon Defence");

        // initial settings
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);

        onPlay = () -> {
            this.setVisible(false);
            //dispose();
            onCreateNewGame.apply(null);
        };

        // Load background image
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("backgroundimage.png").getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT)); // Replace with your image path
        label = new JLabel(imageIcon);
        label.setSize(WIDTH, HEIGHT);

        ButtonContainer();

        this.add(label);
        this.setVisible(true);
    }

    private void ButtonContainer(){
        Runnable chooseStrategy = () -> System.out.println("Bot");
        Runnable createWave = () -> System.out.println("Wave");

        addButton(new UIButton("Graj", onPlay), 0.48, 0.72, 0.5, 0.1);
        addButton(new UIButton("Gracz|Bot", chooseStrategy), 0.48-0.125, 0.85, 0.24, 0.1);
        addButton(new UIButton("Kreator Fal", createWave), 0.48+0.125, 0.85, 0.24, 0.1);
    }
    private void addButton(UIButton button, double x, double y, double width, double height){
        //button.setBounds(100,100,100,100);
        button.setBounds((int)(x*WIDTH-(double)width*WIDTH/2), (int)(y*HEIGHT-height*(double)HEIGHT/2), (int)(width*WIDTH), (int)(height*HEIGHT));
        label.add(button);
    }
}
