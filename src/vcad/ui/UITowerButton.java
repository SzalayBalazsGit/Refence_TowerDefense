/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import vcad.Handler;
import vcad.buildings.Building;
import vcad.buildings.Gunner;
import vcad.buildings.Rocketeer;
import vcad.buildings.Sniper;
import vcad.gfx.Assets;
import vcad.utils.Vector2;

/**
 *
 * @author hzb59
 */
public class UITowerButton extends UIObject{
    /* 
        A toronyfejlesztések alapja. Lényegében tartalmaz magában még 3 UIManager-t amelyek reprezentálják az épületek stádiumait:
        A épitesi stádium
        A fejlesztési stádium
        A specializálüdü stádium
        A torony szintjétől függően aktiválódik az adott stádium UIManagere, és átadjuk neki az Egeret, tehát csak azon az adott UserInterfacen
        figyeljük meg az egeret, ezáltal megelőzve, hogy a többi UI-al átfedése legyen és biztosítva egyszerre csak egy UI legyen a játékban aktív.
        Minden UIManagerhez letrehozzuk a gombokat, és ha kattintunk egy gombra elvégezzünk az adott művelettel,
        majd visszaadjuk az egeret a a Levelben lévő UImanagernek így az lesz az aktív.
        Egy adott UITowerButtont egy toronnyal az ID-je alapján kötünk össze, és vizsgálatoknál a handler osztállyal a Levelből kérjük le ( a tornyot).
        A torony fejlesztését már maga a torony végzi.

    */
    private BufferedImage images;
    private ClickListener clicker;
    private int tID;
    private boolean activeChoosing = false;
    private boolean activeUpgrade = false;
    private boolean activeSpec = false;
    private Handler handler;
    private UIManager buttons;
    private UIManager upgrade;
    private UIManager special;
    private Building tower;
    private int dpDmg;
    private int dpRng;
    /*
        @param x,y - pozíció
        @param tID - torony azonosítója, amelyhez a gomb tartozik
        @param handler - handler, amivel az egérinputot kezelhejtük
    */
    public UITowerButton(float x, float y,int tID, Handler handler) {
        super(x, y, 150,150);
        this.active = true;
        this.images = Assets.towerCircle;
        this.tID = tID;
        this.clicker = new ClickListener() {
            @Override
            public void onColick() {
               if(!handler.getLevel().checkForBuilding(tID)){
                activeChoosing = true;
                handler.getMouseManager().setUIManager(buttons);
               }else if(handler.getLevel().getBuildingByID(tID).getLevel()<3){
                   activeUpgrade = true;
                   handler.getMouseManager().setUIManager(upgrade);
               }else if(handler.getLevel().getBuildingByID(tID).getLevel()==3){
                   activeSpec = true;
                   handler.getMouseManager().setUIManager(special);
               }
            }
        };
        this.handler = handler;
        buttons = new UIManager();
        upgrade = new UIManager();
        special = new UIManager();
        buttons.addObject(new UIImageButton(x+5,y+15,30,30,Assets.sniperBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                Building b = new Sniper(new Vector2(x+35,y+35),tID);
                if(handler.getLevel().getMoney()>=200){
                handler.getLevel().addBuilding(b);
                handler.getLevel().decMoney(-200);
               dpDmg =25;
                }
                activeChoosing = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
                BufferedImage[] image1 = Assets.reaperBtn;
                BufferedImage[] image2 = Assets.tricksterBtn;
                loadSpec(image1,image2);
                
            }
        }));
        buttons.addObject(new UIImageButton(x+60,y,30,30,Assets.rocketeerBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                Building b = new Rocketeer(new Vector2(x+35,y+35),tID);
                if(handler.getLevel().getMoney()>=200){
                handler.getLevel().addBuilding(b);
                handler.getLevel().decMoney(-200);
               dpDmg = 15;
                }
                activeChoosing = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
                BufferedImage[] image1 = Assets.grenaderBtn;
                BufferedImage[] image2 = Assets.freezerBtn;
                loadSpec(image1,image2);
                
            }
        }));
        buttons.addObject(new UIImageButton(x+115,y+15,30,30,Assets.gunnerBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                Building b = new Gunner(new Vector2(x+35,y+35),tID);
                if(handler.getLevel().getMoney()>=200){
                handler.getLevel().addBuilding(b);
                handler.getLevel().decMoney(-200);
                dpDmg = 12;
                }
                activeChoosing = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
                BufferedImage[] image1 = Assets.burnerBtn;
                BufferedImage[] image2 = Assets.speedsterBtn;
                loadSpec(image1,image2);
            }
        }));
        buttons.addObject(new UIImageButton(x+60,y+130,30,30,Assets.exitBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                activeChoosing = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
            }
        }));
        upgrade.addObject(new UIImageButton(x+60,y,30,30,Assets.upgradeBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                //System.out.println(tID+" upgraded");
                if(handler.getLevel().getMoney()>=150){
                handler.getLevel().getBuildingByID(tID).upgrade();
                handler.getLevel().decMoney(-150);
                }
                refresh();
                activeUpgrade = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
            }
        }));
        upgrade.addObject(new UIImageButton(x+60,y+130,30,30,Assets.exitBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                activeUpgrade = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
            }
        }));
    }
    
    @Override
    public void tick() {
        if(activeChoosing)
            buttons.tick();
        if(activeUpgrade)
            upgrade.tick();
        if(activeSpec)
            special.tick();
    }

    @Override
    public void render(Graphics g) {
     if(hovering){
            g.drawImage(images, (int)x,(int)y,width,height,null);
            //buttons.render(g);
             g.setFont(g.getFont().deriveFont(25.0f));
            g.setColor(Color.red);
            g.drawString(" "+dpDmg, (int)x+58,(int)y-5);
            
        }
     if(activeChoosing)
            buttons.render(g);
     if(activeUpgrade)
         upgrade.render(g);
     if(activeSpec)
            special.render(g);
    }

    @Override
    public void onClick() {
        clicker.onColick();
        
    }
        //A specializációs UI-nak a gombjait attól függően töltjük be, hogy milyen alaptornyot hozunk létre, mivel minden toronynak
        //más a specializációja, és más gombot kell a specializációhoz renderelni
        /*
        @param image1,image2 - a gombok betölteni való kinézetei
        */
    public void loadSpec(BufferedImage[] image1,BufferedImage[] image2){
     special.addObject(new UIImageButton(x+5,y+15,30,30,image1,new ClickListener() {
            @Override
            public void onColick() {
                //spec1
                if(handler.getLevel().getMoney()>=300){
                handler.getLevel().decMoney(-300);
                handler.getLevel().getBuildingByID(tID).specialize(1);
                }
                refresh();
                activeSpec = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
            }
        })); 
     special.addObject(new UIImageButton(x+115,y+15,30,30,image2,new ClickListener() {
            @Override
            public void onColick() {
                //spec2
                  if(handler.getLevel().getMoney()>=300){
                handler.getLevel().decMoney(-300);
                handler.getLevel().getBuildingByID(tID).specialize(2);
                }
                refresh();
                activeSpec = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
            }
        }));
     special.addObject(new UIImageButton(x+60,y+130,30,30,Assets.exitBtn,new ClickListener() {
            @Override
            public void onColick() {
                //System.out.println("asd");
                activeSpec = false;
                handler.getMouseManager().setUIManager(handler.getLevel().UI());
            }
        }));
    }
    public void refresh(){
        
        this.dpDmg = handler.getLevel().getBuildingByID(tID).getDmg();
    }
}
