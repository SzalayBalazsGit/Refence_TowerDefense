/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.enemies;

import java.awt.Graphics;
import java.util.ArrayList;
import vcad.gfx.Assets;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public class Runner extends Enemy {
       
    //Gyors de gyenge ellenség
     //@param waypoints - az utvonalnak a pontjai
    //@param id - azonosito
    
    public Runner(ArrayList<Vector2> waypoints, int id) {
        super(waypoints, id);
        currentWPC = 1;
        currentWP = wayPoints.get(currentWPC);
        finished = false;
        alive = true;
        speed = 2;
        realspeed = speed;
        health = 75;
    }

    @Override
    public void tick() {
        if (debuff != 0) {
            debuffTick(debuff);
        }
        if (toWayPoint(currentWP)) {
            currentWPC++;
            if (currentWPC == wayPoints.size()) {
                finish();
            } else {
                currentWP = differWaypoint(wayPoints.get(currentWPC));
                //System.out.println(currentWPC+" "+id);
                //currentWP.print();
            }
        }
        //position.addX(2);
    }

    @Override
    public void render(Graphics g) {
        if (alive && !finished) {
            g.drawImage(Assets.Runner, (int) position.getX() - 55, (int) position.getY() - 85, null);
            renderStat(g, 95);
        }
    }
}
