/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.buildings;

import java.awt.Graphics;
import java.util.ArrayList;
import vcad.enemies.Enemy;

/**
 *
 * @author hzb59
 */
public class Base extends Building{
    //Nem lett felhasználva
    
    @Override
    public void tick(ArrayList<Enemy> enemies) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void getEnemy() {
    }
    
    public void Build(){
        
    }

    @Override
    public void specialize(int x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
