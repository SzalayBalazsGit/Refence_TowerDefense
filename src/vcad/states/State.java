/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.states;

import java.awt.Graphics;
import vcad.Handler;
import vcad.input.MouseManager;

/**
 *
 * @author hzb59
 */
public abstract class State {
    // A statuszokat ebből származtatjuk.
    private static State currentState = null;
    protected boolean finished = false;
    
    public static void setState(State s){
        currentState = s;
    }
    
    public static State getState(){
        return currentState;
    }
    
    //Class
    
    protected Handler handler;
    
    public State(Handler handler){
        this.handler= handler;
    }
    public abstract void tick();
    
    public abstract void render(Graphics g);

    public boolean isFinished() {
        return finished;
    }
    
    
}
