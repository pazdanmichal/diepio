package Entity;

public abstract class Entity {
    private int hp; //domyslnie 1 dla poczatkowej wersji gry
    private byte radius; //typ zmiennej zalezny od skali osi wspolrzednych (do ustalenia)
    private float angle; //kat obrotu jako wartosc zmienno-przecinkowa - wieksza dokladnosc
    private int[] position = new int[2]; //dwie wartosci x i y oznaczajace pozycje
    private int movementSpeed; //poniewaz enemy i bullet ma, a u pleyera domyslne bd 0

    public Entity(int hp, byte radius, float angle, int[] position, int movementSpeed) {
        this.hp = hp;
        this.radius = radius;
        this.angle = angle;
        this.position = position;
        this.movementSpeed = movementSpeed;
    }

    public int getHp() {
        return hp;
    }

    public byte getRadius() {
        return radius;
    }

    public void setRadius(byte radius) {
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

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getSpeed() {
        return movementSpeed;
    }

    public void setSpeed(int movementSpeed) {
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
