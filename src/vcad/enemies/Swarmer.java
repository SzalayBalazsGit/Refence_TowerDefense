/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.enemies;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import vcad.Handler;
import vcad.gfx.Assets;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public class Swarmer extends Enemy {

    private int counter = 0;
    private Handler handler;
    /*
        Eros es kozepes gyorsasagu ellenfel. Swarmokat spawnol le 3 masodpercenkent.
        A handlerre szüksége van hogy elérje a pending enemies listát, hogy lespawnolodjanak a SWarmok és teljes értékű  ellenségek legyenek.
        @param waypoints - az utvonalnak a pontjai
        @param id - azonosito
        @param handler - a handler amivel üzenhet a felette lévő szintnek, mikor lespawnol egy Swarmot
    */
    public Swarmer(ArrayList<Vector2> waypoints, int id, Handler handler) {
        super(waypoints, id);
        currentWPC = 1;
        currentWP = wayPoints.get(currentWPC);
        finished = false;
        alive = true;
        speed = 1;
        realspeed = speed;
        health = 150;
        this.handler = handler;
    }

    public Swarmer(ArrayList<Vector2> waypoints, int id) {
        super(waypoints, id);
        currentWPC = 1;
        currentWP = wayPoints.get(currentWPC);
        finished = false;
        alive = true;
        speed = 1;
        realspeed = speed;
        health = 150;
        this.handler = null;
    }

    @Override
    public void tick() {
        if (counter == 180) {
            counter = 0;
            //System.out.println("Swpan swarm");
            ArrayList<Vector2> tmpWp = new ArrayList<Vector2>();
            for (int i = currentWPC - 1; i < wayPoints.size(); i++) {
                tmpWp.add(wayPoints.get(i));
            }
            Swarm e = new Swarm(tmpWp, 1);
            handler.getLevel().addEnemy(e);
        } else {
            counter++;
        }
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
            g.drawImage(Assets.Swarmer, (int) position.getX() - 55, (int) position.getY() - 120, null);
            renderStat(g, 130);
        }
    }

}
