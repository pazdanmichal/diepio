package Entity;

import java.util.Arrays;

public abstract class Entity {
    private int maxHp;
    private int hp; //domyslnie 1 dla poczatkowej wersji gry
    private float radius; //typ zmiennej zalezny od skali osi wspolrzednych (do ustalenia)
    private float angle; //kat obrotu jako wartosc zmienno-przecinkowa - wieksza dokladnosc
    private float[] position = new float[2]; //dwie wartosci x i y oznaczajace pozycje
    private float movementSpeed; //poniewaz enemy i bullet ma, a u pleyera domyslne bd 0

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public Entity(int hp, float radius, float angle, float[] position, float movementSpeed) {
        this.maxHp = hp;
        this.hp = hp;
        this.radius = radius;
        this.angle = angle;
        this.position = position;
        this.movementSpeed = movementSpeed;
    }
    public Entity() {
        this.hp = 0;
        this.radius = 0;
        this.angle = 0;
        this.position = new float [] {0,0};
        this.movementSpeed = 0;
    }

    public int getHp() {
        return hp;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    //mozemy zrobic tak, ze po otrzymaniu damageu enemy bedzie zmniejszal swoj
    //promien, co bedzie prostym sposobem na przedstawienie ile zostalo mu hp

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float[] getPosition() {
        return position;
    }

    public void setPosition(float[] position) {
        this.position = position;
    }

    public void setHp(int hp) {
        // if entity died, execute OnDie function
        // if damage was dealt, execute OnHit function
        if (hp <= 0){
            OnDie();
        } else if (hp < this.hp){
            OnHit();
        }
        // set hp
        this.hp = hp;
    }

    public float getSpeed() {
        return movementSpeed;
    }

    public void setSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void OnHit(){
        //System.out.println("entity was hit");
    };

    public void OnDie(){
        //System.out.println("entity died");
    };

    @Override
    public String toString() {
        return "hp = " + hp +
                "\nradius = " + radius +
                "\nangle = " + angle +
                "\nposition = " + Arrays.toString(position) +
                "\nmovementSpeed = " + movementSpeed;
    }
}
