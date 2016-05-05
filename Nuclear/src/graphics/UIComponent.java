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
	public boolean active = false;
	protected boolean visible = true;
	protected int xSize;
	protected int ySize;
	protected int x;
	protected int y;
	protected int x_displace=20; //default setting
	protected int y_displace=90; //default setting
	protected Color color = new Color(174, 174, 207);
	protected Color textColor = Color.black; //default setting
	protected Font font = new Font("Impact", Font.PLAIN, 96);
	protected Rectangle bounds;
	protected boolean movable = true;
	public UIComponent(int xPos, int yPos, int xSize, int ySize) {
		throw new UnsupportedOperationException();
	}
	public UIComponent(){
		//Do nothing
	}
	public void setTextDisplacement(int x, int y) {
		this.x_displace=x;
		this.y_displace=y;
	}
	public void drawObj(Graphics2D g) {
		throw new UnsupportedOperationException();
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText(){
		return text;
	}
	public void setVisible(boolean bool){
		visible = bool;
	}
	public void setLocation(int x2, int y2) {
		x=x2;
		y=y2;
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
	public void setTextColor(Color co) {
		this.textColor = co;
	}
	public void setMovable(boolean move){
		movable = move;
	}
	public void setActive(boolean bol){
		active = bol;
	}
}
