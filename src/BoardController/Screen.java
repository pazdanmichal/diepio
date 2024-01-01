package BoardController;

import Algorithms.Algorithm;
import Entity.Entity;
import Entity.Player;
import Entity.Enemy;
import Entity.Bullet;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Screen extends BoardController {
    private int screenDimensionX;
    private int screenDimensionY;
    private long spawnedEnemies;
    private static Collider entityCollider;
    private long window;
    private long startTime;
    private long frame;
    private Random random;
    private Algorithm playerAlgorithm;
    private boolean _isSpawnedFirstWave;
    private WaveHandler waveHandler;
    private long lastframe = 0;

    public Screen(int screenDimensionX, int screenDimensionY) {
        this.screenDimensionX = screenDimensionX;
        this.screenDimensionY = screenDimensionY;
        setEnemyTable(new ArrayList<Entity>());
        setAllyBulletTable(new ArrayList<Entity>());
        setEnemyBulletTable(new ArrayList<Entity>());
    }

    public int getScreenDimensionX() {
        return screenDimensionX;
    }

    public int getScreenDimensionY() {
        return screenDimensionY;
    }

    public void setScreenDimensionX(int screenDimensionX) {
        this.screenDimensionX = screenDimensionX;
    }

    public void setScreenDimensionY(int screenDimensionY) {
        this.screenDimensionY = screenDimensionY;
    }

    public WaveHandler getWaveHandler(){ return waveHandler; }
    public long getStartTime(){ return startTime; }


    public void drawCircle(float colorA, float colorB, float colorC, float radius, float centerX, float centerY) {
        centerX += screenDimensionX / 2f;
        centerY += screenDimensionY / 2f;
        glColor3f(colorA, colorB, colorC);
        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(centerX, centerY); // Center of the circle
        int segments = 50; // Increase this for smoother circles
        double increment = 2.0 * Math.PI / segments;
        for (int i = 0; i <= segments; i++) {
            double theta = i * increment;
            float x = (float) (centerX + radius * Math.cos(theta));
            float y = (float) (centerY + radius * Math.sin(theta));
            glVertex2f(x, y);
        }
        glEnd();
    }

    public void drawLine(float colorA, float colorB, float colorC, float startX, float startY, float length, float thickness, float angle) {
        startX += screenDimensionX / 2f;
        startY += screenDimensionY / 2f;
        glColor3f(colorA, colorB, colorC);

        // Convert angle to radians
        double angleRad = Math.toRadians(angle);

        // Calculate the end point of the line based on length and angle
        float endX = startX + length * (float) Math.cos(angleRad);
        float endY = startY + length * (float) Math.sin(angleRad);

        // Calculate the perpendicular vector for thickness
        float dx = endX - startX;
        float dy = endY - startY;
        float lengthInv = 1.0f / (float) Math.sqrt(dx * dx + dy * dy);
        float perpX = -dy * lengthInv * thickness;
        float perpY = dx * lengthInv * thickness;

        // Define the vertices for the rectangle representing the line
        float[] vertices = {
                startX - perpX, startY - perpY,  // Bottom-left
                startX + perpX, startY + perpY,  // Bottom-right
                endX + perpX, endY + perpY,      // Top-right
                endX - perpX, endY - perpY       // Top-left
        };

        // Draw the rectangle representing the line
        glBegin(GL_QUADS);
        for (int i = 0; i < vertices.length; i += 2) {
            glVertex2f(vertices[i], vertices[i + 1]);
        }
        glEnd();
    }

    private void DrawHealthBar(Entity entity) {

        if (entity instanceof Player) {
            float barLength = screenDimensionX / 1.2f, barThickness = 8;

            // szara ramka
            drawLine(0.95f, 0.95f, 0.95f,
                    -barLength / 2f, screenDimensionY / 2f * 0.9f,
                    barLength, barThickness, 0);

            // zielony pasek
            float healthBarLength = barLength / entity.getMaxHp() * entity.getHp();
            drawLine(0, 1, 0,
                    -healthBarLength / 2f, screenDimensionY / 2f * 0.9f,
                    healthBarLength, barThickness, 0);
        }
        for (Entity enemy : getEnemyTable()) {
            Enemy currentEnemy = (Enemy) enemy;
            drawCircle(1, 0, 0, currentEnemy.getRadius(), currentEnemy.getPosition()[0], currentEnemy.getPosition()[1]);
        }
        Player player = getCurrentPlayer();
        drawLine(0.4f, 0.4f, 0.4f, player.getPosition()[0], player.getPosition()[1], player.getRadius() * player.getGunLengthMultiply(), player.getRadius() * player.getGunWidthMultiply(), player.getAngle());
        drawCircle(0, 0.75f, 1, player.getRadius(), player.getPosition()[0], player.getPosition()[1]);

    }

    private int textureId;

    private int loadTexture(String filePath) { //obsluga jpeg
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer image = STBImage.stbi_load(filePath, width, height, channels, 4);

        if (image == null) {
            throw new RuntimeException("Failed to load texture file: " + filePath);
        }

        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(0), height.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);

        STBImage.stbi_image_free(image);

        return textureId;
    }

    private void DrawUpgradeWindow() {
        textureId = loadTexture("src/BoardController/JPGFILES/upgrade.jpeg");
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        // Renderuj kwadrat z teksturą
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(100, 100);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(200, 100);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(200, 200);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(100, 200);
        GL11.glEnd();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    private void DrawEntities() {

        Player player = getCurrentPlayer();

        for (Entity entity : getAllyBulletTable()) {
            Bullet currentBullet = (Bullet) entity;
            drawCircle(0, 0, 0.6f, currentBullet.getRadius(), currentBullet.getPosition()[0], currentBullet.getPosition()[1]);
        }
        for (Entity entity : getEnemyBulletTable()) {
            Bullet currentBullet = (Bullet) entity;
            drawCircle(0.6f, 0, 0, currentBullet.getRadius(), currentBullet.getPosition()[0], currentBullet.getPosition()[1]);
        }
        for (Entity enemy : getEnemyTable()) {
            Enemy currentEnemy = (Enemy) enemy;
            drawCircle(1, 0, 0, currentEnemy.getRadius(), currentEnemy.getPosition()[0], currentEnemy.getPosition()[1]);
        }

        drawLine(0.4f, 0.4f, 0.4f, player.getPosition()[0], player.getPosition()[1], player.getRadius() * player.getGunLengthMultiply(), player.getRadius() * player.getGunWidthMultiply(), player.getAngle());
        drawCircle(0, 0.75f, 1, player.getRadius(), player.getPosition()[0], player.getPosition()[1]);

        DrawHealthBar(player);
        if (getCurrentPlayer().getCurrentPkt() > 0) {
            drawCircle(0.2f, 0.4f, 1, 50, 200, 0);
            DrawUpgradeWindow();
        }

    }

    private boolean PlayerAlive() {
        if (getCurrentPlayer().getHp() <= 0) {
            return false;
        }
        return true;
    }

    public static void AddEnemy(Enemy enemy) {
        entityCollider.addEntity(enemy);
    }


    private void KeyListener(Player currentPlayer) {

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            // Spacebar is pressed
            currentPlayer.Shoot(startTime, entityCollider);
        }

        if ((glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) && (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)) {
            // both A and D are pressed
            currentPlayer.setCurrentRotation((byte) 0);
        } else if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            // D is pressed
            currentPlayer.setCurrentRotation((byte) 1);
        } else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            // A is pressed
            currentPlayer.setCurrentRotation((byte) -1);
        } else if ((glfwGetKey(window, GLFW_KEY_D) == GLFW_RELEASE) && (glfwGetKey(window, GLFW_KEY_A) == GLFW_RELEASE)) {
            // nothing is pressed
            currentPlayer.setCurrentRotation((byte) 0);
        }

        if ((glfwGetKey(window, GLFW_KEY_1) == GLFW_PRESS) && frame - lastframe > 100) {
            UpgradeWindow.upgrade(1, currentPlayer); //zwiekszenie maxHP
            lastframe = frame;
        } else if ((glfwGetKey(window, GLFW_KEY_2) == GLFW_PRESS && frame - lastframe > 100)) {
            UpgradeWindow.upgrade(2, currentPlayer); //zwiekszenie attackspeed
            lastframe = frame;
        } else if ((glfwGetKey(window, GLFW_KEY_3) == GLFW_PRESS && frame - lastframe > 100)) {
            UpgradeWindow.upgrade(3, currentPlayer); //zwiekszenie damage
            lastframe = frame;
        }
    }

    private void AutonomicBotMove() {
        playerAlgorithm.Init(this);
        getCurrentPlayer().setCurrentRotation(playerAlgorithm.Move());
        boolean isShooted = false;
        if (playerAlgorithm.TryShoot()) {
            isShooted = getCurrentPlayer().Shoot(startTime, entityCollider);
        }
        playerAlgorithm.FetchCurrentFrameInfo(isShooted);
    }

    public List<Enemy> enemyList() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (Entity entity : getEnemyTable()) {
            enemies.add((Enemy) entity);
        }
        return enemies;
    }

    private void GameLoop() {
        while (!glfwWindowShouldClose(window) && PlayerAlive()) {
            frame += 1;

            entityCollider.RenderStep();
            entityCollider.CheckColisions();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glColor3f(1.0f, 1.0f, 1.0f);


            if (!waveHandler.IsRunning(enemyList())) {
                waveHandler.NextWave();
                UpgradeWindow.nextUpgrade(getCurrentPlayer());
                System.out.println("Ilość punktów ulepszeń: " + getCurrentPlayer().getCurrentPkt());
                System.out.println("Max Hp: " + getCurrentPlayer().getMaxHp());
                System.out.println("Attack Frequency: " + getCurrentPlayer().getAttackFrequency());
                System.out.println("Damage: " + getCurrentPlayer().getDamage());
                if (playerAlgorithm != null)
                    playerAlgorithm.Init(this);
            } else {
                waveHandler.TrySpawnEnemy(System.currentTimeMillis());

                if (playerAlgorithm == null) {
                    KeyListener(getCurrentPlayer());
                } else {
                    AutonomicBotMove();
                }
            }

            DrawEntities();

            // Swap the front and back buffers
            glfwSwapBuffers(window);

            // Poll for and process events
            glfwPollEvents();


        }
    }

    private void InitialScreenSettings() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(screenDimensionX, screenDimensionY, "LWJGL Circle Example", NULL, NULL);

        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - screenDimensionX) / 2, (vidMode.height() - screenDimensionY) / 2);

        glfwMakeContextCurrent(window);
        createCapabilities();

        glfwSwapInterval(1); //TODO dodaj tutaj jako zmienna
        glfwShowWindow(window);

        glOrtho(0, screenDimensionX, screenDimensionY, 0, -1, 1);

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void DestroyWindowAndExit() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public void RunGame(Algorithm _playerAlgorithm, Player currentPlayer) {

        playerAlgorithm = _playerAlgorithm;
        waveHandler = new WaveHandler(4, "TestWaves0");

        InitialScreenSettings();

        setCurrentPlayer(currentPlayer);

        startTime = System.currentTimeMillis();
        frame = 0;

        spawnedEnemies = 0;

        random = new Random();

        entityCollider = new Collider();
        // Our game
        GameLoop();

        DestroyWindowAndExit();

    }
}
