package BoardController;

import Entity.*;
import java.util.ArrayList;

public abstract class BoardController {
    private static ArrayList<Entity> enemyBulletTable;
    private static ArrayList<Entity> allyBulletTable;
    private static ArrayList<Entity> enemyTable;
    private static Player currentPlayer;

    private static int tickRate;
    private static int boardRadius;


    public BoardController(){}

    public static void setBoardRadius(int boardRadiuss) {
        boardRadius = boardRadiuss;
    }

    public static int getBoardRadius() {
        return boardRadius;
    }

    public static void setTickRate(int tickRatee) {
        tickRate = tickRatee;
    }

    public static int getTickRate() {
        return tickRate;
    }

    public static void addEntity(Entity entity) {
        if (entity instanceof Enemy){
            enemyTable.add((Enemy) entity);
        } else if (entity instanceof Bullet){
            if (((Bullet) entity).isAlly()){
                allyBulletTable.add((Bullet) entity);
            } else {
                enemyBulletTable.add((Bullet) entity);
            }

        }
    }

    public static  ArrayList<Entity> getEnemyBulletTable() {
        return enemyBulletTable;
    }

    public static void setEnemyBulletTable(ArrayList<Entity> enemyBulletTablee) {
        enemyBulletTable = enemyBulletTablee;
    }

    public static ArrayList<Entity> getAllyBulletTable() {
        return allyBulletTable;
    }

    public static  void setAllyBulletTable(ArrayList<Entity> allyBulletTablee) {
        allyBulletTable = allyBulletTablee;
    }

    public static  ArrayList<Entity> getEnemyTable() {
        return enemyTable;
    }

    public static  void setEnemyTable(ArrayList<Entity> enemyTablee) {
        enemyTable = enemyTablee;
    }

    public static  Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayerr) {
        currentPlayer = currentPlayerr;
    }
}
