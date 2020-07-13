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
public class Sniper extends Building {
    /*
     A Sniper osztály lassú de erős lövéseket ad le nagy hatótávban.
     Specializációi
     Repaer - Van esélye hogy egy "Headshot-ot" ad amely bármely ellenséget azonnal megöl. Szimplán csak a lövés erejéig 500-ra megnő a sebzése a toronynak.
     Trickster - Van esélye, hogy összezavarja a célzott ellenséget és átadja neki a tricked debuff kódját, amitől az ellenség elkezd hátrafelé menni.
     */
    private Enemy ShotEnemy;
    private Boolean shot;
    private Color ShotColor;
   
     //@param pos - torony pozicioja
    //@param id  - torony azonositoja
    public Sniper(Vector2 pos,int id) {
        this.id = id;
        this.level = 1;
        this.position = pos;
        this.damage = 25;
        this.fireRate = 3000.0f;
        this.range = 1100.0f;
        this.tower = Assets.Sniper;
        this.lasttime = System.currentTimeMillis() - (int) fireRate -1;
        shot = false;
        ShotColor = Color.BLUE;
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
                    if(this.variation == 0){
                        dealDamage(enemies.get(index));   
                    }
                    if(this.variation == 1){
                        Random rand = new Random(); 
                                int randint = rand.nextInt(3);
                                if(randint == 1 || testing){
                                    headshot(enemies.get(index));
                                }else{
                                  dealDamage(enemies.get(index));      
                                }
                    }
                    if(this.variation == 2){
                         Random rand = new Random(); 
                                int randint = rand.nextInt(3);
                                if(randint == 1 || testing){
                                    dealDamage(enemies.get(index));   
                                    enemies.get(index).decWp();
                                    dealDebuff(enemies.get(index),4);
                                }else{
                                  dealDamage(enemies.get(index));      
                                }
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
        g.drawImage(this.tower, (int) position.getX()-5, (int) position.getY()-40, null);
        if (shot == true) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(ShotColor);
            g2.drawLine((int) position.getX(), (int) position.getY(), (int) ShotEnemy.getPos().getX(), (int) ShotEnemy.getPos().getY());
        }
    }

    @Override
    public void getEnemy() {

    }
    
    @Override
    public void specialize(int x) {
        if(x==1){
            this.tower = Assets.Reaper;
            this.variation = 1;
        }else{
              this.tower = Assets.Trickster;
              this.variation = 2;
        }
        this.level++;
    }
    
    public void headshot(Enemy e){
         e.takeDamage(500);
        lasttime = System.currentTimeMillis();
    }
}
