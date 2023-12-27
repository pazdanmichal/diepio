package Entity;

public class Player extends Mob{
    private byte currentRotation;//-1 -> lewo; 0 -> stoi; 1 -> prawo
    private float rotationSpeed;
    private float gunWidthMultiply;
    private float gunLengthMultiply;
    private int bulletSPeed;

    public Player(int hp, float radius, float angle, float[] position, float movementSpeed, boolean canShoot,
                  float attackFrequency, int damage, byte currentRotation, float rotationSpeed, float gunLengthMultiply, float gunWidthMultiply, int bulletSpeed) {
        super(hp, radius, angle, position, movementSpeed, canShoot, attackFrequency, damage);
        this.currentRotation = currentRotation;
        this.rotationSpeed = rotationSpeed;
        this.gunLengthMultiply = gunLengthMultiply;
        this.gunWidthMultiply = gunWidthMultiply;
        this.bulletSPeed = bulletSpeed;
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

    public int getBulletSPeed() {
        return bulletSPeed;
    }

    public void setBulletSPeed(int bulletSPeed) {
        this.bulletSPeed = bulletSPeed;
    }

    @Override
    public String toString() {
        return "\nPlayer:\n" + super.toString() +
                "\ncurrentRotation = " + currentRotation +
                "\nrotationSpeed = " + rotationSpeed +
                "\ngunWidthMultiply = " + gunWidthMultiply +
                "\ngunLengthMultiply = " + gunLengthMultiply +
                "\nbulletSPeed = " + bulletSpeed;
    }
}
