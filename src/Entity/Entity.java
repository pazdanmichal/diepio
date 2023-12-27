package Entity;

public abstract class Entity {
    private int hp; //domyslnie 1 dla poczatkowej wersji gry
    private float radius; //typ zmiennej zalezny od skali osi wspolrzednych (do ustalenia)
    private float angle; //kat obrotu jako wartosc zmienno-przecinkowa - wieksza dokladnosc
    private float[] position = new float[2]; //dwie wartosci x i y oznaczajace pozycje
    private float movementSpeed; //poniewaz enemy i bullet ma, a u pleyera domyslne bd 0

    public Entity(int hp, float radius, float angle, float[] position, float movementSpeed) {
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
        this.hp = hp;
    }

    public float getSpeed() {
        return movementSpeed;
    }

    public void setSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public void TakeDamage(int givenDamage){
        this.hp = this.hp - givenDamage;
        if(this.hp <= 0){
            Die();
        }
    }
    public void Die(){
        //metoda bedzie przyslonieta dla roznych klas, dla enemy i bullet
        //bedzie kasowac obiekt, a dla playera bedzie konczyc gre
    }




}
