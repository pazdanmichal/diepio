package BoardController;
import Entity.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Collider extends BoardController{
    public Collider(){};
    public void RotatePlayer(Entity entity){
        Player player = (Player) entity;
        if (player.getCurrentRotation() == (byte) -1) {
            player.setAngle(player.getAngle() - player.getRotationSpeed());
        } else if (player.getCurrentRotation() == (byte) 1) {
            player.setAngle(player.getAngle() + player.getRotationSpeed());
        }
    }
    public void MoveEntity(Entity entity){

        float rad = (float) (360/(2*Math.PI));

        float XOffset = (float) (entity.getSpeed() * Math.cos((entity.getAngle())/rad)),
        XPosition = entity.getPosition()[0] + XOffset;

        float YOffset = (float) (entity.getSpeed() * Math.sin((entity.getAngle())/rad)),
        YPosition = entity.getPosition()[1] + YOffset;

        float [] finalPos = new float[] {XPosition, YPosition};
        entity.setPosition(finalPos);
    }

    public void RenderStep(ArrayList<Entity> entityTable){
        for (Entity entity : entityTable) {

            // Jeśli obiekt jest klasą Player to go obróć
            if (entity instanceof Player) {
                RotatePlayer(entity);
            }

            // Jeśli obiekt jest klasą Bullet albo Enemy to go przesuń
            if ((entity instanceof Bullet) || (entity instanceof Enemy)) {
                MoveEntity(entity);
            }
        }
        super.setEntityTable(entityTable);
    }

    public ArrayList<ArrayList<Entity>> ObjectCollision(ArrayList<Entity> objectArray1, ArrayList<Entity> objectArray2) {
        for (Entity object1: objectArray1) {
            for (Entity object2: objectArray2) {
                float[] pos1 = object1.getPosition(), pos2 = object2.getPosition();
                float radius1 = object1.getRadius(), radius2 = object2.getRadius();
                boolean collisionOccurs = Math.sqrt(Math.pow((pos1[0]-pos2[0]),2) + Math.pow((pos1[1]-pos2[1]),2))
                        <= radius1 + radius2;

                if (collisionOccurs) {
                    int minHp = Math.min(object1.getHp(), object2.getHp());
                    object1.setHp(object1.getHp() - minHp);
                    object2.setHp(object2.getHp() - minHp);
                }
            }
        }
        return new ArrayList<> (Arrays.asList(objectArray1, objectArray2));
    }

    public void CheckColisions(ArrayList<Entity> entityTable){
        ArrayList<Entity> allyBulletTable = new ArrayList<>(), enemyBulletTable = new ArrayList<>(),
                enemyTable = new ArrayList<>(), playerTable = new ArrayList<>();

        for (Entity entity : entityTable) {
            if (entity instanceof Bullet) {
                Bullet currentBullet = (Bullet) entity;
                if (currentBullet.isAlly()) {
                    allyBulletTable.add(currentBullet);
                } else {
                    enemyBulletTable.add(currentBullet);
                }
            }
            if (entity instanceof Enemy)    { enemyTable.add((Enemy) entity);   }
            if (entity instanceof Player)   { playerTable.add(entity);          }
        }

        ArrayList<ArrayList<Entity>> x = ObjectCollision(allyBulletTable, enemyTable);
        allyBulletTable = x.get(0); enemyTable = x.get(1);

        ArrayList<ArrayList<Entity>> y = ObjectCollision(enemyTable, playerTable);
        enemyTable = y.get(0); playerTable = y.get(1);

        // Tworzenie granic lotu poscisków
        for (Entity entity : allyBulletTable) {
            Bullet bullet = (Bullet) entity;

            float bulletPosX = bullet.getPosition()[0], bulletPosY = bullet.getPosition()[1];
            boolean hitBoundary = Math.sqrt(Math.pow(bulletPosX, 2) + Math.pow(bulletPosY, 2)) >= this.getBoardRadius();
            if (hitBoundary) {
                bullet.setHp(0);
            }
        }

        ArrayList<Entity> newEntityTable = new ArrayList<>();
        for (Entity entity: entityTable) {
            if (entity.getHp() > 0) {
                newEntityTable.add(entity);
            }
        }

        super.setEntityTable(newEntityTable);

    }
}