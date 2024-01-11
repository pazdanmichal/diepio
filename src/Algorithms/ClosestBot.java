package Algorithms;

import Operators.ScreenHandler.ScreenOperator;
import Entity.*;

import java.util.List;

import static Algorithms.Helper.mod360;

public class ClosestBot implements Algorithm {
    private Player player;
    private List<Enemy> enemies;
    private Enemy closestEnemy;

    @Override
    public void Init() {
        this.player = ScreenOperator.getCurrentPlayer();
        this.enemies = ScreenOperator.enemyList();
        closestEnemy = findClosest();
    }

    protected float angleBetween(Entity entity) {
        return Math.min(Math.abs(mod360(player.getAngle()) - mod360(entity.getAngle() + 180)), 360 - Math.abs(mod360(player.getAngle()) - mod360(entity.getAngle() + 180)));
    }

    private Enemy findClosest() {
        if (enemies.isEmpty()) {
            return null;
        }
        float minAngle = Float.MAX_VALUE;
        for (Enemy enemy : enemies) {
            if (angleBetween(enemy) < minAngle) {
                minAngle = angleBetween(enemy);
                closestEnemy = enemy;
            }

        }
        return closestEnemy;
    }

    @Override
    public byte Move() {
        if (closestEnemy == null || angleBetween(closestEnemy) < 0.1) {
            return 0;
        } else if (mod360(mod360(player.getAngle()) - mod360(closestEnemy.getAngle() + 180)) >= 180) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean TryShoot() {
        if (closestEnemy != null) {
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
    }
}
