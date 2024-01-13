package Launcher.Windows;

import Algorithms.ClosestBot;
import Algorithms.FastestBot;
import Algorithms.GroupBot;
import Algorithms.IdiotBot;
import Launcher.UI.UIButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

public class BotSelection extends LauncherWindow{

    private Menu menuWindow;

    private UIButton algorithmButtons[] = {
        new UIButton("Gracz", () -> changeAlgorithm(0)),
        new UIButton("Fastest Bot", () -> changeAlgorithm(1)),
        new UIButton("Closest Bot", () -> changeAlgorithm(2)),
        new UIButton("Group Bot", () -> changeAlgorithm(3)),
        new UIButton("Idiot Bot", () -> changeAlgorithm(4)),
    };
    public BotSelection(Menu _menuWindow){
        super("Images/backgroundImageNoTitle.png");

        menuWindow = _menuWindow;
        algorithmButtons[0].changeStatus(true);
        menuWindow.setPlayerAlgorithm(null);

        ButtonContainer();
    }

    private void changeAlgorithm(int index){
        switch (index) {
            case 0:
                menuWindow.setPlayerAlgorithm(null);
                break;
            case 1:
                menuWindow.setPlayerAlgorithm(new FastestBot());
                break;
            case 2:
                menuWindow.setPlayerAlgorithm(new ClosestBot());
                break;
            case 3:
                menuWindow.setPlayerAlgorithm(new GroupBot());
                break;
            case 4:
                menuWindow.setPlayerAlgorithm(new IdiotBot());
                break;
        }

        for (int i = 0; i < algorithmButtons.length; i++) {
            if (i != index) {
                algorithmButtons[i].changeStatus(false);
            } else {
                algorithmButtons[i].changeStatus(true);
            }
        }
    }

    private void ButtonContainer(){

        addButton(algorithmButtons[0], 0.48+0.07, 0.40, 0.35, 0.1);
        addButton(new UIButton("<-", () -> {
            this.setVisible(false);
            menuWindow.setVisible(true);
        }), 0.48-0.18, 0.40, 0.13, 0.1);

        addButton(algorithmButtons[1], 0.48-0.125, 0.53, 0.24, 0.1);
        addButton(algorithmButtons[2], 0.48+0.125, 0.53, 0.24, 0.1);
        addButton(algorithmButtons[3], 0.48-0.125, 0.66, 0.24, 0.1);
        addButton(algorithmButtons[4], 0.48+0.125, 0.66, 0.24, 0.1);

        addButton(new UIButton("Wybierz bota z pliku ", () -> changeAlgorithm(0),
                new Color(171, 109, 2), new Color(194, 162, 0),  new Color(255, 163, 5)), 0.48, 0.79, 0.49, 0.1);

    }
}
