/*
 Brendan Aucoin
 2018/04/13
 this is a class that any object you want to click on inherits from.
 its essentially just a rectangle.
 */
package mouse;

import java.awt.Rectangle;

public abstract class ClickableObject{
  private float x,y;
  private int width,height;
  public ClickableObject() {}
  public ClickableObject(float x,float y,int width,int height){
    this.x =x;
    this.y = y;
    this.width = width;
    this.height  =height;
  }
  
  public Rectangle getBounds() {return new Rectangle((int)x,(int)y,width,height);}
  public void setX(float x){this.x = x;}
  public void setY(float y){this.y = y;}
  public void setWidth(int width){this.width = width;}
  public void setHeight(int height){this.height = height;}
  public float getX(){return x;}
  public float getY(){return y;}
  public int getWidth(){return width;}
  public int getHeight(){return height;}
}