package BoardController;

import Entity.Enemy;
import Entity.Entity;
import java.util.AbstractMap.SimpleEntry;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WaveHandler {

    private long startTime;
    private List<SimpleEntry<Enemy, Long>> enemiesToSpawn;
    private int numberOfWaves;
    private int currentWave = 0;
    private String wavePackageName;

    public WaveHandler(int numberOfWaves, String wavePackageName){
        this.numberOfWaves = numberOfWaves;
        this.wavePackageName = wavePackageName;
    }

    private void sort(){
        Collections.sort(enemiesToSpawn, Comparator.comparingLong(SimpleEntry::getValue));
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
                Enemy enemy = CreateEnemy(movementSpeed,radius,hp);
                enemiesToSpawn.add(new SimpleEntry<Enemy, Long>(enemy, delay));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void NextWave(){
        readEnemiesFromFile("src/Waves/" + wavePackageName + "/wave" + currentWave + ".txt");
        System.out.println("Wave " + currentWave + " from package " + wavePackageName + " has been started!");
        int nextWaveIndex = (currentWave+1)%numberOfWaves;
        currentWave = nextWaveIndex;
        sort();

        this.startTime = System.currentTimeMillis();


        /*
        Random random=new Random();
        /* SpawnWave(random.nextInt(1 + (int) (frame / 1000), 3 + (int) (frame / 1000)),0.5f,20,1);
        int amountOfEnemies = random.nextInt(1 + (int) (frame / 1000), 3 + (int) (frame / 1000));
        float movementSpeed = 5f;
        float radius = 20;
        int hp = 1;
        ////////////
        for (int i = 0; i < amountOfEnemies; ++i) {
            CreateEnemy(movementSpeed, radius, hp);
        }
        /*amount of enemies random.nextInt(1 + (int) (frame / 1000), 3 + (int) (frame / 1000))*/
    }

    public void TrySpawnEnemy(long _time) {
        if (!enemiesToSpawn.isEmpty()) {
            if (_time - startTime >= enemiesToSpawn.get(0).getValue()) {
                Screen.AddEnemy(enemiesToSpawn.get(0).getKey());
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
        Enemy enemy = new Enemy(hp, radius, angle, new float[]{x, y}, movementSpeed, false, 0, 0);
        return enemy;
    }

    public boolean IsRunning(List<Enemy> enemies) {
        if(enemiesToSpawn == null) return false;
        if (enemies.isEmpty() && enemiesToSpawn.isEmpty()) return false;
        return true;
    }
}
