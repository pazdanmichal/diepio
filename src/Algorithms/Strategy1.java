package Algorithms;

import Entity.Enemy;
import Entity.Entity;
import Entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Strategy1 implements Algorithm {
    private Enemy fastestEnemy;
    private Player player;
    private List<Enemy> enemies;

    private Enemy findFastestEnemy(List<Enemy> enemies) {
        if (enemies.isEmpty()) {
            return null;
        }

        float minTimeToPlayer = Float.MAX_VALUE;

        for (Enemy enemy : enemies) {
            if (timeToPlayer(enemy) < minTimeToPlayer && timeToPlayer(enemy) >= timeToRotate(enemy)) {
                minTimeToPlayer = timeToPlayer(enemy);
                fastestEnemy = enemy;
            }
        }

        return fastestEnemy;
    }

    private float timeToPlayer(Entity entity) {
        float distance = (float) Math.sqrt(Math.pow(entity.getPosition()[0] - player.getPosition()[0], 2)
                + Math.pow(entity.getPosition()[1] - player.getPosition()[1], 2));
        return distance / entity.getSpeed();
    }

    private float timeToRotate(Entity entity) {
        return angleBetween(entity) / player.getRotationSpeed();
    }

    private float angleBetween(Entity entity) {
        return Math.min(Math.abs(player.getAngle() - entity.getAngle()), 360 - Math.abs(player.getAngle() - entity.getAngle()));
    }

    @Override
    public void Init(List<Enemy> enemies, Player player, long startTime) {
        this.player = player;
        this.enemies = enemies;
    }

    @Override
    public byte Move(long _time) {
        fastestEnemy = findFastestEnemy(enemies);
        if (fastestEnemy == null) { //|| angleBetween(fastestEnemy) < 0.1
            return 0;
        } else if ((player.getAngle() - fastestEnemy.getAngle()) % 360 <= 180) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean TryShoot(long _time) {
//        if (fastestEnemy == null || angleBetween(fastestEnemy) > 3){
//            return false;
//        } else {
//            return true;
//        }
        return true;
    }

    @Override
    public void FetchCurrentFrameInfo(long _time, boolean isShooted, List<Enemy> enemies, Player player) {
        if (fastestEnemy != null) {
            System.out.println(angleBetween(fastestEnemy));
        }
    }


}
