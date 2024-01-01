package Entity;

public class Player extends Mob{
    private byte currentRotation;//-1 -> lewo; 0 -> stoi; 1 -> prawo
    private float rotationSpeed;
    private int maxHp;
    private float gunWidthMultiply;
    private float gunLengthMultiply;
    private int bulletSpeed;

    public Player(int hp, float radius, float angle, float[] position, float movementSpeed, boolean canShoot,
                  float attackFrequency, int damage, byte currentRotation, float rotationSpeed, float gunLengthMultiply, float gunWidthMultiply, int bulletSpeed, int maxHp) {
        super(hp, radius, angle, position, movementSpeed, canShoot, attackFrequency, damage);
        this.currentRotation = currentRotation;
        this.rotationSpeed = rotationSpeed;
        this.gunLengthMultiply = gunLengthMultiply;
        this.gunWidthMultiply = gunWidthMultiply;
        this.bulletSpeed = bulletSpeed;
        this.maxHp = maxHp;
    }
    public Player() {
        super();
        this.currentRotation = 0;
        this.rotationSpeed = 0;
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

    public void Die(){
        //przyslonieta metoda dla klasy player -> zakonczenie rozgrywki
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
}
