package Entity;

import BoardController.Collider;
import Operators.*;

public class Player extends Mob {
    private byte currentRotation;//-1 -> lewo; 0 -> stoi; 1 -> prawo
    private float rotationSpeed;
    private int maxHp;
    private float gunWidthMultiply;
    private float gunLengthMultiply;
    private int bulletSpeed;
    private int currentPkt;
    private int maxHpDiff;
    private float attackFrequencyDiff;
    private int dmgDiff;



    public Player(int hp, float radius, float angle, float[] position, float movementSpeed, boolean canShoot,
                  float attackFrequency, int damage, byte currentRotation, float rotationSpeed, float gunLengthMultiply, float gunWidthMultiply, int bulletSpeed, int maxHp, int currentPkt, int maxHpDiff, float attackFrequencyDiff, int dmgDiff) {
        super(hp, radius, angle, position, movementSpeed, canShoot, attackFrequency, damage);
        this.currentRotation = currentRotation;
        this.rotationSpeed = rotationSpeed;
        this.gunLengthMultiply = gunLengthMultiply;
        this.gunWidthMultiply = gunWidthMultiply;
        this.bulletSpeed = bulletSpeed;
        this.maxHp = maxHp;
        this.currentPkt = currentPkt;
        this.maxHpDiff=maxHpDiff;
        this.attackFrequencyDiff=attackFrequencyDiff;
        this.dmgDiff=dmgDiff;
    }

    public Player() {
        super();
        this.currentRotation = 0;
        this.rotationSpeed = 0;
    }

    public int getCurrentPkt() {
        return currentPkt;
    }

    public void setCurrentPkt(int currentPkt) {
        this.currentPkt = currentPkt;
    }

    public byte getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(byte currentRotation) {
        this.currentRotation = currentRotation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    //mozliwa zmiana, np. zmiana statystyk w trakcie gry (lvl up)
    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void Die() {
        //przyslonieta metoda dla klasy player -> zakonczenie rozgrywki
    }

    public boolean Shoot(long startTime) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime > getShootTime() + getAttackFrequency()) {
            SoundOperator.playSoundOnThread("src/Sounds/fireball.wav", false, -30.0f);
            float[] currentPosition = getPosition();
            float rotation = getAngle();

            double angleRad = Math.toRadians(rotation);

            // Calculate the end point of the line based on length and angle
            float endX = currentPosition[0] + getRadius() * getGunLengthMultiply() * (float) Math.cos(angleRad);
            float endY = currentPosition[1] + getRadius() * getGunLengthMultiply() * (float) Math.sin(angleRad);
            Collider.addEntity(new Bullet(getDamage(), getRadius() * getGunWidthMultiply() * 0.8f, getAngle(), new float[]{endX, endY}, getBulletSpeed(), true));
            setShootTime(elapsedTime);
            return true;
        }
        return false;

    }

    public float getGunWidthMultiply() {
        return gunWidthMultiply;
    }

    public float getGunLengthMultiply() {
        return gunLengthMultiply;
    }

    public void setGunWidthMultiply(float gunWidthMultiply) {
        this.gunWidthMultiply = gunWidthMultiply;
    }

    public void setGunLengthMultiply(float gunLengthMultiply) {
        this.gunLengthMultiply = gunLengthMultiply;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public void setBulletSpeed(int bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
    }
    public int getMaxHpDiff() {return maxHpDiff;}

    public float getAttackFrequencyDiff() {return attackFrequencyDiff;}

    public int getDmgDiff() {return dmgDiff;}
}
