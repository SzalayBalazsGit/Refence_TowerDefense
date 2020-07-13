/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.states;

import java.awt.Graphics;
import vcad.Handler;
import vcad.input.KeyManager;
import vcad.input.MouseManager;
import vcad.levels.Level;

/**
 *
 * @author hzb59
 */
public class GameState extends State {
    /*
        A játékstátusz , itt tároljuk és hozzuk létre a szintet. Itt vizsgáljuk meg, hogy mikor van vége egy szintnek, és váltjuk a következő szintet.
    
    */
    private Level level;
    private MouseManager mouse;
    private KeyManager keys;
    //private boolean finished = false;
    //@param handler - amit átadunk majd a levelnek és lejebb
    public GameState(Handler handler) {
        super(handler);
        level = new Level(handler);
        handler.setLevel(level);
    }
    
    
    
    
    @Override
    public void tick() {
        level.tick();
        if(level.isFailed()){
            level = new Level(handler);
            handler.setLevel(level);
        }
        if(level.isFinished()){
            handler.setCrntLevel(handler.getCrntLevel()+1);
            //System.out.println(handler.getCrntLevel());
            if(handler.getCrntLevel()<6){
            level = new Level(handler);
            handler.setLevel(level);
                System.out.println(handler.getCrntLevel());
            }else{
                
                finished = true;
                handler.getGame().menuState = new MenuState(handler);
                State.setState(handler.getGame().menuState);
               
            }
        }
    }
    @Override
    public void render(Graphics g) {
        level.render(g);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
}
