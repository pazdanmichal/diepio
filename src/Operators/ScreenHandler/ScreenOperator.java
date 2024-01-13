package Operators.ScreenHandler;

import Algorithms.Algorithm;
import BoardController.*;
import Entity.*;


import java.util.ArrayList;
import java.util.List;

import Operators.SoundOperator;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/*

 Klasa ScreenOperator jest główną klasą pakietu 'ScreenHandler'
        Przy rozpoczęciu działania programu tworzy okno,
        rozpoczyna pętlę gry za pośrednictwem klasy RenderStepOperator
        i nasłuchuje wejścia użytkownika przy pomocy metody 'KeyListener'

 */

public class ScreenOperator extends BoardController {

    //------------------------------// SKŁADOWE I KONSTRUKTOR \\---------------------------------\\

    // Składowe publiczne
    public static int screenSize;
    public static long window;
    public static long startTime;

    public static long frame;
    // Składowe prywatne
    private static long lastframe;

    // Konstruktor
    public ScreenOperator(Player currentPlayer, int tickRate, int boardRadius, int screenSizee, Algorithm playerAlgorithmm) {

        // Odwołania do BoardController
        setCurrentPlayer(currentPlayer);
        setTickRate(tickRate);
        setBoardRadius(boardRadius);

        setEnemyTable(new ArrayList<>());
        setAllyBulletTable(new ArrayList<>());
        setEnemyBulletTable(new ArrayList<>());

        // Deklarowanie zmiennych składowych
        startTime = System.currentTimeMillis();
        screenSize = screenSizee;
        frame = 0;

        // "Podwaliny" programu - uruchamiamy metodę InitialScreenSettings oraz konstruktor klasy RenderStepOperator
        InitialScreenSettings();
        RenderStepOperator renderStepOperator = new RenderStepOperator(playerAlgorithmm);
    }

    //---------------------------------------// METODY \\-----------------------------------------\\

    public static long getFrame() {
        return frame;
    }

    //----------- metoda RunGame - wywoływana bezpośrednio z pliku Main
    public static void RunGame(){
        SoundOperator.playSoundOnThread("Sounds/background.wav", true, -10.0f);

        RenderStepOperator.GameLoop();      // Główna pętla gry
        SoundOperator.StopSounds();
        glfwFreeCallbacks(window);          // Po zakończeniu pętli GameLoop zniszcz okno i zakończ program
        glfwDestroyWindow(window);
        glfwTerminate();

    }

    // Metoda wywoływana na samym początku programu - tworzenie okna gry i jego wstępne modyfikacje
    private static void InitialScreenSettings() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        window = glfwCreateWindow(screenSize, screenSize, "Slimy Traper Studios", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert vidMode != null;
        glfwSetWindowPos(window, (vidMode.width() - screenSize) / 2, (vidMode.height() - screenSize) / 2);

        glfwMakeContextCurrent(window);
        createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glfwSwapInterval(1); //TODO dodaj tutaj jako zmienna
        glfwShowWindow(window);

        glOrtho(0, screenSize, screenSize, 0, -1, 1);

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    // Metoda KeyListener nasłuchuje wejście użytkownika i podejmuje odpowiednie działania
    public static void KeyListener(Player currentPlayer) {

        long window = ScreenOperator.window;

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            // Spacebar is pressed
            currentPlayer.Shoot(startTime);
        }

        if ((glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) && (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)) {
            // both A and D are pressed
            currentPlayer.setCurrentRotation((byte) 0);
        } else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            // D is pressed
            currentPlayer.setCurrentRotation((byte) 1);
        } else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            // A is pressed
            currentPlayer.setCurrentRotation((byte) -1);
        } else if ((glfwGetKey(window, GLFW_KEY_D) == GLFW_RELEASE) && (glfwGetKey(window, GLFW_KEY_A) == GLFW_RELEASE)) {
            // nothing is pressed
            currentPlayer.setCurrentRotation((byte) 0);
        }

        if (glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS&& frame - lastframe > 100&&currentPlayer.getMaxHp()<10+currentPlayer.getMaxHpDiff()*5) {
            UpgradeWindow.upgrade(1, currentPlayer, currentPlayer.getMaxHpDiff(), currentPlayer.getAttackFrequencyDiff(), currentPlayer.getDmgDiff()); //zwiekszenie maxHP
            lastframe = frame;
        } else if (glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS&& frame - lastframe > 100&&currentPlayer.getAttackFrequency()>300-currentPlayer.getAttackFrequencyDiff()*5) {
            UpgradeWindow.upgrade(2, currentPlayer, currentPlayer.getMaxHpDiff(), currentPlayer.getAttackFrequencyDiff(), currentPlayer.getDmgDiff()); //zwiekszenie attackspeed
            lastframe = frame;
        } else if (glfwGetKey(window, GLFW_KEY_3) == GLFW_PRESS&& frame - lastframe > 100&&currentPlayer.getDamage()<1+currentPlayer.getDmgDiff()*5) {
            UpgradeWindow.upgrade(3, currentPlayer, currentPlayer.getMaxHpDiff(), currentPlayer.getAttackFrequencyDiff(), currentPlayer.getDmgDiff()); //zwiekszenie damage
            lastframe = frame;
        }
    }

    public static List<Enemy> enemyList() {
        List<Enemy> enemies = new ArrayList<>();
        for (Entity entity : getEnemyTable()) {
            enemies.add((Enemy) entity);
        }
        return enemies;
    }
}