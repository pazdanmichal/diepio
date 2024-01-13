package Launcher.Windows;

import Algorithms.*;
import Launcher.UI.UIButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
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

        addButton(algorithmButtons[0], 0.48+0.1, 0.40, 0.28, 0.1);
        addButton(new UIButton("Powrot", () -> {
            this.setVisible(false);
            menuWindow.setVisible(true);
        }), 0.48-0.145, 0.40, 0.20, 0.1);

        addButton(algorithmButtons[1], 0.48-0.125, 0.53, 0.24, 0.1);
        addButton(algorithmButtons[2], 0.48+0.125, 0.53, 0.24, 0.1);
        addButton(algorithmButtons[3], 0.48-0.125, 0.66, 0.24, 0.1);
        addButton(algorithmButtons[4], 0.48+0.125, 0.66, 0.24, 0.1);

        UIButton buttonToImportFile = new UIButton("Wybierz bota z pliku ",
                new Color(171, 109, 2), new Color(194, 162, 0),  new Color(255, 163, 5));

        buttonToImportFile.addFunction(() -> {
            Pair<Algorithm, String> fileImportInfo = tryLoadAlgorithmFromFile();

            Algorithm algorithm = fileImportInfo.first;
            String textInfo = fileImportInfo.second;

            buttonToImportFile.setText(textInfo);
            if(algorithm == null){
                buttonToImportFile.changeStatus(false);
            } else {
                buttonToImportFile.changeStatus(true);
                menuWindow.setPlayerAlgorithm(algorithm);
                changeAlgorithm(-1);
            }
        });

        addButton(buttonToImportFile, 0.48, 0.79, 0.49, 0.1);

    }

    public class Pair<T, U>{
        public T first;
        public U second;

        public Pair(T _first, U _second){
            this.first = _first;
            this.second = _second;
        }
    }

    private Pair<Algorithm, String> tryLoadAlgorithmFromFile(){
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.getName().endsWith(".class")) {
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                try {
                    // Load the class
                    URL url = selectedFile.toURI().toURL();
                    URL[] urls = new URL[]{url};
                    ClassLoader cl = new URLClassLoader(urls);
                    String className = selectedFile.getName().substring(0, selectedFile.getName().length() - 6);
                    System.out.println(className);
                    Class cls = cl.loadClass("Algorithms." + className);

                    // Check if the class implements the Algorithm interface
                    for (Class<?> iface : cls.getInterfaces()) {
                        if (iface.equals(Algorithm.class)) {
                            // Create an instance of the class
                            Constructor<?> constructor = cls.getConstructor();
                            Algorithm instance = (Algorithm) constructor.newInstance();

                            return new Pair<>(instance, "Algorithms." + className + ".class");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return new Pair<>(null, "Blad odczytu. Sprobuj ponownie");
                }
            } else {
                System.out.println("Please select a .class file");
                return new Pair<>(null, "Blad typu pliku. Sprobuj ponownie");
            }
        }
        return new Pair<>(null, "Blad odczytu. Sprobuj ponownie");
    }
}
