/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.buildings;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vcad.enemies.Brute;
import vcad.enemies.Enemy;
import vcad.gfx.Assets;
import vcad.utils.Vector2;

/**
 *
 * @author Szalay Balázs
 */
public class BuildingTest {

    public BuildingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void GunnerConstructorTest() {
        Vector2 vec = new Vector2(1, 1);
        Gunner g = new Gunner(vec, 1);
        assertEquals(1.0f, g.getPos().getX(), 0.05f);
        assertEquals(1.0f, g.getPos().getY(), 0.05f);
        assertEquals(1500.0f, g.getFireRate(), 0.05f);
        assertEquals("Gunner.getDmg() != 12", 12, g.getDmg());
        assertEquals(650.0f, g.getRange(), 0.05f);
        assertEquals("Gunner.getlevel() != 1", 1, g.getLevel());
    }

    @Test
    public void SniperConstructorTest() {
        Vector2 vec = new Vector2(1, 1);
        Sniper s = new Sniper(vec, 2);
        assertEquals(1.0f, s.getPos().getX(), 0.05f);
        assertEquals(1.0f, s.getPos().getY(), 0.05f);
        assertEquals(3000.0f, s.getFireRate(), 0.05f);
        assertEquals("Sniper.getDmg() != 25", 25, s.getDmg());
        assertEquals(1100.0f, s.getRange(), 0.05f);
        assertEquals("Sniper.getlevel() != 1", 1, s.getLevel());
    }

    @Test
    public void RocketeerConstructorTest() {
        Vector2 vec = new Vector2(1, 1);
        Rocketeer r = new Rocketeer(vec, 3);
        assertEquals(1.0f, r.getPos().getX(), 0.05f);
        assertEquals(1.0f, r.getPos().getY(), 0.05f);
        assertEquals(2200.0f, r.getFireRate(), 0.05f);
        assertEquals("Rocketeer.getDmg() != 15", 15, r.getDmg());
        assertEquals(380.0f, r.getRange(), 0.05f);
        assertEquals("Rocketeer.getlevel() != 1", 1, r.getLevel());
        assertEquals(150.0f, r.getAOEDistance(), 0.05f);
    }

    @Test
    public void dealdamageTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy e = new Brute(wayp, 1);
        Vector2 vec = new Vector2(2, 2);
        Gunner g = new Gunner(vec, 4);
        Sniper s = new Sniper(vec, 5);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        g.dealDamage(e);
        assertEquals("Brute.getHP() != 280", 288, e.getHP());
        s.dealDamage(e);
        assertEquals("Brute.getHP() != 235", 263, e.getHP());
    }

    @Test
    public void RocketeerAOETest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        ArrayList<Vector2> wayp2 = new ArrayList<Vector2>();
        wayp2.add(new Vector2(10.0f, 10.0f));
        wayp2.add(new Vector2(11.0f, 11.0f));
        Enemy e1 = new Brute(wayp, 1);
        Enemy e2 = new Brute(wayp2, 2);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e1);
        enemies.add(e2);
        Vector2 vec = new Vector2(2, 2);
        Rocketeer r = new Rocketeer(vec, 6);
        assertEquals("Brute1.getHP() != 300", 300, e1.getHP());
        assertEquals("Brute2.getHP() != 300", 300, e2.getHP());
        r.tick(enemies);
        assertEquals("Brute1.getHP() != 278", 285, e1.getHP());
        assertEquals("Brute2.getHP() != 278", 285, e2.getHP());
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        r.tick(enemies);
        assertEquals("Brute1.getHP() != 256", 270, e1.getHP());
        assertEquals("Brute2.getHP() != 256", 270, e2.getHP());
    }

    @Test
    public void RocketeerUpgradeTester() {
        Vector2 vec = new Vector2(1, 1);
        Rocketeer r = new Rocketeer(vec, 3);
        Vector2 vec2 = new Vector2(2, 2);
        Rocketeer r2 = new Rocketeer(vec2, 6);
        assertEquals("Rocketeer.getDmg() != 15", 15, r.getDmg());
        assertEquals("Rocketeer.getlevel() != 1", 1, r.getLevel());
        r.upgrade();
        assertEquals("Rocketeer.getDmg() != 18", 18, r.getDmg());
        assertEquals("Rocketeer.getlevel() != 2", 2, r.getLevel());
        r.upgrade();
        assertEquals("Rocketeer.getDmg() != 21", 21, r.getDmg());
        assertEquals("Rocketeer.getlevel() != 3", 3, r.getLevel());
        r.specialize(1);
        assertEquals("Rocketeer.getDmg() != 21", 21, r.getDmg());
        assertEquals("Rocketeer.getlevel() != 4", 4, r.getLevel());
        assertEquals("Rocketeer.getImage() != 4", Assets.Speedster, r.getImage());
        r2.upgrade();
        r2.upgrade();
        r2.specialize(2);
        assertEquals("Rocketeer.getImage() != Freezer", Assets.Freezer, r2.getImage());
    }

    @Test
    public void GunnerUpgradeTester() {
        Vector2 vec = new Vector2(1, 1);
        Gunner r = new Gunner(vec, 3);
        Vector2 vec2 = new Vector2(2, 2);
        Gunner r2 = new Gunner(vec2, 6);
        assertEquals("Gunner.getDmg() != 12", 12, r.getDmg());
        assertEquals("Gunner.getlevel() != 1", 1, r.getLevel());
        r.upgrade();
        assertEquals("Gunner.getDmg() != 14", 14, r.getDmg());
        assertEquals("Gunner.getlevel() != 2", 2, r.getLevel());
        r.upgrade();
        assertEquals("Gunner.getDmg() != 16", 16, r.getDmg());
        assertEquals("Gunner.getlevel() != 3", 3, r.getLevel());
        r.specialize(1);
        assertEquals("Gunner.getDmg() != 16", 16, r.getDmg());
        assertEquals("Gunner.getlevel() != 4", 4, r.getLevel());
        assertEquals("Gunner.getImage() != 4", Assets.Burner, r.getImage());
        r2.upgrade();
        r2.upgrade();
        r2.specialize(2);
        assertEquals("Gunner.getImage() != Freezer", Assets.Grenader, r2.getImage());
    }

    @Test
    public void SniperUpgradeTester() {
        Vector2 vec = new Vector2(1, 1);
        Sniper r = new Sniper(vec, 3);
        Vector2 vec2 = new Vector2(2, 2);
        Sniper r2 = new Sniper(vec2, 6);
        assertEquals("Sniper.getDmg() != 25", 25, r.getDmg());
        assertEquals("Sniper.getlevel() != 1", 1, r.getLevel());
        r.upgrade();
        assertEquals("Sniper.getDmg() != 30", 30, r.getDmg());
        assertEquals("Sniper.getlevel() != 2", 2, r.getLevel());
        r.upgrade();
        assertEquals("Sniper.getDmg() != 21", 36, r.getDmg());
        assertEquals("Sniper.getlevel() != 3", 3, r.getLevel());
        r.specialize(1);
        assertEquals("Sniper.getDmg() != 21", 36, r.getDmg());
        assertEquals("Sniper.getlevel() != 4", 4, r.getLevel());
        assertEquals("Sniper.getImage() != 4", Assets.Reaper, r.getImage());
        r2.upgrade();
        r2.upgrade();
        r2.specialize(2);
        assertEquals("Rocketeer.getImage() != Freezer", Assets.Trickster, r.getImage());
    }

    @Test
    public void GunnerUpgrade2EffectTest() {
        Vector2 vec = new Vector2(1, 1);
        Gunner r = new Gunner(vec, 3);
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(10.0f, 10.0f));
        Enemy e = new Brute(wayp, 1);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        assertEquals(1500.0f, r.getFireRate(), 0.05f);
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 288", 288, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 274", 274, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 258", 258, e.getHP());
        r.specialize(2);
        r.tester(true);
        r.tick(enemies);
        assertEquals(500.0f, r.getFireRate(), 0.05f);
    }

    @Test
    public void GunnerUpgrade1EffectTest() {
        Vector2 vec = new Vector2(1, 1);
        Gunner r = new Gunner(vec, 3);
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(10.0f, 10.0f));
        Enemy e = new Brute(wayp, 1);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        assertEquals(1500.0f, r.getFireRate(), 0.05f);
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 288", 288, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 274", 274, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 258", 258, e.getHP());
        r.specialize(1);
        r.tick(enemies);
        assertEquals("Brute1.getHP() != 242", 242, e.getHP());
        assertEquals("Brute1.getDebuff() != 1", 1, e.getDebuff());
        e.setTesting(true);
        e.debuff(e.getDebuff());
        assertEquals("Brute1.getHP() != 237", 237, e.getHP());
        e.debuff(e.getDebuff());
        assertEquals("Brute1.getHP() != 232", 232, e.getHP());
        e.debuff(e.getDebuff());
        assertEquals("Brute1.getHP() != 227", 227, e.getHP());
    }

    @Test
    public void SniperUpgrade1EffectTest() {
        Vector2 vec = new Vector2(1, 1);
        Sniper r = new Sniper(vec, 3);
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(10.0f, 10.0f));
        Enemy e = new Brute(wayp, 1);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 275", 275, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 245", 245, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 209", 209, e.getHP());
        r.specialize(1);
        r.tester(true);
        r.tick(enemies);
        assertEquals("Brute1.getHP() != -291", -291, e.getHP());
        assertEquals("Brute1.isAlive != false", false, e.isAlive());
    }

    @Test
    public void SniperUpgrade2EffectTest() {
        Vector2 vec = new Vector2(1, 1);
        Sniper r = new Sniper(vec, 3);
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(10.0f, 10.0f));
        Enemy e = new Brute(wayp, 1);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 275", 275, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 245", 245, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 209", 209, e.getHP());
        r.specialize(2);
        r.tester(true);
        r.tick(enemies);
        assertEquals("Brute1.getDebuff() != 4", 4, e.getDebuff());
    }

    @Test
    public void RocketeerUpgrade1EffectTest() {
        Vector2 vec = new Vector2(1, 1);
        Rocketeer r = new Rocketeer(vec, 3);
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(10.0f, 10.0f));
        ArrayList<Vector2> wayp2 = new ArrayList<Vector2>();
        wayp2.add(new Vector2(200.0f, 0.0f));
        wayp2.add(new Vector2(210.0f, 0.0f));
        Enemy e = new Brute(wayp, 1);
        Enemy f = new Brute(wayp2, 2);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e);
        enemies.add(f);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        r.tick(enemies);
        assertEquals("Rocketeer.getVar()", 0, r.getVar());
        assertEquals(150.0f, r.getAOEDistance(), 0.05f);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 285", 285, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals(150.0f, r.getAOEDistance(), 0.05f);
        assertEquals("Brute1.getHP() != 267", 267, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals(150.0f, r.getAOEDistance(), 0.05f);
        assertEquals("Brute1.getHP() != 246", 246, e.getHP());
        assertEquals("Brute2.getHP() != 300", 300, f.getHP());
        r.specialize(1);
        assertEquals("Rocketeer.getVar()", 1, r.getVar());
        r.tester(true);
        r.tick(enemies);
        assertEquals(250.0f, r.getAOEDistance(), 0.05f);
        assertEquals("Brute1.getHP() != 225", 225, e.getHP());
        assertEquals("Brute2.getHP() != 279", 279, f.getHP());

    }

    @Test
    public void RocketeerUpgrade2EffectTest() {
        Vector2 vec = new Vector2(1, 1);
        Rocketeer r = new Rocketeer(vec, 3);
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(10.0f, 10.0f));
        ArrayList<Vector2> wayp2 = new ArrayList<Vector2>();
        wayp2.add(new Vector2(200.0f, 0.0f));
        wayp2.add(new Vector2(210.0f, 0.0f));
        ArrayList<Vector2> wayp3 = new ArrayList<Vector2>();
        wayp3.add(new Vector2(110.0f, 0.0f));
        wayp3.add(new Vector2(120.0f, 0.0f));
        Enemy e = new Brute(wayp, 1);
        Enemy f = new Brute(wayp2, 2);
        Enemy g = new Brute(wayp3, 3);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(e);
        enemies.add(f);
        enemies.add(g);
        assertEquals("Brute.getHP() != 300", 300, e.getHP());
        r.tick(enemies);
        assertEquals("Rocketeer.getVar()", 0, r.getVar());
        assertEquals(0.5f, e.getSpeed(), 0.05f);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals("Brute1.getHP() != 285", 285, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals(150.0f, r.getAOEDistance(), 0.05f);
        assertEquals("Brute1.getHP() != 267", 267, e.getHP());
        r.upgrade();
        r.tick(enemies);
        r.setLasttime(System.currentTimeMillis() - (int) r.getFireRate() - 1);
        assertEquals(150.0f, r.getAOEDistance(), 0.05f);
        assertEquals("Brute1.getHP() != 246", 246, e.getHP());
        assertEquals("Brute2.getHP() != 300", 300, f.getHP());
        r.specialize(2);
        assertEquals("Rocketeer.getVar()", 2, r.getVar());
        r.tester(true);
        r.tick(enemies);
        assertEquals("Brute1.getHP() != 225", 225, e.getHP());
        assertEquals("Brute2.getHP() != 225", 225, g.getHP());
        assertEquals("Brute3.getHP() != 300", 300, f.getHP());
        assertEquals("Brute1.getDebuff() != 5", 5, e.getDebuff());
        assertEquals("Brute2.getDebuff() != 5", 5, g.getDebuff());
        assertEquals("Brute3.getDebuff() != 0", 0, f.getDebuff());
        e.debuff(e.getDebuff());
        f.debuff(f.getDebuff());
        g.debuff(g.getDebuff());
        assertEquals(0.4f, e.getSpeed(), 0.05f);
        assertEquals(0.4f, g.getSpeed(), 0.05f);
        assertEquals(0.5f, f.getSpeed(), 0.05f);

    }
    /**
     * Test of tick method, of class Building.
     */

    /*
    public class BuildingImpl extends Building {

        public void tick(ArrayList<Enemy> enemies) {
        }

        public void render(Graphics g) {
        }

        public void getEnemy() {
        }
    }
     */
}
