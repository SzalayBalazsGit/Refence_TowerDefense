/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author hzb59
 */
public class UIManager {
        //Ebben kezeljük a felvett UI gombjainkat
	private ArrayList<UIObject> objects;
	
	public UIManager( ){

		objects = new ArrayList<UIObject>();
	}
	
	public void tick(){
		for(UIObject o : objects)
                    if(o.active)
			o.tick();
	}
	
	public void render(Graphics g){
		for(UIObject o : objects)
                     if(o.active)
			o.render(g);
	}
	
	
	
	public void onMouseRelease(MouseEvent e){
		for(UIObject o : objects)
			o.onMouseRelease(e);
	}
	
	public void addObject(UIObject o){
		objects.add(o);
	}
	
	public void removeObject(UIObject o){
		objects.remove(o);
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

    public void onMouseMove(MouseEvent e) {
       for(UIObject o : objects)
            o.onMouseMove(e);
    }
    
    public void setActive(){
        for(UIObject o : objects){
             if(!(o.active))
                 o.active = true;
        }
    }
	
}
