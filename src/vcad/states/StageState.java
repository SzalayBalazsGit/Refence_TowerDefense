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
public class StageState extends State{
    //nem lett felhasználva
    public StageState(Handler handler) {
        super(handler);
    }


    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
