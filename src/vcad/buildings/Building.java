/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.buildings;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import vcad.enemies.Enemy;
import vcad.gfx.Animation;
import vcad.ui.UIObject;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public abstract class Building {

    protected boolean testing = false;
    /*Az alap building osztály amiből származnak később a tornyok. A működési elve mindegyik toronynak, hogy
    kap egy Enemy listát, amelyik az épp aktuális ellengségeket tartalmazza, és megvizsgálja az összeset, hogy melyek vannak benne a 
    hatókörében, és onnan kiválasztja a legközelebb lévőt és sebzi.
    
    Az upgrade metódus megnöveli az adott toronynak a sebzését 20%-al és növeli 1-el a szintjét.
    Ha elérte a 3. szintet, akkor specializálódhat, ami ad neki egy új külsőt és egy új speciális sebzést.
    
    Ha specializálódik, akkor változik a variation változó, amely lövésnél határozza meg, hogy milyet lőjön a torony
    1-sima lövés, 2-speci1 3-speci2      
    Egyes spaceilalizációknál az összes lövés változik, máshol csak esély van arra hogy speciális lövést adjon le.
    Ha esélyt a speciális lövésre egy random inttel döntjük el pl rand() % 3 és akkor lesz speci lövés ha 1-et kapunk eredménynek.
    
    A löveseket  egy a renderben egy drawline-al rajzoljuk ki, a torony és az ellenség pozicíója között.
    */
    protected int id;
    protected Vector2 position;
    protected float fireRate;
    protected int damage;
    protected float range;
    protected int level = 1;
    protected int[] upgradeCostM;
    protected int[] upgradeCostX;
    protected int damageUpgrade;
    protected float rangeUpgrade;
    protected BufferedImage tower;
    protected ArrayList<Animation> animations;
    protected UIObject listener;
    protected long lasttime;
    protected int variation = 0;
    //@param enemies - ellensegek listája, akiket sebezni fogunk
    public abstract void tick(ArrayList<Enemy> enemies);

    public abstract void render(Graphics g);

    public abstract void getEnemy();

    public abstract void specialize(int x);
    //@param e  -ellenseg akit sebzunk
    public void dealDamage(Enemy e) {
        e.takeDamage(this.damage);
        lasttime = System.currentTimeMillis();
    }

    public void upgrade() {
        this.level++;
        this.damage = (int) (this.damage * 1.2);
    }

    public BufferedImage getImage() {
        return tower;
    }

    public long getLastTime() {
        return lasttime;
    }

    public float getFireRate() {
        return fireRate;
    }

    public Vector2 getPos() {
        return position;
    }

    public float getRange() {
        return range;
    }

    public float getAOEDistance() {
        return 0.0f;
    }

    public int getDmg() {
        return damage;
    }

    public int getLevel() {
        return level;
    }

    public void setLasttime(long l) {
        lasttime = l;
    }

    public int getID() {
        return id;
    }
    //@param e - ellenseg akire a debuff megy
    //@param i - debuff tipusa szamkent
    public void dealDebuff(Enemy e, int i) {
        e.takeDebuff(i);
        lasttime = System.currentTimeMillis();
    }
    public void tester(boolean b)
    {
        testing = b;
    }
    public int getVar()
    {
        return variation;
    }
}
