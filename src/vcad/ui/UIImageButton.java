/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author hzb59
 */
public class UIImageButton extends UIObject {
    /*Az összes gombnak amit használunk az alapja
        Működési elve, hogy van két állapota, amikor felette van az egér, és amikor nincs.
        Ha felette van akkor A képet renderel, ha nincs felette akkor B képet.
        Továbbá van ClickListener, amelyet mindig a gomb létrahozásakor adunk meg New-ként és a gomb létrehozásánál adjuk meg
        mi legyen a gomb funkciója.
    
    */
    private BufferedImage[] images;
    private ClickListener clicker;
    /*@param x,y - pozíció a ablakban
      @param width,heigth - gomb méretei
      @param images - a gomb kinézete
      @param clicker - egy clicklistener, ami figyeli, hogy mikor kattintunk a gombra
    */
    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListener clicker) {
        super(x, y, width, height);
        this.images = images;
        this.clicker = clicker;
    }
    /*@param x,y - pozíció a ablakban
      @param width,heigth - gomb méretei
      @param images - a gomb kinézete
      @param clicker - egy clicklistener, ami figyeli, hogy mikor kattintunk a gombra
    */
     public UIImageButton(float x, float y, int width, int height, BufferedImage images, ClickListener clicker) {
        super(x, y, width, height);
        BufferedImage[] tmpImg= new BufferedImage[2];
        tmpImg[0] = images;
        tmpImg[1] = images;
        this.images = tmpImg;
        this.clicker = clicker;
        this.active = false;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void onClick() {
        if(active)
        clicker.onColick();
    }

    @Override
    public void render(Graphics g) {
        if(hovering){
            g.drawImage(images[1], (int)x,(int)y,width,height,null);
        }else
            g.drawImage(images[0], (int)x,(int)y,width,height,null);
    }
    }
    
