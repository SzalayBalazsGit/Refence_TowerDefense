/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 *
 * @author hzb59
 */
public abstract class UIObject{
    /*
        A saját gombjaink ősosztálya. Ennek van egy Rectangle-je amellyel megadjuk a gomb felületét, tehát, hogy mekkora területen érzékeli
        az egeret maga felett.
     */
    protected float x,y;
    protected int width,height;
    protected Rectangle bounds;
    protected boolean hovering = false;
    protected boolean active = true;
    /*
    @param x,y - pozicio
    @param width,height - méret
    */
    public UIObject(float x, float y, int width, int height){
        this.x=x;
        this.y=y;
        this.height = height;
        this.width = width;
        bounds = new Rectangle((int)x,(int)y,width,height);
    }

    public abstract void tick();
    
    public abstract void render(Graphics g);
    
    public abstract void onClick();
    
    public void onMouseMove(MouseEvent e){
        if(bounds.contains(e.getX(),e.getY()))
            hovering = true;
        else
            hovering = false;
    }
    public void onMouseRelease(MouseEvent e){
        if(hovering)
            onClick();
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
