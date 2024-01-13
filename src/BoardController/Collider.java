package BoardController;
import Entity.*;
import Operators.SoundOperator;

import java.util.ArrayList;
import java.util.Iterator;

public class Collider extends BoardController {

    public Collider(){

    }

    public static void RotatePlayer(){
        Player player = getCurrentPlayer();
        if (player.getCurrentRotation() == (byte) -1) {
            player.setAngle(player.getAngle() - player.getRotationSpeed());
        } else if (player.getCurrentRotation() == (byte) 1) {
            player.setAngle(player.getAngle() + player.getRotationSpeed());
        }
    }
    public static void MoveEntity(Entity entity){

        float rad = (float) (360/(2*Math.PI));

        float XOffset = (float) (entity.getSpeed() * Math.cos((entity.getAngle())/rad)),
        XPosition = entity.getPosition()[0] + XOffset;

        float YOffset = (float) (entity.getSpeed() * Math.sin((entity.getAngle())/rad)),
        YPosition = entity.getPosition()[1] + YOffset;

        float [] finalPos = new float[] {XPosition, YPosition};
        entity.setPosition(finalPos);
    }


    public static void RenderStep(){
        RotatePlayer();
        for (Entity enemyBullet : getEnemyBulletTable()){
            MoveEntity(enemyBullet);
        }

        for (Entity allyBullet : getAllyBulletTable()){
            MoveEntity(allyBullet);
        }

        for (Entity enemy : getEnemyTable()){
            MoveEntity(enemy);
        }

    }
    public static void ExecuteCollision(Entity object1, Entity object2){
        float[] pos1 = object1.getPosition(), pos2 = object2.getPosition();
        float radius1 = object1.getRadius(), radius2 = object2.getRadius();
        boolean collisionOccurs = Math.sqrt(Math.pow((pos1[0]-pos2[0]),2) + Math.pow((pos1[1]-pos2[1]),2))
                <= radius1 + radius2;

        if (collisionOccurs) {
            int minHp = Math.min(object1.getHp(), object2.getHp());
            object1.setHp(object1.getHp() - minHp);
            object2.setHp(object2.getHp() - minHp);
            if (object1 instanceof Enemy && object2 instanceof  Player){
                SoundOperator.playSoundOnThread("Sounds/sword.wav", false, 0);
            }
        }
    }

    public static void ObjectCollision(ArrayList<Entity> objectArray1, ArrayList<Entity> objectArray2) {
        for (Entity object1: objectArray1) {
            for (Entity object2: objectArray2) {
                ExecuteCollision(object1, object2);
            }
        }
    }

    public static ArrayList<Entity> RemoveDeadEntities(ArrayList<Entity>currentEntityTable){
        Iterator<Entity> iterator = currentEntityTable.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.getHp() <= 0) {
                if (entity instanceof Enemy) {
                    Statistics.killedEnemies++;
                    System.out.println("Punkty: " + Statistics.killedEnemies);
                }
                iterator.remove(); // Remove the current element

            }
        }
        return currentEntityTable;
    }

    public static void CheckColisions(){
        ObjectCollision(getAllyBulletTable(), getEnemyTable());

        ArrayList<Entity>playerTable = new ArrayList<>();
        playerTable.add(getCurrentPlayer());

        ObjectCollision(getEnemyTable(), playerTable);

        // Tworzenie granic lotu poscisków
        for (Entity entity : getAllyBulletTable()) {
            Bullet bullet = (Bullet) entity;

            float bulletPosX = bullet.getPosition()[0], bulletPosY = bullet.getPosition()[1];
            boolean hitBoundary = Math.sqrt(Math.pow(bulletPosX, 2) + Math.pow(bulletPosY, 2)) >= getBoardRadius();
            if (hitBoundary) {
                bullet.setHp(0);
            }
        }

        setEnemyBulletTable(RemoveDeadEntities(getEnemyBulletTable()));
        setAllyBulletTable(RemoveDeadEntities(getAllyBulletTable()));
        setEnemyTable(RemoveDeadEntities(getEnemyTable()));
    }
}