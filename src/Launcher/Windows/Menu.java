package Launcher.Windows;

import Algorithms.Algorithm;
import Launcher.UI.UIButton;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class Menu extends LauncherWindow {
    private Runnable onPlay;
    private BotSelection botSelectionWindow;
    private Algorithm playerAlgorithm;

    public Algorithm getPlayerAlgorithm() {
        return playerAlgorithm;
    }

    public void setPlayerAlgorithm(Algorithm playerAlgorithm) {
        this.playerAlgorithm = playerAlgorithm;
    }

    public Menu(Function<Algorithm, Boolean> onCreateNewGame){
        super("Images/backgroundImageTitle.png");

        botSelectionWindow = new BotSelection(this);
        botSelectionWindow.setVisible(false);

        onPlay = () -> {
            this.setVisible(false);
            //dispose();
            onCreateNewGame.apply(playerAlgorithm);
        };

        ButtonContainer();

        this.setVisible(true);
    }
    private void ButtonContainer(){
        Runnable chooseStrategy = () -> {
            botSelectionWindow.setVisible(true);
            this.setVisible(false);
        };
        Runnable createWave = () -> {};

        addButton(new UIButton("Graj", onPlay), 0.48, 0.72, 0.5, 0.1);
        addButton(new UIButton("Gracz|Bot", chooseStrategy), 0.48-0.125, 0.85, 0.24, 0.1);
        addButton(new UIButton("Kreator Fal", createWave), 0.48+0.125, 0.85, 0.24, 0.1);
    }

}
