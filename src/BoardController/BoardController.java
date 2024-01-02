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

    /*public BoardController(ArrayList<Bullet> enemyBulletTable, ArrayList<Bullet> allyBulletTable, ArrayList<Enemy> enemyTable, Player currentPlayer, int boardRadius) {
        BoardController.enemyBulletTable = enemyBulletTable;
        BoardController.allyBulletTable = allyBulletTable;
        BoardController.enemyTable = enemyTable;
        BoardController.currentPlayer = currentPlayer;
        BoardController.boardRadius = boardRadius;
    }*/

    public static void setBoardRadius(int boardRadius) {
        BoardController.boardRadius = boardRadius;
    }

    public static int getBoardRadius() {
        return boardRadius;
    }

    public static void setTickRate(int tickRate) {
        BoardController.tickRate = tickRate;
    }

    public static int getTickRate() {
        return tickRate;
    }

    public static void addEntity(Entity entity) {
        if (entity instanceof Enemy){
            BoardController.enemyTable.add((Enemy) entity);
        } else if (entity instanceof Bullet){
            if (((Bullet) entity).isAlly()){
                BoardController.allyBulletTable.add((Bullet) entity);
            } else {
                BoardController.enemyBulletTable.add((Bullet) entity);
            }

        }
    }

    public static ArrayList<Entity> getEnemyBulletTable() {
        return enemyBulletTable;
    }

    public static void setEnemyBulletTable(ArrayList<Entity> enemyBulletTable) {
        BoardController.enemyBulletTable = enemyBulletTable;
    }

    public static ArrayList<Entity> getAllyBulletTable() {
        return allyBulletTable;
    }

    public static void setAllyBulletTable(ArrayList<Entity> allyBulletTable) {
        BoardController.allyBulletTable = allyBulletTable;
    }

    public static ArrayList<Entity> getEnemyTable() {
        return enemyTable;
    }

    public static void setEnemyTable(ArrayList<Entity> enemyTable) {
        BoardController.enemyTable = enemyTable;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(Player currentPlayer) {
        BoardController.currentPlayer = currentPlayer;
    }
}
