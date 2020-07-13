/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.states;

import java.awt.Graphics;
import vcad.Handler;
import vcad.gfx.Assets;
import vcad.input.MouseManager;
import vcad.ui.ClickListener;
import vcad.ui.UIImageButton;
import vcad.ui.UIManager;

/**
 *
 * @author hzb59
 */
public class MenuState extends State{
    /*
        A menustatusz, itt renderekjük a főmenut, és kezeljük a menus UI-t, amennyiben a szintek gombra kattintunk, renderelődnek és aktivalodnak
        a szintek választása.
    */
    
    private UIManager ui;
    private int level;
    //@param handler - a handler amivel lekérjük az egeret a UI-oknak
    public MenuState(Handler handler) {
        super(handler);
        ui = new UIManager();
        handler.getMouseManager().setUIManager(ui);
        ui.addObject(new UIImageButton(102,100,248,112,Assets.mmStartBtn,new ClickListener() {
            @Override
            public void onColick() {
                handler.getMouseManager().setUIManager(null);
                    handler.getGame().gameState = new GameState(handler);
                 State.setState(handler.getGame().gameState);
            }
        }));
        ui.addObject(new UIImageButton(102,250,248,112,Assets.mmSelectBtn,new ClickListener() {
            @Override
            public void onColick() {
                ui.setActive();
            }
        }));
        ui.addObject(new UIImageButton(102,400,248,112,Assets.mmExitBtn,new ClickListener() {
            @Override
            public void onColick() {
                handler.getMouseManager().setUIManager(null);
                System.exit(0);
            }
        }));
        ui.addObject(new UIImageButton(450, 280, 90, 90, Assets.level1btn, new ClickListener() {
                    @Override
                    public void onColick() {
                        handler.setCrntLevel(1);
                    }
         }));
        ui.addObject(new UIImageButton(750, 280, 90, 90, Assets.level2btn, new ClickListener() {
                    @Override
                    public void onColick() {
                        handler.setCrntLevel(2);
                    }
         }));
        ui.addObject(new UIImageButton(1050, 280, 90, 90, Assets.level3btn, new ClickListener() {
                    @Override
                    public void onColick() {
                        handler.setCrntLevel(3);
                    }
         }));
        ui.addObject(new UIImageButton(600, 500, 90, 90, Assets.level4btn, new ClickListener() {
                    @Override
                    public void onColick() {
                        handler.setCrntLevel(4);
                    }
         }));
        ui.addObject(new UIImageButton(900, 500, 90, 90, Assets.level5btn, new ClickListener() {
                    @Override
                    public void onColick() {
                        handler.setCrntLevel(5);
                    }
         }));
    }

    

    @Override
    public void tick() {
        ui.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.menuBg, 0, 0, null);
        ui.render(g);
    }
    
}
