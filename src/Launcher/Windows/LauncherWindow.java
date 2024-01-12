package Launcher.Windows;

import Launcher.UI.UIButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class LauncherWindow extends JFrame {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;

    protected JLabel label;

    public LauncherWindow(String pathToBackgroundImage){
        super("Dragon Defence");

        // initial settings
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setUndecorated(false);

        // Load background image
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(pathToBackgroundImage).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT)); // Replace with your image path
        label = new JLabel(imageIcon);
        label.setSize(WIDTH, HEIGHT);

        this.add(label);
    }

    protected void addButton(UIButton button, double x, double y, double width, double height){
        //button.setBounds(100,100,100,100);
        button.setBounds((int)(x*WIDTH-(double)width*WIDTH/2), (int)(y*HEIGHT-height*(double)HEIGHT/2), (int)(width*WIDTH), (int)(height*HEIGHT));
        label.add(button);
    }

    protected void dropFile() {

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }
    }

}
