package Operators.ScreenHandler;

import Algorithms.Algorithm;
import BoardController.*;
import Entity.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;

/*

 Klasa RenderStepOperator odpowiada za wszystkie akcje związane z tworzeniem i modyfikowaniem obrazu
        wyświetlanego na ekranie w obrębie czasowym jednego ticka

 Metody klasy podzielone są na 'Główną Metodę' i na wywoływane przez nią 'Podrzędne metody'

 */

public class RenderStepOperator extends BoardController {

    //------------------------------// SKŁADOWE I KONSTRUKTOR \\---------------------------------\\

    private static Algorithm playerAlgorithm;
    private static WaveHandler waveHandler;
    private static Player player;

    private static DrawingOperator drawingOperator;

    // Konstruktor
    public RenderStepOperator(Algorithm playerAlgorithmm) {

        super();

        player = getCurrentPlayer();

        playerAlgorithm = playerAlgorithmm;
        waveHandler = new WaveHandler(4, "TestWaves0");

        drawingOperator = new DrawingOperator(ScreenOperator.screenSize);
    }

    //----------------------------// GŁÓWNA METODA - GAME LOOP \\--------------------------------\\
    // Metoda GameLoop odpowiada za wywoływanie wszystkich podrzędnych metod zdefiniowanych dalej \\

    public static void GameLoop() {
        while (!glfwWindowShouldClose(ScreenOperator.window) && player.getHp() > 0) {

            ScreenOperator.frame += 1;

            Collider.RenderStep();
            Collider.CheckColisions();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glColor3f(1.0f, 1.0f, 1.0f);


            // Renderowanie tła
            drawingOperator.renderImage(drawingOperator.posCalc(new float[]{0,0}, 1000/(float)Math.sqrt(2), 0), 2);

            //new float[][] {{0,0},{1000,0},{1000,1000},{0,1000}}

            if (!waveHandler.IsRunning(ScreenOperator.enemyList())) {


                waveHandler.NextWave();
                UpgradeWindow.nextUpgrade(player);

                System.out.println("Ilość punktów ulepszeń: " + player.getCurrentPkt());
                System.out.println("Max Hp: " + player.getMaxHp());
                System.out.println("Attack Frequency: " + player.getAttackFrequency());
                System.out.println("Damage: " + player.getDamage());

                if (playerAlgorithm != null) {
                    playerAlgorithm.Init();
                }

            } else {

                waveHandler.TrySpawnEnemy(System.currentTimeMillis());

                if (playerAlgorithm == null) {

                    ScreenOperator.KeyListener(player);

                } else {

                    AutonomicBotMove();

                }
            }


            RenderEntities();
            // Swap the front and back buffers
            //long startTime = System.currentTimeMillis();
            //System.out.println(System.currentTimeMillis() - startTime);
            if (getCurrentPlayer().getCurrentPkt() > 0) {
                drawingOperator.drawUpgradeBar(20, 20, 50, 20, 5, 3, player,true);
            }
            else{
                drawingOperator.drawUpgradeBar(20, 20, 50, 20, 5, 3, player,false);
            }

            // Poll for and process events
            glfwSwapBuffers(ScreenOperator.window);
            glfwPollEvents();
        }

    }

    //--------------------------------// PODRZĘDNE METODY \\-------------------------------------\\

    private static void RenderEntities() {


        Player player = getCurrentPlayer();

        for (Entity entity : getAllyBulletTable()) {
            Bullet currentBullet = (Bullet) entity;
            drawingOperator.renderImage(drawingOperator.posCalc(currentBullet.getPosition(), currentBullet.getRadius()*3, currentBullet.getAngle()), 8);
//            drawingOperator.drawCircle(0, 0, 0.6f, currentBullet.getRadius(), currentBullet.getPosition()[0], currentBullet.getPosition()[1]);
        }
        for (Entity entity : getEnemyBulletTable()) {
            Bullet currentBullet = (Bullet) entity;
            drawingOperator.drawCircle(0.6f, 0, 0, currentBullet.getRadius(), currentBullet.getPosition()[0], currentBullet.getPosition()[1]);
        }
        for (Entity enemy : getEnemyTable()) {
            Enemy currentEnemy = (Enemy) enemy;
//            drawingOperator.drawCircle(1, 0, 0, currentEnemy.getRadius(), currentEnemy.getPosition()[0], currentEnemy.getPosition()[1]);
            drawingOperator.renderImage(drawingOperator.posCalc(currentEnemy.getPosition(), currentEnemy.getRadius()*3, currentEnemy.getAngle()), 4);
        }

//        drawingOperator.drawLine(0.4f, 0.4f, 0.4f, player.getPosition()[0], player.getPosition()[1], player.getRadius() * player.getGunLengthMultiply(), player.getRadius() * player.getGunWidthMultiply(), player.getAngle());
//        drawingOperator.drawCircle(0f, 0.75f, 1f, player.getRadius(), player.getPosition()[0], player.getPosition()[1]);
        drawingOperator.renderImage(drawingOperator.posCalc(player.getPosition(), player.getRadius()*3, player.getAngle() + 180), 3);

        drawingOperator.drawHealthBar(player);

//        if (getCurrentPlayer().getCurrentPkt() > 0) {
//            float[][] pos = new float[][] {{700,0}, {900,0}, {900,200}, {700,200}};
//            drawingOperator.renderImage(pos, 0);
//        }

    }

    private static void AutonomicBotMove() {
        playerAlgorithm.Init();
        getCurrentPlayer().setCurrentRotation(playerAlgorithm.Move());
        boolean isShot = false;
        if (playerAlgorithm.TryShoot()) {
            isShot = getCurrentPlayer().Shoot(ScreenOperator.startTime);
        }
        playerAlgorithm.FetchCurrentFrameInfo(isShot);
    }
}
