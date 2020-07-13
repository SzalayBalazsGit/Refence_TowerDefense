/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import vcad.Handler;
import vcad.buildings.Building;
import vcad.buildings.Gunner;
import vcad.buildings.Rocketeer;
import vcad.buildings.Sniper;
import vcad.enemies.Brute;
import vcad.enemies.Enemy;
import vcad.enemies.Runner;
import vcad.enemies.Slasher;
import vcad.enemies.Swarm;
import vcad.enemies.Swarmer;
import vcad.gfx.Assets;
import vcad.gfx.ImageLoader;
import vcad.input.MouseManager;
import vcad.states.MenuState;
import vcad.ui.ClickListener;
import vcad.ui.UIImageButton;
import vcad.ui.UIManager;
import vcad.ui.UIObject;
import vcad.ui.UITowerButton;
import vcad.utils.Utils;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public class Level {

    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> pendingEnemies;
    private Handler handler;
    private String[] wawes;
    private int currentWawe = -1;
    private ArrayList<Vector2>[] wayPoints;
    private ArrayList<Vector2> buildingPoints;
    private ArrayList<Building> buildings;
    private ArrayList<Building> pendingBuildings;
    //private LevelManager manager;
    private UIManager uimanager;
    private boolean waweInProg;
    private int health;
    private int money;
    private int counter;
    private int actE;
    private boolean anyAlive = true;
    private boolean failed = false;
    private boolean finished = false;
    private boolean stop = false;
    private int builID;
    private int wayId = 0;
    private int wayIdMax;
    BufferedImage bg;

    //A levelnek létrehozása, itt töltjük be a hátteret, illetve az építmények pontjait, továbbá az útvonalat, amin az ellenségek lesznek.
    //A waweStart button elindítja a következő hullámot, amennyiben nincs épp folyamatban hullám és még van következő hullám
    //@param handler - a handler, amelyet átadunk majd a levelben lévő tagoknak akiknek szüksége van rá (UI, Swarmer)
    public Level(Handler handler) {
        this.handler = handler;
        buildings = new ArrayList<Building>();
        pendingBuildings = new ArrayList<Building>();
        health = 10;
        money = 1000;
        bg = ImageLoader.loadImgae("/level" + handler.getCrntLevel() + "/background.png");
        builID = 1;
        enemies = new ArrayList<Enemy>();
         pendingEnemies = new ArrayList<Enemy>();
        buildingPoints = new ArrayList<Vector2>();
        loadLevel("level" + handler.getCrntLevel());
        uimanager = new UIManager();
        loadbuilding();
        handler.getMouseManager().setUIManager(uimanager);
        uimanager.addObject(new UIImageButton(1000, 500, 190, 190, Assets.waweStartBtn, new ClickListener() {

            @Override
            public void onColick() {
                if (!waweInProg && stop == false) {
                    waweStart();
                }
            }
        }));

    }

    //Betölti az építmények pontjait, és létrehoz egy "Torony UI-t" az adott ponton.
    public void loadbuilding() {
        for (Vector2 p : buildingPoints) {
            uimanager.addObject(new UITowerButton(p.getX() - 40, p.getY() - 40, builID, handler));
            builID++;
        }
    }

    /* 
        Az adott hullám ellenségeit az enemies-ben tároljuk, és minden új hullámnál kürítjük ezt.
        Ha egy ellenség meghal, akkor benne marad a ArrayListben de deaktiválódik és nem tickel és renderel
        Azért kell benne tartanunk hullám közben is, hogy ne kapjunk hibát arra, ha iterálás közben akarunk eltávolítani egy elemet.
     */
    public void waweStart() {
        enemies.clear();
        waweInProg = true;
        currentWawe++;
        actE = 0;
        spawnEnemy();
    }

    public void changeHealth(int x) {
        health += x;
    }

    public void money(int x) {
        money += x;
    }

    /*
        Tickeljük az aktív ellenségeket, majd az építmenyeket, és végül vizsgáljuk hogy a szintet elbuktuk vagy végig vittük-e.
        Amennyiben van még ellenség életben az adott hullámból (enemies ArrayList) addig folyamatban van a hullám.
     */
    public void tick() {
        if (!stop) {
            for(Building b : pendingBuildings){
                buildings.add(b);
            }
            pendingBuildings.clear();
            if (waweInProg) {
                anyAlive = false;
                if (counter == 120) {
                    counter = 0;
                    spawnEnemy();
                    //System.out.println("spawn");
                } else {
                    counter++;
                }
                for(Enemy e : pendingEnemies){
                    enemies.add(e);
                }
                pendingEnemies.clear(); 
                for (Enemy e : enemies) {
                    if (!e.isAlive() && !e.isPayed()) {
                        this.money += 50;
                        e.setPayed(true);
                    }
                    if (e.isAlive() && !e.isFinished()) {
                        anyAlive = true;
                        e.tick();
                        if (e.isFinished()) {
                            health--;
                            //enemies.remove(e);
                            //System.out.println(enemies.size());
                        }
                    }
                }
                if (!anyAlive) {
                    waweInProg = false;
                }
            }
            for (Building b : buildings) {
                b.tick(enemies);
            }
        }
        if (health == 0) {
            failLevel();
            stop = true;
        }
        if (currentWawe == wawes.length - 1 && anyAlive == false) {
            finishLevel();
            stop = true;
        }

    }

    public void render(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        /* for (Building b : buildings) {
            b.render(g);
        }*/
        for (Iterator<Building> iterator = buildings.iterator(); iterator.hasNext();) {
            Building b = iterator.next();
            b.render(g);
        }

        for (Enemy e : enemies) {
            if (e.isAlive() && !e.isFinished()) {
                e.render(g);
            }
        }

        uimanager.render(g);
        g.setFont(g.getFont().deriveFont(25.0f));
        g.drawImage(Assets.healthUI, 20, 460, null);
        g.setColor(Color.red);
        g.drawString("" + health, 185, 540);
        g.drawImage(Assets.moneyUI, 20, 600, null);
        g.setColor(Color.green);
        g.drawString("" + money, 185, 660);
    }

    //Betoltjük a szint adatait a txt fájlokból egy tömb tagjaiként és a megfelelő adatokat megfelelő helyre írjuk.
    //@param path - a level adatainak betöltési útvonala
    public void loadLevel(String path) {
        String file = Utils.loadFileAsString("res/" + path + "/levelData.txt");
        String[] tokens = file.split("\\s+");
        int n = Utils.parseInt(tokens[0]);
        int m = Utils.parseInt(tokens[1]);
        wayPoints = new ArrayList[m];
        wayIdMax = m;
        for (int i = 0; i < m; i++) {
            wayPoints[i] = new ArrayList<Vector2>();
        }
        int tc = 2;
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                wayPoints[j].add(new Vector2(Utils.parseInt(tokens[tc]), Utils.parseInt(tokens[tc + 1])));
                tc += 2;
            }
        }
        n = Utils.parseInt(tokens[tc++]);
        for (int i = 0; i < n; i++) {
            buildingPoints.add(new Vector2(Utils.parseInt(tokens[tc]), Utils.parseInt(tokens[tc + 1])));
            tc += 2;
        }
        tokens = null;
        file = Utils.loadFileAsString("res/" + path + "/enemyData.txt");
        tokens = file.split("\\s+");
        n = Utils.parseInt(tokens[0]);
        wawes = new String[n];
        for (int i = 0; i < n; i++) {
            wawes[i] = tokens[i + 1];
        }
    }

    //A soron következő Enemy lespawnolja a felvett adatok alapján, továbbá hibakezelés túlindexelés eseten.
    //Mivel az adatokban több utvonal is fel van véve, az ellensegeket tobb utvonalon indutjuk el. Ezek kozott egy intet "forgatunk" amely eldonti
    //hogy  a waypoits tömbnek melyik indexű pontlistajat kapja meg az ellenseg
    public void spawnEnemy() {
        Enemy e;
        if (wayId == wayIdMax - 1) {
            wayId = 0;
        } else {
            wayId++;
        }

        if (actE < wawes[currentWawe].length()) {
            switch (wawes[currentWawe].charAt(actE)) {
                case '1':
                    e = new Slasher(wayPoints[wayId], 1);
                    break;
                case '2':
                    e = new Runner(wayPoints[wayId], 1);
                    break;
                case '3':
                    e = new Brute(wayPoints[wayId], 1);
                    break;
                case '4':
                    e = new Swarmer(wayPoints[wayId], 1, handler);
                    break;
                case '5':
                    e = new Swarm(wayPoints[wayId], 1);
                    break;
                default:
                    e = new Slasher(wayPoints[wayId], 1);
                    break;
            }
            enemies.add(e);
            actE++;
            //anyAlive = true;
        }
    }

    public Enemy getEnemy(Enemy e) {
        return e;
    }

    public Building getBuilding(Building b) {
        return b;
    }

    

    //Ha sikerül vagy nem sikerül egy szint akkor egy új gombot kap a UI és ehhez mérten funkcionál az új adott gomb
    public void failLevel() {
        uimanager.addObject(new UIImageButton(480, 300, 248, 112, Assets.restartBtn, new ClickListener() {
            @Override
            public void onColick() {
                failed = true;
                stop = true;
            }
        }));
    }

    ;
    public void finishLevel() {
        //System.out.println(handler.getCrntLevel());
        if (handler.getCrntLevel() < 5) {
            uimanager.addObject(new UIImageButton(480, 300, 248, 112, Assets.mmStartBtn, new ClickListener() {
                @Override
                public void onColick() {
                    finished = true;
                    stop = true;
                }
            }));
        } else {
            finished = true;
        }
    }

    ;
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void decHealth() {
        health--;
    }

    public boolean isFailed() {
        return failed;
    }

    public boolean isFinished() {
        return finished;
    }

    public void addBuilding(Building b) {
        pendingBuildings.add(b);
    }

    public UIManager UI() {
        return uimanager;
    }
    //@param i - keresendő épület azonosítója
    public boolean checkForBuilding(int i) {
        boolean f = false;
        for (Building b : buildings) {
            if (b.getID() == i) {
                f = true;
            }
        }
        return f;
    }

    public int getMoney() {
        return money;
    }

    public void decMoney(int x) {
        this.money += x;
    }
    //@param i - lekérendő épület azonosítója
    public Building getBuildingByID(int i) {
        for (Building b : buildings) {
            if (b.getID() == i) {
                return b;
            }
        }
        return null;
    }

    public void addEnemy(Enemy e) {
        pendingEnemies.add(e);
    }
}
