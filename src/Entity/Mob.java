package Entity;

public abstract class Mob extends Entity {
    private boolean canShoot;

    // sth
    private float attackFrequency;
    private int damage;
    private long shootTime = 0;

    public Mob(int hp, float radius, float angle, float[] position,
               float movementSpeed, boolean canShoot, float attackFrequency, int damage) {
        super(hp, radius, angle, position, movementSpeed);
        this.canShoot = canShoot;
        this.attackFrequency = attackFrequency;
        this.damage = damage;
    }



    public Mob() {
        super();
        this.canShoot = false;
        this.attackFrequency = 0;
        this.damage = 0;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }
    //bez sensu, raczej nie bedzie potrzeby zmiany tej zmiennej

    public float getAttackFrequency() {
        return attackFrequency;
    }

    public void setAttackFrequency(float attackFrequency) {
        this.attackFrequency = attackFrequency;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public long getShootTime() {
        return shootTime;
    }

    public void setShootTime(long shootTime) {
        this.shootTime = shootTime;
    }

    public void SpawnBullet(){
        //czekamy na interface
    }
}
