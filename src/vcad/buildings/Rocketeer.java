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
public class Rocketeer extends Building {
    /*
        A Rocketeer torony közepesen gyorsan es közepesen erősen sebez, de Területi sebzést  ad. Szintén meghatározza lövéskor a legközelebbi 
    ellenséget rangen belül. Aztén e pont központú és területi sebzés mérete sugarú körben megnézi a bent lévő ellenségeket és mindegyiket sebzi.
    Specializációi
    Freezer - a sebzett ellenségeknek ad egy szintén egy debuff számot, amelyet az ellenség lekezel. Ez esetben csökken sebessége.
    Speedster - Eredetileg lett volna esélye, hogy gyorsan leadjon 5 lövést (nevéből adódóan) de utólag változtattuk meg, hogy van esélye,
    hogy 2szer akkora méretű a a területi sebzés.
    */
    private float aoeDistance = 150.0f;
    private Enemy ShotEnemy;
    private Boolean shot;
    private Color ShotColor;
    private Boolean speedStrike;
     //@param pos - torony pozicioja
    //@param id  - torony azonositoja
    public Rocketeer(Vector2 pos, int id) {
        this.id = id;
        this.level = 1;
        this.position = pos;
        this.damage = 15;
        this.fireRate = 2200.0f;
        this.range = 380.0f;
        this.tower = Assets.Rocketeer;
        this.lasttime = System.currentTimeMillis() - (int) fireRate - 1;
        shot = false;
        ShotColor = Color.GREEN;
        speedStrike = false;
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
                for (int j = index; j < enemies.size(); j++) {
                    if (position.distance(enemies.get(j).getPos()) <= minDistance && enemies.get(j).isAlive() == true && enemies.get(j).isFinished() == false) {
                        index = j;
                        minDistance = position.distance(enemies.get(j).getPos());
                        ShotEnemy = enemies.get(j);
                    }
                }
                if (minDistance <= range) {
                    if (this.variation == 1)
                    {
                        Random rand = new Random();
                        int randint = rand.nextInt(3);
                        if (randint == 1 || testing) {
                            aoeDistance = 250.0f;
                            speedStrike = true;
                        }
                    }
                    for (int i = 0; i < enemies.size(); i++) {
                        if (enemies.get(index).getPos().distance(enemies.get(i).getPos()) <= aoeDistance && enemies.get(i).isAlive() == true && enemies.get(i).isFinished() == false) {
                            if (this.variation == 0) {
                                dealDamage(enemies.get(i));
                            }
                            if (this.variation == 1) {
                                dealDamage(enemies.get(i));
                            }
                            if (this.variation == 2) {
                                dealDamage(enemies.get(i));
                                dealDebuff(enemies.get(i), 5);
                            }
                        }
                        shot = true;
                        //System.out.println(enemies.get(index).getPos().distance(enemies.get(i).getPos()));
                    }
                } else {
                    shot = false;
                }
            } else {
                shot = false;
            }
        } else {
            shot = false;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.tower, (int) position.getX() - 5, (int) position.getY() - 40, null);
        if (shot == true) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(ShotColor);
            g2.drawLine((int) position.getX(), (int) position.getY(), (int) ShotEnemy.getPos().getX(), (int) ShotEnemy.getPos().getY());
            if (speedStrike == false) {
                g2.fillOval((int) ShotEnemy.getPos().getX() - (int) aoeDistance / 2, (int) ShotEnemy.getPos().getY() - (int) aoeDistance / 2, (int) aoeDistance, (int) aoeDistance);
            } else {
                aoeDistance = 250.0f;
                g2.fillOval((int) ShotEnemy.getPos().getX() - (int) aoeDistance / 2, (int) ShotEnemy.getPos().getY() - (int) aoeDistance / 2, (int) aoeDistance, (int) aoeDistance);
                aoeDistance = 150.0f;
                speedStrike = false;
            }
        }
    }

    @Override
    public void getEnemy() {

    }

    public float getAOEDistance() {
        return aoeDistance;
    }

    @Override
    public void specialize(int x) {
        if (x == 1) {
            this.tower = Assets.Speedster;
            this.variation = 1;
        } else {
            this.tower = Assets.Freezer;
            this.variation = 2;
        }
        this.level++;
    }

    public void setStrike(Boolean b) {
        speedStrike = b;
    }
}
