/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vcad.utils;

/**
 *
 * @author hzb59
 */
public class Vector2 {
   
    private float x;
    private float y;
    
    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }
    public double distance(Vector2 b){
        float v0 =  b.x - this.x;
        float v1 = b.y - this.y;
        return Math.sqrt(v0*v0 + v1*v1);
    }
    public  double length(Vector2 a){
        return Math.sqrt(a.x*a.x + a.y*a.y);
    }
    public void normalize(){
        //Vector2 n = new Vector2((float)(a.x/length(a)),(float)(a.y/length(a)));
        this.x = (float)(this.x/length(this));
        this.y = (float)(this.y/length(this));
    }
    public Vector2 plus(float x,float y){
        this.x+=x;
        this.y+=y;
        return this;
    }
    public void print(){
        System.out.println(this.x+" "+this.y);
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public void addX(float a){
        this.x+=a;
    }
    public void addY(float a){
        this.y+=a;
    }
}
