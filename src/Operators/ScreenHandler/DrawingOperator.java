package Operators.ScreenHandler;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import Entity.Entity;
import org.lwjgl.opengl.GL11;
import Entity.Player;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/*

 Klasa DrawingOperator zawiera wszystkie metody odpowiadające za rysowanie
        kształtów dynamicznych (takich jak przeciwnik, pocisk i gracz)
        i statycznych (pasek życia, pasek ulepszeń)
        oraz renderowanie obrazów z pakietu Images

  Jest ona podzielona na kategorie w zależności od "genezy" elementów generowanych przez metody

 */

public class DrawingOperator {

    //------------------------------// SKŁADOWE I KONSTRUKTOR \\---------------------------------\\

    private final int screenSize;
    private final ArrayList<Integer> textureIds;

    // Konstruktor
    public DrawingOperator(int screenSize) {

        this.screenSize = screenSize;
        textureIds = new ArrayList<>();

        // Dorzucając kolejne ścieżki ZAWSZE NA SAM DÓŁ
        String[] textureNames = new String[] {

                "src/Images/upgrade.jpeg",      // 0
                "src/Images/rycerz.png",        // 1
                "src/Images/tlo.png",           // 2
                "src/Images/dragon.png",        // 3
                "src/Images/rycerz2.png",       // 4
                "src/Images/serce.png",         // 5
                "src/Images/luk.png",           // 6
                "src/Images/miecz.png",         // 7
                "src/Images/fireball.png"       // 8

        };

        for (String textureName : textureNames) {
            int texture = ImageToId(textureName);
            textureIds.add(texture);
        }
    }

    //---------------------------------------// METODY \\-----------------------------------------\\

    //----------- metoda addTexture - konwersja ciągu String (ścieżki obrazka) na unikalne id obrazu

    public int ImageToId(String filePath) {

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

        // Dodajemy ID obrazka do zbioru wszystkich ID obrazków (ArrayListy)
        // i korzystamy z tych ID za pomocą indeksu który podajemy jako wskaźnik

        return textureId;

    }

    //----------- RYSOWANIE ELEMENTÓW DYNAMICZNYCH (metody "draw") ---------------------

    public void drawLine(float colorA, float colorB, float colorC, float startX, float startY, float length, float thickness, float angle) {
        startX += screenSize / 2f;
        startY += screenSize / 2f;
        glColor3f(colorA, colorB, colorC);

        // Zamiana ze stopni na radiany
        double angleRad = Math.toRadians(angle);

        // Obliczanie punktu na koncu linii na podstawie wspolrzednych poczatkowych i dlugosci
        float endX = startX + length * (float) Math.cos(angleRad);
        float endY = startY + length * (float) Math.sin(angleRad);

        // Obliczanie wektora prostopadlego, w celu obliczenia grubosci linii
        float dx = endX - startX;
        float dy = endY - startY;
        float lengthInv = 1.0f / (float) Math.sqrt(dx * dx + dy * dy);
        float perpX = -dy * lengthInv * thickness;
        float perpY = dx * lengthInv * thickness;

        // Zdefiniowanie wierzcholkow prostokata
        float[] vertices = {
                startX - perpX, startY - perpY,  // Lewy dolny
                startX + perpX, startY + perpY,  // Prawy dolny
                endX + perpX, endY + perpY,      // Prawy gorny
                endX - perpX, endY - perpY       // Lewy gorny
        };

        // Rysowanie prostokatu
        glBegin(GL_QUADS);
        for (int i = 0; i < vertices.length; i += 2) {
            glVertex2f(vertices[i], vertices[i + 1]);
        }
        glEnd();
    }

    public void drawCircle(float colorA, float colorB, float colorC, float radius, float centerX, float centerY) {
        centerX += screenSize / 2f;
        centerY += screenSize / 2f;
        glColor3f(colorA, colorB, colorC);
        glBegin(GL_POLYGON);
        int segments = 50; // Zwieksz to, aby okrag byl bardziej gladki
        double increment = 2.0 * Math.PI / segments;
        for (int i = 0; i <= segments; i++) {
            double theta = i * increment;
            float x = (float) (centerX + radius * Math.cos(theta));
            float y = (float) (centerY + radius * Math.sin(theta));
            glVertex2f(x, y);
        }
        glEnd();
    }

    public void drawParallelogram(float a1, float b1, float a2, float b2, float a3, float b3, float a4, float b4, float colorA, float colorB, float colorC, float transparency){
        //GL11.glColor3f(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(colorA, colorB, colorC, transparency);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(a1, b1);
        GL11.glVertex2f(a2, b2);
        GL11.glVertex2f(a3, b3);
        GL11.glVertex2f(a4, b4);
        GL11.glEnd();
    }

    //----------- RYSOWANIE ELEMENTÓW STATYCZNYCH (metody "draw") ----------------------

    public void drawHealthBar(Entity entity) {

        if (entity instanceof Player) {
            float barLength = screenSize / 1.2f, barThickness = 8;

            // Rysowannie szarej ramki
            drawLine(0.95f, 0.95f, 0.95f,
                    -barLength / 2f, screenSize / 2f * 0.9f,
                    barLength, barThickness, 0);

            // Rysowanie zielonego paska
            float healthBarLength = barLength / entity.getMaxHp() * entity.getHp();
            drawLine(0, 1, 0,
                    -healthBarLength / 2f, screenSize / 2f * 0.9f,
                    healthBarLength, barThickness, 0);
        }
    }

    public void drawUpgradeBar(float startPosX, float startPosY, float cellSizeX, float cellSizeY, float spaceBetweenCells, float numberOfCells, Player currentPlayer, boolean ready){

        for (int i = 0; i < 6; ++i) {
            float base = startPosX + i * (cellSizeX + spaceBetweenCells);
            for (int j = 0; j < 3; j++) {

                // Rysowanie ikonek
                if (i == 0) {

                    int iconId = textureIds.get(j+4);

                    float cellSizeX2 = cellSizeX - 35;
                    float[][] pos = new float[][]{
                        {base - cellSizeX2 + 8, startPosY - cellSizeX2 + 10},
                        {base + cellSizeX2 + 8, startPosY - cellSizeX2 + 10},
                            {base + cellSizeX2 + 8, startPosY + cellSizeX2 + 10},                  // A2 B2
                            {base - cellSizeX2 + 8, startPosY + cellSizeX2 + 10}                   // A1 B1
                    };

                    renderImage(pos, iconId);


                    // Rysowanie parrarelogramu
                } else {
                    if(ready){
                        drawParallelogram(
                                base- 3, startPosY-3,
                                base - cellSizeY -8,startPosY + cellSizeY+3,
                                base - cellSizeY + cellSizeX+3, startPosY + cellSizeY+3,
                                base + cellSizeX +8, startPosY-3,
                                1,0,1,1
                    );}
                    else{
                        drawParallelogram(
                                    base- 3, startPosY-3,
                                    base - cellSizeY -8,startPosY + cellSizeY+3,
                                    base - cellSizeY + cellSizeX+3, startPosY + cellSizeY+3,
                                    base + cellSizeX +8, startPosY-3,
                                    0,0,0,1
                            );
                        }

                    drawParallelogram(
                            base, startPosY,
                            base - cellSizeY, startPosY + cellSizeY,
                            base - cellSizeY + cellSizeX, startPosY + cellSizeY,
                            base + cellSizeX, startPosY,
                            1,1,1,1
                    );

                    if(j==0){
                        if((currentPlayer.getMaxHp()-10)/ currentPlayer.getMaxHpDiff()>=i){
                            drawParallelogram(
                                    base, startPosY,
                                    base - cellSizeY, startPosY + cellSizeY,
                                    base - cellSizeY + cellSizeX, startPosY + cellSizeY,
                                    base + cellSizeX, startPosY,
                                    0,1,1,1
                            );
                        }
                    }
                    else if(j==1){
                        if((300-currentPlayer.getAttackFrequency())/ currentPlayer.getAttackFrequencyDiff()>=i){
                            drawParallelogram(
                                    base, startPosY,
                                    base - cellSizeY, startPosY + cellSizeY,
                                    base - cellSizeY + cellSizeX, startPosY + cellSizeY,
                                    base + cellSizeX, startPosY,
                                    0,1,1,1
                            );
                        }
                    }
                    else{
                        if((currentPlayer.getDamage()- currentPlayer.getDmgDiff())>=i){
                            drawParallelogram(
                                    base, startPosY,
                                    base - cellSizeY, startPosY + cellSizeY,
                                    base - cellSizeY + cellSizeX, startPosY + cellSizeY,
                                    base + cellSizeX, startPosY,
                                    0,1,1,1
                            );
                        }
                    }
                }


                startPosY += 32;
            }
            startPosY -= 96;
        }
    }

    //----------- RENDEROWANIE OBRAZÓW -------------------------------------------------

    public void renderImage(float[][] pos, int textureIdIndex) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIds.get(textureIdIndex));

        // Renderuj kwadrat z teksturą                  // pos - [ [x,y], [x,y], [x,y], [x,y] ]
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(1,1,1);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(pos[0][0], pos[0][1]);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(pos[1][0], pos[1][1]);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(pos[2][0], pos[2][1]);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(pos[3][0], pos[3][1]);
        GL11.glEnd();
        //
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public float[][] posCalc(float[] pos1, float radius, float angle){
        float[][] pos2 = new float[4][2];

        float przekatna = radius/(float)Math.sqrt(2);
        float wymiar = (float)screenSize/2;

        pos2[3][0] = pos1[0] - przekatna + wymiar;
        pos2[3][1] = pos1[1] + przekatna + wymiar;
        pos2[2][0] = pos1[0] + przekatna + wymiar;
        pos2[2][1] = pos1[1] + przekatna + wymiar;
        pos2[1][0] = pos1[0] + przekatna + wymiar;
        pos2[1][1] = pos1[1] - przekatna + wymiar;
        pos2[0][0] = pos1[0] - przekatna + wymiar;
        pos2[0][1] = pos1[1] - przekatna + wymiar;

        float radians = (float) Math.toRadians(angle);
        float cosA = (float) Math.cos(radians+(Math.PI/2));
        float sinA = (float) Math.sin(radians+(Math.PI/2));

        for (int i = 0; i < 4; i++) {
            float x = pos2[i][0] - (pos1[0] + wymiar);
            float y = pos2[i][1] - (pos1[1] + wymiar);

            // Zastosowanie macierzy obrotu
            pos2[i][0] = x * cosA - y * sinA + (pos1[0] + wymiar);
            pos2[i][1] = x * sinA + y * cosA + (pos1[1] + wymiar);
        }

        return pos2;
    }
    /*

        // najpierw dajecie wspolrzedne gdzie sie ten pasek zaczyna (100,100)
        // potem dlugosc jednego rownloegloboku (50)
        // potem jego wysokosc (20)
        // potem odstep miedzy kolejnymi (5)
        // potem ilosc ich w pasku
        drawUpgradeBar(100,100, 50,20,5,3);

     */





    }

