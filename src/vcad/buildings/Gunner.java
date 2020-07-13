/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.buildings;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import vcad.enemies.Enemy;

import vcad.gfx.Assets;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public class Gunner extends Building {
    /*  
        Az Gunner típusú torony gyors es gyenge lövést tud adni, specializációja a
        Burner - égés debuffot ad az ellenségekenek (egy int-et ad át, amely alapján az ellenség eldönti h miylen 
        debuffot kap, szinte úgyanúgy mint a sima sebzésnél)
        Grenader - van esély rá, hogy aktiválódik a gyorstüzelés és 5 lövést sokkal gyorsabban lead
        (azért tér el a neve, mert az eredeti képessége nagyon hasonlított volna, a sima rocketeer toronyra, 
        utólag meg kellet változtatnunk, de a sprietot, már nem érekztünk újrarazolni)
    */
    private Enemy ShotEnemy;
    private Boolean shot;
    private Color ShotColor;
    private int shotcounter;
    //@param pos - torony pozicioja
    //@param id  - torony azonositoja
    public Gunner(Vector2 pos, int id) {
        this.id = id;
        this.level = 1;
        this.position = pos;
        this.damage = 12;
        this.fireRate = 1500.0f;
        this.range = 650.0f;
        this.tower = Assets.Gunner;
        this.lasttime = System.currentTimeMillis() - (int) fireRate - 1;
        shot = false;
        ShotColor = Color.RED;
        shotcounter = 0;
    }

    @Override
    public void tick(ArrayList<Enemy> enemies) {
        if (System.currentTimeMillis() - lasttime > fireRate && enemies.size() > 0) {
            int index = 0;
            while (index < enemies.size() && (enemies.get(index).isAlive() == false || enemies.get(index).isFinished() == true)) {
                index++;
            }
            if (index < enemies.size()) {
                double minDistance = position.distance(enemies.get(index).getPos());
                for (int j = index + 1; j < enemies.size(); j++) {
                    if (position.distance(enemies.get(j).getPos()) < minDistance && enemies.get(j).isAlive() == true && enemies.get(j).isFinished() == false) {
                        index = j;
                        minDistance = position.distance(enemies.get(j).getPos());
                    }
                }
                if (minDistance <= range) {
                    if (this.variation == 0) {
                        dealDamage(enemies.get(index));
                    }
                    if (this.variation == 1) {
                        dealDamage(enemies.get(index));
                        dealDebuff(enemies.get(index), 1);
                    }
                    if (this.variation == 2) {
                        Random rand = new Random();
                        int randint = rand.nextInt(3);
                        if (randint == 1 || testing) {
                            fireRate = 500.0f;
                            shotcounter = 0;
                        }
                        shotcounter++;
                        if (shotcounter >= 3) {
                            fireRate = 1500.0f;
                        }
                        dealDamage(enemies.get(index));
                    }
                    shot = true;
                    ShotEnemy = enemies.get(index);
                }
            }
        } else {
            shot = false;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.tower, (int) position.getX() - 5, (int) position.getY() - 30, null);
        if (shot == true) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(ShotColor);
            g2.drawLine((int) position.getX(), (int) position.getY(), (int) ShotEnemy.getPos().getX(), (int) ShotEnemy.getPos().getY());
        }
    }

    @Override
    public void getEnemy() {

    }
    //@param x - specializáció számként
    @Override
    public void specialize(int x) {
        if (x == 1) {
            this.tower = Assets.Burner;
            this.variation = 1;
        } else {
            this.tower = Assets.Grenader;
            this.variation = 2;
        }
        this.level++;
    }
}
