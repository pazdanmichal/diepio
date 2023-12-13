package Entity;

public abstract class Mob extends Entity {
    private boolean canShoot;
    private int attackFrequency;
    private int damage;

    public Mob(int hp, byte radious, float angle, int[] position,
               int movementSpeed, boolean canShoot, int attackFrequency, int damage) {
        super(hp, radious, angle, position, movementSpeed);
        this.canShoot = canShoot;
        this.attackFrequency = attackFrequency;
        this.damage = damage;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }
    //bez sensu, raczej nie bedzie potrzeby zmiany tej zmiennej

    public int getAttackFrequency() {
        return attackFrequency;
    }

    public void setAttackFrequency(int attackFrequency) {
        this.attackFrequency = attackFrequency;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void SpawnBullet(){
        //czekamy na interface
    }
}
