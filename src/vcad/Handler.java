/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad;

import vcad.input.MouseManager;
import vcad.levels.Level;

/**
 *
 * @author hzb59
 */
public class Handler {
    
    private Game game;
    private Level level;
    private int crntLevel;
   
    /*Ez az osztály arra szolgál, hogy ha egy másik osztálnyak muszáj felfelé is látnia, akkor ezzel megteheti  
    */
   
    Handler(Game game) {
        this.game = game;
        crntLevel = 1;
        
    }
    

    public int getWidth(){
        return this.game.getWdith();
    }
    
    public int getHeight(){
        return this.game.getHeight();
    }
    
    public void setGame(Game game){
        this.game = game;
    }
    public Game getGame(){
        return this.game;
    }
    public void setLevel(Level level){
        this.level = level;
    }
    public Level getLevel(){
        return this.level;
    }

    public MouseManager getMouseManager() {
        return this.game.getMouseManager();
    }

    public int getCrntLevel() {
        return crntLevel;
    }

    public void setCrntLevel(int crntLevel) {
        this.crntLevel = crntLevel;
    }
    
    
}
