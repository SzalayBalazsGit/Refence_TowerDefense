/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.enemies;

import java.awt.Graphics;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vcad.Game;
import vcad.Handler;
import vcad.utils.Vector2;

/**
 *
 * @author Szalay Balázs
 */
public class EnemyTest {

    public EnemyTest() {
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
    public void BruteConstructorTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Brute(wayp, 1);
        assertEquals("Brute.getId() != 1", 1, E.getId());
        assertEquals(0.0f, E.getPos().getX(), 0.05f);
        assertEquals(0.0f, E.getPos().getY(), 0.05f);
        assertEquals("Brute.getHP() != 300", 300, E.getHP());
        assertEquals("Brute.getId() != 1", 1, E.getId());
        assertEquals("Brute.getIsFinished() != false", false, E.isFinished());
        assertEquals("Brute.getisAlive() != true", true, E.isAlive());
    }

    @Test
    public void RunnerConstructorTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Runner(wayp, 1);
        assertEquals("E.getId() != 1", 1, E.getId());
        assertEquals(0.0f, E.getPos().getX(), 0.05f);
        assertEquals(0.0f, E.getPos().getY(), 0.05f);
        assertEquals("E.getHP() != 75", 75, E.getHP());
        assertEquals("E.getId() != 1", 1, E.getId());
        assertEquals("E.getIsFinished() != false", false, E.isFinished());
        assertEquals("E.getisAlive() != true", true, E.isAlive());
    }

    @Test
    public void SwarmerConstructorTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Swarmer(wayp, 1);
        assertEquals("E.getId() != 1", 1, E.getId());
        assertEquals(0.0f, E.getPos().getX(), 0.05f);
        assertEquals(0.0f, E.getPos().getY(), 0.05f);
        assertEquals("E.getHP() != 150", 150, E.getHP());
        assertEquals("E.getId() != 1", 1, E.getId());
        assertEquals("E.getIsFinished() != false", false, E.isFinished());
        assertEquals("E.getisAlive() != true", true, E.isAlive());
    }

    @Test
    public void SwarmConstructorTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Swarm(wayp, 1);
        assertEquals("E.getId() != 1", 1, E.getId());
        assertEquals(0.0f, E.getPos().getX(), 0.05f);
        assertEquals(0.0f, E.getPos().getY(), 0.05f);
        assertEquals("E.getHP() != 50 ", 50, E.getHP());
        assertEquals("E.getId() != 1", 1, E.getId());
        assertEquals("E.getIsFinished() != false", false, E.isFinished());
        assertEquals("E.getisAlive() != true", true, E.isAlive());
    }

    @Test
    public void EnemyTakeDamageTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Brute(wayp, 1);
        assertEquals("E.getHP() != 300 ", 300, E.getHP());
        E.takeDamage(50);
        assertEquals("E.getHP() != 250 ", 250, E.getHP());
        E.takeDamage(30);
        assertEquals("E.getHP() != 220 ", 220, E.getHP());
        E.takeDamage(140);
        assertEquals("E.getHP() != 80 ", 80, E.getHP());
    }
    @Test
    public void enemyDieTest() {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Runner(wayp, 1);
        assertEquals("E.getHP() != 75 ", 75, E.getHP());
        assertEquals("E.getisAlive() != true", true, E.isAlive());
        E.takeDamage(75);
        assertEquals("E.getisAlive() != false", false, E.isAlive());
    }
    @Test
    public void EnemyFinishTest()
    {
        ArrayList<Vector2> wayp = new ArrayList<Vector2>();
        wayp.add(new Vector2(0.0f, 0.0f));
        wayp.add(new Vector2(1.0f, 1.0f));
        Enemy E = new Runner(wayp, 1);
        assertEquals("E.getIsFinished() != false", false, E.isFinished());
        E.tick(); 
        assertEquals("E.getIsFinished() != true", true, E.isFinished());
        Enemy E2 = new Runner(wayp, 1);
        assertEquals("E.getIsFinished() != false", false, E2.isFinished());
        E2.finish();
        assertEquals("E.getIsFinished() != true", true, E2.isFinished());
    }
    
    
    /*public class EnemyImpl extends Enemy {

        public EnemyImpl() {
            super(null, 0);
        }

        public void tick() {
        }

        public void render(Graphics g) {
        }
    }
     */
}
