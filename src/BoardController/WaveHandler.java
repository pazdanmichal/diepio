package BoardController;

import Entity.Enemy;
import java.util.AbstractMap.SimpleEntry;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WaveHandler {

    private static long startTime;
    private static List<SimpleEntry<Enemy, Long>> enemiesToSpawn;
    private static int numberOfWaves;
    private static int currentWave = 0;
    private static String wavePackageName;

    public WaveHandler(int numberOfWaves, String wavePackageName) {
        WaveHandler.numberOfWaves = numberOfWaves;
        WaveHandler.wavePackageName = wavePackageName;
    }

    public static int getCurrentWave() {
        return currentWave;
    }

    private static void sort() {
        enemiesToSpawn.sort(Comparator.comparingLong(SimpleEntry::getValue));
    }

    public void readEnemiesFromFile(String filename) {
        enemiesToSpawn = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int numEnemies = Integer.parseInt(br.readLine());
            for (int i = 0; i < numEnemies; i++) {
                String[] enemyData = br.readLine().split(" ");
                float movementSpeed = Float.parseFloat(enemyData[0]);
                float radius = Float.parseFloat(enemyData[1]);
                int hp = Integer.parseInt(enemyData[2]);
                long delay = Long.parseLong(enemyData[3]);
                Enemy enemy = CreateEnemy(movementSpeed, radius, hp);
                enemiesToSpawn.add(new SimpleEntry<Enemy, Long>(enemy, delay));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void NextWave() {
        readEnemiesFromFile("src/Waves/" + wavePackageName + "/wave" + currentWave + ".txt");
        System.out.println("Wave " + currentWave + " from package " + wavePackageName + " has been started!");

        currentWave = (currentWave + 1) % numberOfWaves;
        sort();

        startTime = System.currentTimeMillis();

    }

    public void TrySpawnEnemy(long _time /*Collider entityCollider*/) {
        if (!enemiesToSpawn.isEmpty()) {
            if (_time - startTime >= enemiesToSpawn.get(0).getValue()) {
                Collider.addEntity(enemiesToSpawn.get(0).getKey());
                enemiesToSpawn.remove(0);
            }
        }
    }

    public Enemy CreateEnemy(float movementSpeed, float radius, int hp) {
        /*spawnedEnemies += 1;*/
        Random random = new Random();
        float angle = random.nextFloat() * 2 * (float) Math.PI;
        float x = 700 * (float) Math.cos(angle);
        float y = 700 * (float) Math.sin(angle);
        angle = (360 * angle) / (2 * (float) Math.PI);
        angle += 180;
        return new Enemy(hp, radius, angle, new float[]{x, y}, movementSpeed, false, 0, 0);
    }

    public boolean IsRunning(List<Enemy> enemies) {
        if (enemiesToSpawn == null) return false;
        return !enemies.isEmpty() || !enemiesToSpawn.isEmpty();
    }
}