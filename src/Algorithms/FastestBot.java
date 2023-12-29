package Algorithms;

import BoardController.Screen;
import Entity.*;

import java.util.List;

public class FastestBot implements Algorithm {
    private Player player;
    private List<Enemy> enemies;
    private Enemy fastestEnemy;

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
    public void Init(Screen screen) {
        this.player = Screen.getCurrentPlayer();
        this.enemies = screen.enemyList();

        fastestEnemy = findFastestEnemy(enemies);
    }

    @Override
    public byte Move() {
        //najoptymalniejsze obracanie się do wroga który dotrze najszybciej
        if (fastestEnemy == null || angleBetween(fastestEnemy) < 0.1) {
            return 0;
        } else if (mod(mod(player.getAngle(),360) - mod(fastestEnemy.getAngle()+180,360), 360) >= 180) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean TryShoot() {
        //strzelanie tylko wtedy gdy gracz namierza na jakiegokolwiek wroga
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
    public void FetchCurrentFrameInfo(boolean isShooted) {
        //wyświetlanie kąta między graczem a wrogiem który dotrze najszybciej
//        if (fastestEnemy != null) { System.out.println(angleBetween(fastestEnemy)); }
    }

}
