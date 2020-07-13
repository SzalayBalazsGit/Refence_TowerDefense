/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.enemies;

import java.awt.Color;
import java.awt.Graphics;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Random;

import vcad.gfx.Animation;
import vcad.gfx.Assets;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public abstract class Enemy {

    /*
    Az ellengsegek ősosztálya.
     */
    protected boolean testing  = false;
    protected int id;
    protected int health;
    protected Vector2 position;
    protected float speed;
    protected ArrayList<Vector2> wayPoints;
    protected boolean alive;
    protected boolean finished;
    protected int currentWPC;
    protected Vector2 currentWP;
    protected ArrayList<Animation> animations;
    protected boolean acitve = true;
    protected int debuffCounter = 0;
    protected int debuff = 0;
    protected boolean frozen = false;
    protected float realspeed;
    protected boolean payed = false;

    protected int dsec = 0;

    //@param waypoints - az utvonalnak a pontjai
    //@param id - azonosito
    public Enemy(ArrayList<Vector2> waypoints, int id) {
        wayPoints = waypoints;
        position = new Vector2(waypoints.get(0).getX(), waypoints.get(0).getY());
        this.id = id;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public boolean isAlive() {
        return alive;
    }

    public boolean isFinished() {
        return finished;
    }

    public void die() {
        alive = false;
    }

    //@param point - algoritmus alapján a pozicióját az objektum igyekszik megváltoztatni, hogy minél közelebb legyen a pointhoz
    //@return - elérte-e azt a pontot
    public boolean toWayPoint(Vector2 point) {
        float xDir = point.getX() - position.getX();
        float yDir = point.getY() - position.getY();
        Vector2 DirVec = new Vector2(xDir, yDir);
        DirVec.normalize();
        position.addX(DirVec.getX() * speed);
        position.addY(DirVec.getY() * speed);
        return (position.distance(point) < 1);
    }

    ///@param point - alterálni való pont
    //@return alterált pont
    public Vector2 differWaypoint(Vector2 point) {
        Random rand = new Random();
        rand.setSeed((long) id);
        point.addX(rand.nextInt(3));
        point.addY(rand.nextInt(3));
        return point;
    }

    //Levon az eléterőből majd megvizsgalja, hogy életben van-e még az ellenség
    //@param damage - levonni való sebzés
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            alive = false;
        }
        //System.out.println(health);
        //System.out.println("damaged");
    }

    public Vector2 getPos() {
        return position;
    }

    public int getHP() {
        return health;
    }

    public void finish() {
        finished = true;
        //System.out.println(id+" Finished!");
    }

    public int getId() {
        return id;
    }

    //alapból 0 - tehát nincs debuffja, de ha speciális löveést kap, akkor ez megváltozik.
    //@param d - aktiválando debuff számkódja
    public void takeDebuff(int d) {
        this.debuff = d;
    }

    ;
    
    //A debuff-oknak a "tick"-je, vagyis ami bizotsítja hogy az adott debuff végrehajtódik
    //Egy debuff 3 másodpercig tart (ami nem azonnal végrehajtandós) majd a debuff visszavált 0-ra.
    //@param i - debuff számkódja
    public void debuffTick(int i) {
        debuffCounter++;
        debuff(i);
        if (debuffCounter == 60) {
            debuffCounter = 0;
            dsec++;
        }
        if (dsec == 3) {
            debuff = 0;
            dsec = 0;
            this.speed = realspeed;
        }
    }

    //a debuff konkrét hatása az ellenségre, az adott int alapján
    //Az égés debuff, másodpercenként sebez (1).
     //@param i - debuff számkódja
    public void debuff(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                if (debuffCounter == 60 || testing) {
                    this.health -= 5;
                    if (health <= 0) {
                        alive = false;
                    }
                }
                break;
            case 5:
                this.speed = 0.4f;
            default:
                break;
        }
    }
    // //@param i - debuff számkódja
    public void renderDebuff(Graphics g, int i) {
        switch (i) {
            case 1:
                g.drawImage(Assets.fireParticle, (int) position.getX() - 40, (int) position.getY() - 100, null);
                break;
            case 4:
                g.drawImage(Assets.confusionParticle, (int) position.getX() - 40, (int) position.getY() - 150, null);
                break;
            case 5:
                g.drawImage(Assets.iceParticle, (int) position.getX() - 40, (int) position.getY() - 100, null);
                break;
            default:
                break;
        }
    }

    //A trickster-nek a "debuff"-ja, vagyis, ami előidézi, hogy az ellenség hátrafelé menjen. Az ezelőtti Waypointot kezdi el követni
    public void decWp() {
        if (this.currentWPC > 0) {
            this.currentWPC--;
        }
        this.currentWP = wayPoints.get(currentWPC);
    }

    //Minden ellenségnek van egy payed változója, ami eldönti hogy halála után kaptunk-e érte pénzt,
    //Ezt a levelben vizsgáljuk és ha egy ellenség meghalt, de nem fizetett, akkor növeljük a pénzünket és megváltoztatjuk az ellenség "payed" változóját.
    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    //rendereljük az ellenségek életét
    //@param y - az ellenség méretéhez képest mennyit kell eltolni az y tengelyen a hogy ne takarja el az információkat
    public void renderStat(Graphics g, int y) {
        g.setFont(g.getFont().deriveFont(25.0f));
        g.setColor(Color.green);
        g.drawString(" " + health, (int) position.getX() - 40, (int) position.getY() - y);
        renderDebuff(g, debuff);
    }

    public int getDebuff() {
        return debuff;
    }

    public float getSpeed() {
        return speed;
    }
    public void setTesting(boolean b)
    {
        testing = b;
    }
}
