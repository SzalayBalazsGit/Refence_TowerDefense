/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.levels;

import vcad.ui.UIImageButton;

/**
 *
 * @author hzb59
 */
public class LevelManager {
    //nem lett felhasznalva
    private int money;
    private int lives;
    private UIImageButton waweStart;
    
    public LevelManager(int money,int lives){
        
    }
    
    public void spendMoney(int amount){
        money-=amount;
    }
    
    public int getMoney(){
        return money;
    }
    
    public void enemySucceed(){
        lives--;
    }
    
    public int getLives(){
        return lives;
    }
}

