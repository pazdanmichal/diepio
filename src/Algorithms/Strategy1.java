package Algorithms;

import Entity.Enemy;
import Entity.Entity;
import Entity.Player;

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
                + Math.pow(entity.getPosition()[1] - player.getPosition()[1], 2))
                - entity.getRadius() - player.getRadius();

        return distance / entity.getSpeed();
    }

    private float timeToRotate(Entity entity) {
        return angleBetween(entity) / player.getRotationSpeed();
    }

    private float angleBetween(Entity entity) {
        return Math.min(Math.abs(mod(player.getAngle(),360) - mod(entity.getAngle()+180,360)), 360 - Math.abs(mod(player.getAngle(),360) - mod(entity.getAngle()+180,360)));
    }

    private float mod(float n, int k) {
        n %= k;
        if (n < 0) {
            n += k;
        }
        return n;
    }

    @Override
    public void Init(List<Enemy> enemies, Player player, long startTime) {
        this.player = player;
        this.enemies = enemies;
    }

    @Override
    public byte Move(long _time) {
        fastestEnemy = findFastestEnemy(enemies);
        if (fastestEnemy == null || angleBetween(fastestEnemy) < 0.1) {
            return 0;
        } else if (mod(mod(player.getAngle(),360) - mod(fastestEnemy.getAngle()+180,360), 360) >= 180) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean TryShoot(long _time) {
        if (fastestEnemy != null){
            for (Enemy enemy : enemies) {
                if (angleBetween(enemy) < 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void FetchCurrentFrameInfo(long _time, boolean isShooted, List<Enemy> enemies, Player player) {
        if (fastestEnemy != null) {
            System.out.println(angleBetween(fastestEnemy));
        }
    }


}
