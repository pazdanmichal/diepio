package Algorithms;

import Operators.ScreenHandler.ScreenOperator;
import BoardController.WaveHandler;
import Entity.*;

import java.util.List;

import static Algorithms.Helper.mod360;

public class GroupBot implements Algorithm {
    private Player player;
    private List<Enemy> enemies;
    private int[] enemiesCountSection;
    private int maxSection = 0;
    private int waveNr = -1;
    private FastestBot fastestBot = new FastestBot();
    private Enemy fastestEnemySection;

    private Enemy findFastestEnemySection(List<Enemy> enemies, int maxSection) {
        if (enemies.isEmpty()) {
            return null;
        }

        float minTimeToPlayer = Float.MAX_VALUE;

        for (Enemy enemy : enemies) {
            if (mod360(enemy.getAngle() + 180) >= maxSection * (360 / 8) && mod360(enemy.getAngle() + 180) <= maxSection * (360 / 8) + (360 / 8)) {
                if (fastestBot.timeToPlayer(enemy) < minTimeToPlayer && fastestBot.timeToPlayer(enemy) >= fastestBot.timeToRotate(enemy)) {
                    minTimeToPlayer = fastestBot.timeToPlayer(enemy);
                    fastestEnemySection = enemy;
                }
            }
        }

        return fastestEnemySection;
    }

    protected float timeToRotateSection(int section) {
        return angleBetweenSection(section) / player.getRotationSpeed();
    }

    protected float angleBetweenSection(int section) {
        return Math.min(Math.abs(mod360(player.getAngle()) - section * (360 / 8) + ((360 / 8) / 2)), 360 - Math.abs(mod360(player.getAngle()) - section * (360 / 8) + ((360 / 8) / 2)));
    }

    @Override
    public void Init() {
        this.player = ScreenOperator.getCurrentPlayer();
        this.enemies = ScreenOperator.enemyList();
        fastestBot.Init();

        if (!enemies.isEmpty()) {
            enemiesCountSection = new int[8];
            for (Enemy enemy : enemies) {
                enemiesCountSection[(int) mod360(enemy.getAngle() + 180) / (360 / 8)] += 1;
            }

            if (WaveHandler.getCurrentWave() != waveNr || enemiesCountSection[maxSection] == 0) {
                waveNr = WaveHandler.getCurrentWave();

                maxSection = 0;
                for (int i = 1; i < enemiesCountSection.length; i++) {
                    if (enemiesCountSection[i] > enemiesCountSection[maxSection]) {
                        maxSection = i;
                    } else if (enemiesCountSection[i] == enemiesCountSection[maxSection]) {
                        if (timeToRotateSection(i) < timeToRotateSection(maxSection)) {
                            maxSection = i;
                        }
                    }
                }
            }
        }


        this.fastestEnemySection = findFastestEnemySection(enemies, maxSection);
    }

    @Override
    public byte Move() {
        if (fastestEnemySection == null || fastestBot.angleBetween(fastestEnemySection) < 0.1) {
            return 0;
        } else if (mod360(mod360(player.getAngle()) - mod360(fastestEnemySection.getAngle() + 180)) >= 180) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean TryShoot() {
        if (!enemies.isEmpty()) {
            for (Enemy enemy : enemies) {
                if (fastestBot.angleBetween(enemy) < 1) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void FetchCurrentFrameInfo(boolean isShooted) {
//        System.out.println("Część: " + maxSection * (360 / 8) + "° - " + (maxSection * (360 / 8) + (360 / 8)) + "°");
    }

}
