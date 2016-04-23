package graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

import logic.Integrator;

//This class is just a shell class for other classes to inherit methods from, the code is actually written
//In individual classes such as the UIText class etc.
public class UIComponent {
	protected boolean usable=false;
	public MouseAdapter mouse;
	protected String text;
	public KeyAdapter key;
	public static int defaultHeight = 110;
	protected boolean active = false;
	protected boolean visible = true;
	protected int xSize;
	protected int ySize;
	protected int x;
	protected int y;
	protected Color color = new Color(174, 174, 207);
	protected Font font = new Font("Impact", Font.PLAIN, 96);
	protected Rectangle bounds;
	public UIComponent(int xPos, int yPos, int xSize, int ySize) {
		throw new UnsupportedOperationException();
	}
	public UIComponent(){
		//Do nothing
	}
	public void drawObj(Graphics2D g) {
		throw new UnsupportedOperationException();
	}
	public void setText(String text) {
		throw new UnsupportedOperationException();
	}
	public String getText(){
		return text;
	}
	public void setVisible(boolean bool){
		active = bool;
		visible = bool;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getWidth(){
		return xSize;
	}
	public int getHeight(){
		return ySize;
	}
	public Rectangle getBounds(){
		return bounds;
	}
	public void setFont(Font font){
		this.font = font;
	}
	public void setFontSize(int size) {
		this.font = new Font("Impact", Font.PLAIN, size); 
	}
	public void setColor(Color color){
		this.color = color;
	}
	public void changeColor(int opacity){
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
	}
	public boolean getActive(){
		return active;
	}
	public boolean getVisible(){
		return visible;
	}
	public void setUsableDuringPaused(boolean usable){
		this.usable = usable;
	}
	protected boolean usable(){
		if(this.active && (!Integrator.paused||usable) && visible){
			return true;
		}
		return false;
	}
}
