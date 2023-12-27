package BoardController;

import Algorithms.Algorithm;
import Algorithms.IdiotBot;
import Entity.Entity;
import Entity.Player;
import Entity.Enemy;
import Entity.Bullet;
import org.lwjgl.glfw.GLFWVidMode;

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
    private static long spawnedEnemies;
    private static Collider entityCollider;
    private long window;
    private long startTime;
    static long frame;
    private Random random;
    private Algorithm playerAlgorithm;
    private boolean _isSpawnedFirstWave;
    private Player currentPlayer;
    public static ArrayList<Entity> currentEntityTable;
    private WaveHandler waveHandler;

    public Screen(int screenDimensionX, int screenDimensionY) {
        this.screenDimensionX = screenDimensionX;
        this.screenDimensionY = screenDimensionY;
        this.waveHandler=new WaveHandler();
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

    public void drawCircle(float colorA, float colorB, float colorC, float radius, float centerX, float centerY) {
        centerX += screenDimensionX / 2;
        centerY += screenDimensionY / 2;
        glColor3f(colorA, colorB, colorC);
        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(centerX, centerY); // Center of the circle
        for (int i = 0; i <= 360; i++) {
            double theta = Math.toRadians(i);
            float x = (float) (centerX + radius * Math.cos(theta));
            float y = (float) (centerY + radius * Math.sin(theta));
            glVertex2f(x, y);
        }
        glEnd();
    }

    public void drawLine(float colorA, float colorB, float colorC, float startX, float startY, float length, float thickness, float angle) {
        startX += screenDimensionX / 2;
        startY += screenDimensionY / 2;
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

    private void DrawEntities() {
        for (Entity entity : currentEntityTable) {
            if (entity instanceof Bullet) {
                Bullet currentBullet = (Bullet) entity;
                if (currentBullet.isAlly()) {
                    drawCircle(0, 0, 0.6f, currentBullet.getRadius(), currentBullet.getPosition()[0], currentBullet.getPosition()[1]);
                } else {
                    drawCircle(0.6f, 0, 0, currentBullet.getRadius(), currentBullet.getPosition()[0], currentBullet.getPosition()[1]);
                }
            } else if (entity instanceof Player) {
                drawLine(0.4f, 0.4f, 0.4f, entity.getPosition()[0], entity.getPosition()[1], entity.getRadius() * ((Player) entity).getGunLengthMultiply(), entity.getRadius() * ((Player) entity).getGunWidthMultiply(), entity.getAngle());
                drawCircle(0, 0.75f, 1, entity.getRadius(), entity.getPosition()[0], entity.getPosition()[1]);
            } else if (entity instanceof Enemy) {
                drawCircle(1, 0, 0, entity.getRadius(), entity.getPosition()[0], entity.getPosition()[1]);
            }
        }
    }

    private Player findPlayer(ArrayList<Entity> currentEntityTable) {
        for (Entity entity : currentEntityTable) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
        //wyjatek -> gdy nie znajdzie gracza wyswietla game over na konsoli czy cos
    }

    private boolean Shoot(Player currentPlayer) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime > currentPlayer.getShootTime() + currentPlayer.getAttackFrequency()) {
            float[] currentPosition = currentPlayer.getPosition();
            float rotation = currentPlayer.getAngle();

            double angleRad = Math.toRadians(rotation);

            // Calculate the end point of the line based on length and angle
            float endX = currentPosition[0] + currentPlayer.getRadius() * currentPlayer.getGunLengthMultiply() * (float) Math.cos(angleRad);
            float endY = currentPosition[1] + currentPlayer.getRadius() * currentPlayer.getGunLengthMultiply() * (float) Math.sin(angleRad);
            entityCollider.addEntity(new Bullet(currentPlayer.getDamage(), currentPlayer.getRadius() * currentPlayer.getGunWidthMultiply() * 0.8f, currentPlayer.getAngle(), new float[]{endX, endY}, currentPlayer.getBulletSPeed(), true));
            currentPlayer.setShootTime(elapsedTime);
            return true;
        }
        return false;

    }

    public static void AddEnemy(Enemy enemy){
        entityCollider.addEntity(enemy);
        currentEntityTable.add(enemy);
    }


    private void KeyListener(Player currentPlayer) {

        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            // Spacebar is pressed
            Shoot(currentPlayer);
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            // D is pressed
            currentPlayer.setCurrentRotation((byte) 1);
        } else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            // A is pressed
            currentPlayer.setCurrentRotation((byte) -1);
        } else if ((glfwGetKey(window, GLFW_KEY_D) == GLFW_RELEASE) && (glfwGetKey(window, GLFW_KEY_A) == GLFW_RELEASE)) {
            // nothing is pressed
            currentPlayer.setCurrentRotation((byte) 0);
        }

    }

    private void AutonomicBotMove() {
        long currentTime = System.currentTimeMillis();
        currentPlayer.setCurrentRotation(playerAlgorithm.Move(currentTime));
        boolean _isShooted = false;
        if (playerAlgorithm.TryShoot(currentTime)) {
            _isShooted = Shoot(currentPlayer);
        }
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (Entity entity : currentEntityTable) {
            if (entity instanceof Enemy) {
                enemies.add((Enemy) entity);
            }
        }
        playerAlgorithm.FetchCurrentFrameInfo(currentTime, _isShooted, enemies, currentPlayer);
    }

    private List<Enemy> enemyList() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        for (Entity entity : currentEntityTable) {
            if (entity instanceof Enemy) enemies.add((Enemy) entity);
        }
        return enemies;
    }

    private void GameLoop() {
        while (!glfwWindowShouldClose(window) && currentPlayer != null) {
            frame += 1;

            entityCollider.RenderStep(entityCollider.getEntityTable());
            entityCollider.CheckColisions(entityCollider.getEntityTable());

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glColor3f(1.0f, 1.0f, 1.0f);

            currentEntityTable = entityCollider.getEntityTable();
            currentPlayer = findPlayer(currentEntityTable);
            if (currentPlayer == null) {
                return;
            }

            if(!waveHandler.IsRunning(enemyList())){
                waveHandler.NewWave(2);
                if(playerAlgorithm != null) playerAlgorithm.Init(enemyList(), currentPlayer, System.currentTimeMillis());
            }
            else {
                waveHandler.TrySpawnEnemy(System.currentTimeMillis());
                if (playerAlgorithm == null) {
                    KeyListener(currentPlayer);
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


        private void InitialScreenSettings () {
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

        private void DestroyWindowAndExit () {
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
            glfwTerminate();
        }

        public void RunGame (Algorithm _playerAlgorithm){

            playerAlgorithm = _playerAlgorithm;
            waveHandler = new WaveHandler();

            InitialScreenSettings();

            entityCollider = new Collider();
            entityCollider.setEntityTable(this.getEntityTable());

            currentPlayer = findPlayer(entityCollider.getEntityTable());

            startTime = System.currentTimeMillis();
            frame = 0;

            spawnedEnemies = 0;

            _isSpawnedFirstWave = false;
            random = new Random();

            // Our game
            GameLoop();

            DestroyWindowAndExit();

        }
    }

