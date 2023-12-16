package BoardController;
import Entity.*;

import java.util.ArrayList;

public class Collider extends BoardController{

    public Collider(){};

    public void RenderStep(ArrayList<Entity> entityTable){

        for (int i = 0; i< entityTable.size(); i++){
            //System.out.println("cza");
            //System.out.println("cza");
            //jesli obiekt ma klase player to go oborc
            if(entityTable.get(i) instanceof Player){
                Player currentPlayer = (Player)entityTable.get(i);
                if(currentPlayer.getCurrentRotation() == (byte) -1){
                    currentPlayer.setAngle(currentPlayer.getAngle() - currentPlayer.getRotationSpeed());
                } else if (currentPlayer.getCurrentRotation() == (byte) 1) {
                    currentPlayer.setAngle(currentPlayer.getAngle() + currentPlayer.getRotationSpeed());
                }
            }
            // jesli obiekt ma klase bullet to przesun
            if(entityTable.get(i) instanceof Bullet){
                Bullet currentBullet = (Bullet) entityTable.get(i);

                float speedX = (float) (currentBullet.getSpeed() * Math.cos(
                        (currentBullet.getAngle())/(360/(2*Math.PI))
                ));
                float speedY = (float) (currentBullet.getSpeed() * Math.sin(
                        (currentBullet.getAngle())/(360/(2*Math.PI))
                ));

                float finalPosX = currentBullet.getPosition()[0] + speedX;
                float finalPosY = currentBullet.getPosition()[1] + speedY;
                float [] finalPos = new float[2];
                finalPos[0] = finalPosX;
                finalPos[1] = finalPosY;
                currentBullet.setPosition(finalPos);
            }

            // jesli obiekt ma klase enemy to przesun
            if (entityTable.get(i) instanceof Enemy){
                Enemy currentEnemy = (Enemy) entityTable.get(i);

                float speedX = (float) (currentEnemy.getSpeed() * Math.cos(
                        (currentEnemy.getAngle())/(360/(2*Math.PI))
                ));
                float speedY = (float) (currentEnemy.getSpeed() * Math.sin(
                        (currentEnemy.getAngle())/(360/(2*Math.PI))
                ));

                float finalPosX = currentEnemy.getPosition()[0] + speedX;
                float finalPosY = currentEnemy.getPosition()[1] + speedY;
                float [] finalPos = new float[2];
                finalPos[0] = finalPosX;
                finalPos[1] = finalPosY;
                currentEnemy.setPosition(finalPos);
            }
        }
        super.setEntityTable(entityTable);

    }
    public void CheckColisions(ArrayList<Entity> entityTable){
        ArrayList<Bullet> allybulletTable = new ArrayList<Bullet>();
        ArrayList<Bullet> enemybulletTable = new ArrayList<Bullet>();
        ArrayList<Enemy> enemyTable = new ArrayList<Enemy>();
        Player player = new Player();


        for (int i = 0; i< entityTable.size(); i++) {
            if (entityTable.get(i) instanceof Bullet){
                Bullet currentBullet = (Bullet) entityTable.get(i);
                    if(currentBullet.isAlly()){
                        allybulletTable.add(currentBullet);
                    }
                    else{
                        enemybulletTable.add(currentBullet);
                    }
                }
                if(entityTable.get(i) instanceof Enemy){
                    enemyTable.add((Enemy)entityTable.get(i));
                }

                if(entityTable.get(i) instanceof Player){
                    player = (Player) entityTable.get(i);
                }
        }

        //kolizja allyBullet/enemy
        for (int i = 0; i< allybulletTable.size(); i++){
            for (int j = 0; j < enemyTable.size(); j++) {
                Bullet currentBullet = (Bullet) allybulletTable.get(i);
                Enemy currentEnemy = (Enemy) enemyTable.get(j);
                if(Math.sqrt(Math.pow((currentEnemy.getPosition()[0]-currentBullet.getPosition()[0]),2)+
                        Math.pow((currentEnemy.getPosition()[1]-currentBullet.getPosition()[1]),2))
                        <=currentEnemy.getRadius()+currentBullet.getRadius()){
                            if(currentBullet.getHp()>currentEnemy.getHp()){
                                currentBullet.setHp(currentBullet.getHp()-currentEnemy.getHp());
                                //zabijanie enemy i array j cofniety o jeden to tylu
                                currentEnemy.setHp(0);
                            }
                            else if(currentBullet.getHp()<currentEnemy.getHp()){
                                 currentEnemy.setHp(currentEnemy.getHp()-currentBullet.getHp());
                                 //zabijanie bulleta i array i cofniety o jeden to tylu
                                 currentBullet.setHp(0);
                            }
                            else{
                                //zabijanie bulleta i array i cofniety o jeden to tylu
                                //zabijanie enemy i array j cofniety o jeden to tylu
                                currentEnemy.setHp(0);
                                currentBullet.setHp(0);
                            }
                }
            }
        }



        //kolizja enemy/player
        for (int i = 0; i< enemyTable.size(); i++){
            Enemy currentEnemy = (Enemy) enemyTable.get(i);
            if(Math.sqrt(Math.pow((currentEnemy.getPosition()[0]-player.getPosition()[0]),2)+
                    Math.pow((currentEnemy.getPosition()[1]-player.getPosition()[1]),2))
                    <=currentEnemy.getRadius()+player.getRadius()){
                if(player.getHp()>currentEnemy.getHp()){
                    player.setHp(player.getHp()-currentEnemy.getHp());
                    currentEnemy.setHp(0);
                    System.out.println(player.getHp());
                }
                else if(player.getHp()<currentEnemy.getHp()){
                    currentEnemy.setHp(currentEnemy.getHp()-player.getHp());
                    player.setHp(0);
                }
                else{
                    currentEnemy.setHp(0);
                    player.setHp(0);
                }
            }
        }




        //ograniczenie dla pocisku
        for (int i = 0; i < allybulletTable.size(); i++) {
            Bullet currentAllyBullet = (Bullet) allybulletTable.get(i);

            if(Math.sqrt(Math.pow((currentAllyBullet.getPosition()[0]),2)+
                    Math.pow((currentAllyBullet.getPosition()[1]),2))
                    >= this.getBoardRadius()){
                    //zabijanie bulleta i array i cofniety o jeden to tylu
                    currentAllyBullet.setHp(0);
            }
        }
        ArrayList<Entity> newArrayList = new ArrayList<>();

        if (player.getHp() > 0){
            newArrayList.add(player);
        }
        for (int i = 0; i < allybulletTable.size(); ++i){
            if (allybulletTable.get(i).getHp() > 0){
                newArrayList.add(allybulletTable.get(i));
            }
        }
        for (int i = 0; i < enemyTable.size(); ++i){
            if (enemyTable.get(i).getHp() > 0){
                newArrayList.add(enemyTable.get(i));
            }
        }
        for (int i = 0; i < enemybulletTable.size(); ++i){
            if (enemybulletTable.get(i).getHp() > 0){
                newArrayList.add(enemybulletTable.get(i));
            }
        }
        super.setEntityTable(newArrayList);
    }
}
//titties