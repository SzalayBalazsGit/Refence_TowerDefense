/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import vcad.display.Display;
import vcad.gfx.Assets;
import vcad.input.MouseManager;
import vcad.states.GameState;
import vcad.states.MenuState;
import vcad.states.State;

/**
 *
 * @author hzb59
 */
public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
      
        
        
	//states
        public State gameState;
        public State menuState;
        public State stageState;

        
        
        //in
        //private KeyManager keyManager;
        private MouseManager mouseManager;
        
        
        
        //hanfler
        private Handler handler;
        
        //@param title - ablak neve
        //@param widht, height ablak mérete
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
                mouseManager = new MouseManager();
	}

	//Itt inicializáljuk magát a játékablakot, és beállítjuk a stateket.
	private void init(){
		display = new Display(title, width, height);
                Assets.init();
                display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
                handler = new Handler(this);
                gameState = new GameState(handler);
                
                menuState = new MenuState(handler);
               
                State.setState(menuState);
                
                

               
                
	}


    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public void setMouseManager(MouseManager mouseManager) {
        this.mouseManager = mouseManager;
    }
	// A "fő" tick metódus. Szinte minden osztálynak van egy tick metódusa, de ne mindegyik végez különösebb feladatokat
        //hanem csak végrehajtja a benne lévő objektumok tickjeit, így kialakul egy piramis szerű szerkezet
	private void tick(){
            /*keyManager.tick();*/
            if(State.getState() != null){
                State.getState().tick(); 
                /*if(State.getState().isFinished()){
                    menuState = new MenuState(handler);
                }*/
            }
            
		
	}
	//A render szintén ugyanúgy működik mint a tick metódus, csak itt rajzolásra használjuk
        //Minden kirajzoláshoz ugyanazt a g Graphic objektumot használjuk, ezért mindig továbbadjuk ezt lefelé, az alattunk 
        //lévő obejktumnak a hierarchiában
        //
	private void render(){
            
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Draw Here!
		
		g.fillRect(0, 0, width, height);
                
                g.clearRect(0,0,width,height);
                
                if(State.getState() != null)
                    State.getState().render(g);
		bs.show();
		g.dispose();
	}
	//Itt hajtjuk végre igazából magát a tick metódust és itt biztosítjuk
        //hogy 60 frame legyen másodpercenként
	public void run(){
		
		init();
		
                int fps = 60;
                double timePerTick = 1000000000 / fps;
                double delta = 0;
                long now;
                long lastTime = System.nanoTime();
                long timer = 0;
                int ticks = 0;
                
		while(running){
                    now = System.nanoTime();
                    delta += (now - lastTime) / timePerTick;
                    timer += (now - lastTime);
                    
                    lastTime = now;
                    if(delta>=1){
			tick();
			render();
                        delta--;
                        ticks++;
                    }
                    if(timer>=1000000000){
                        System.out.println("FPS: " + ticks);
                        timer = 0;
                        ticks = 0;
                    }
		}
		
		stop();
	}
        
      /*  public KeyManager getKeyManager(){
            return keyManager;
        }*/
        
        public int getWdith(){
            return width;
        }
        public int getHeight(){
            return height;
        }
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        }
}
