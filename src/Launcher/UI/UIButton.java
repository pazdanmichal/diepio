package Launcher.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class UIButton extends JButton {

    private Color backgroundColor = new Color(171, 131, 82);
    private Color borderColor = new Color(194, 162, 0);
    private Color hoverBackgroundColor = new Color(186, 64, 34);
    private Color onPressedColor = new Color(148, 0, 0);
    private Color _backgroundColor = new Color(171, 131, 82);

    public UIButton(String text){
        super(text);

        this.setBackground(backgroundColor);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/font.otf"));
            font = font.deriveFont(Font.PLAIN, 18); // Set font size
            this.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        hoverMouseEffect();
    }

    public void addFunction(Runnable function){
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function.run();
            }
        });
    }
    public UIButton(String text, Runnable function) {
        this(text);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function.run();
            }
        });
    }

    public UIButton(Icon icon, Runnable function){
        super(icon);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                function.run();
            }
        });
        this.setBackground(backgroundColor);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/font.otf"));
            font = font.deriveFont(Font.PLAIN, 18); // Set font size
            this.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        hoverMouseEffect();
    }

    public UIButton(String text, Runnable function, Color backgroundColor, Color borderColor, Color hoverBackgroundColor){
        this(text, function);
        this.backgroundColor = backgroundColor;
        this._backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.setBackground(backgroundColor);
    }

    public UIButton(String text, Color backgroundColor, Color borderColor, Color hoverBackgroundColor){
        this(text);
        this.backgroundColor = backgroundColor;
        this._backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.hoverBackgroundColor = hoverBackgroundColor;
        this.setBackground(backgroundColor);
    }

    public void changeStatus(boolean isPressed){
        if(isPressed){
            this.backgroundColor = onPressedColor;
        } else {
            this.backgroundColor = _backgroundColor;
        }
        this.setBackground(backgroundColor);
    }
    private void hoverMouseEffect(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackgroundColor); // Change color when mouse enters
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor); // Change color back when mouse exits
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY); // Change color when button is pressed
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 50, 50);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(2));// Change to the color of your choice
        g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 50, 50);
    }
}
