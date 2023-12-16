package BoardController;

import Entity.Entity;
import Entity.Player;
import Entity.Enemy;
import Entity.Bullet;
import org.lwjgl.glfw.GLFWVidMode;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Screen extends BoardController{
    private int screenDimensionX;
    private int screenDimensionY;
    private long spawnedEnemies;
    private Collider entityCollider;
    private long window;
    private boolean playerFound;
    private long startTime;
    private long frame;
    private Random random;

    public Screen(int screenDimensionX, int screenDimensionY){
        this.screenDimensionX = screenDimensionX;
        this.screenDimensionY = screenDimensionY;
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
    public void drawCircle(float colorA, float colorB, float colorC, float radius, float centerX, float centerY){
        centerX += screenDimensionX/2;
        centerY += screenDimensionY/2;
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
        startX += screenDimensionX/2;
        startY += screenDimensionY/2;
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

    private Player findPlayer(ArrayList<Entity> currentEntityTable){
        for (Entity entity : currentEntityTable){
            if (entity instanceof Player){
                return (Player) entity;
            }
        }
        return null;
    }

    private void Shoot(Player currentPlayer, long startTime){
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime > currentPlayer.getShootTime() + currentPlayer.getAttackFrequency()){
            float[] currentPosition = currentPlayer.getPosition();
            float rotation = currentPlayer.getAngle();

            double angleRad = Math.toRadians(rotation);

            // Calculate the end point of the line based on length and angle
            float endX = currentPosition[0] + currentPlayer.getRadius()*currentPlayer.getGunLengthMultiply() * (float) Math.cos(angleRad);
            float endY = currentPosition[1] + currentPlayer.getRadius()*currentPlayer.getGunLengthMultiply() * (float) Math.sin(angleRad);
            entityCollider.addEntity(new Bullet(currentPlayer.getDamage(), currentPlayer.getRadius() * currentPlayer.getGunWidthMultiply() * 0.8f, currentPlayer.getAngle(), new float[] {endX, endY}, currentPlayer.getBulletSPeed(), true));
            currentPlayer.setShootTime(elapsedTime);
        }
    }

    private void SpawnEnemy(){
        spawnedEnemies += 1;
        float angle = random.nextFloat() * 2 * (float) Math.PI;
        float x = 700 * (float) Math.cos(angle);
        float y = 700 * (float) Math.sin(angle);
        angle = (360*angle)/(2*(float)Math.PI);
        angle += 180;
        entityCollider.addEntity(new Enemy(1, 20, angle, new float[] {x, y}, 0.5f, false, 0, 0));
    }

    private void SpawnWave(long amountOfEnemies){
        for (int i = 0; i<amountOfEnemies; ++i){
            SpawnEnemy();
        }
    }

    private void KeyListener(Player currentPlayer){
        /*if (glfwGetKey(window, GLFW_KEY_V) == GLFW_PRESS) {
            // if V is pressed spawn wave
            SpawnWave(20);
        }*/

        // Keyboard listening <- przydalaby sie osobna funkcja
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            // Spacebar is pressed
            Shoot(currentPlayer, startTime);
        }

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            // D is pressed
            currentPlayer.setCurrentRotation((byte)1);
        } else if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            // A is pressed
            currentPlayer.setCurrentRotation((byte)-1);
        } else if ((glfwGetKey(window, GLFW_KEY_D) == GLFW_RELEASE)&&(glfwGetKey(window, GLFW_KEY_A) == GLFW_RELEASE)) {
            // nothing is pressed
            currentPlayer.setCurrentRotation((byte)0);
        }
    }

    private void GameLoop(){
        while (!glfwWindowShouldClose(window) && playerFound) {
            frame += 1;

            entityCollider.RenderStep(entityCollider.getEntityTable());
            entityCollider.CheckColisions(entityCollider.getEntityTable());

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glColor3f(1.0f, 1.0f, 1.0f);


            ArrayList <Entity> currentEntityTable = entityCollider.getEntityTable();
            Player currentPlayer = findPlayer(currentEntityTable);

            // Dosyc fajne skalowanie fali
            random = new Random();
            if(frame%300 == 0){
                SpawnWave((long)random.nextInt(1+(int)(frame/1000), 3+(int)(frame/1000)));
            }
            KeyListener(currentPlayer);

            // Draw bullets and other entities
            for (int i = 0; i<currentEntityTable.size(); i++){
                if (currentEntityTable.get(i) instanceof Bullet) {
                    Bullet currentBullet = (Bullet) currentEntityTable.get(i);
                    if (currentBullet.isAlly()){
                        drawCircle(0, 0, 0.6f, currentEntityTable.get(i).getRadius(), currentEntityTable.get(i).getPosition()[0], currentEntityTable.get(i).getPosition()[1]);
                    } else {
                        drawCircle(0.6f, 0, 0, currentEntityTable.get(i).getRadius(), currentEntityTable.get(i).getPosition()[0], currentEntityTable.get(i).getPosition()[1]);
                    }
                }
            }

            playerFound = false;
            for (int i = 0; i<currentEntityTable.size(); i++) {

                if (currentEntityTable.get(i) instanceof Player) {
                    playerFound = true;
                    drawLine(0.4f, 0.4f, 0.4f, currentEntityTable.get(i).getPosition()[0], currentEntityTable.get(i).getPosition()[1], currentEntityTable.get(i).getRadius() * ((Player) currentEntityTable.get(i)).getGunLengthMultiply(), currentEntityTable.get(i).getRadius() * ((Player) currentEntityTable.get(i)).getGunWidthMultiply(), currentEntityTable.get(i).getAngle());
                    drawCircle(0, 0.75f, 1, currentEntityTable.get(i).getRadius(), currentEntityTable.get(i).getPosition()[0], currentEntityTable.get(i).getPosition()[1]);

                } else if (currentEntityTable.get(i) instanceof Enemy) {
                    drawCircle(1, 0, 0, currentEntityTable.get(i).getRadius(), currentEntityTable.get(i).getPosition()[0], currentEntityTable.get(i).getPosition()[1]);
                }
            }

            // Swap the front and back buffers
            glfwSwapBuffers(window);

            // Poll for and process events
            glfwPollEvents();
        }
    }
    public void RunGame(){

        //tutaj inicjalizacja
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(screenDimensionX,screenDimensionY, "LWJGL Circle Example", NULL, NULL);

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

        entityCollider = new Collider();
        entityCollider.setEntityTable(this.getEntityTable());

        playerFound = true;

        startTime = System.currentTimeMillis();
        frame = 0;

        spawnedEnemies = 0;

        // Our game
        GameLoop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}
