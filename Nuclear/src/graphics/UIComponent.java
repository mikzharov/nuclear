package graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

//This class is just a shell class for other classes to inherit methods from, the code is actually written
//In individual classes such as the UIText class etc.
public class UIComponent {
	public MouseAdapter mouse;
	String text;
	public KeyAdapter key;
	public boolean active = false;
	boolean visible = true;
	int xSize;
	int ySize;
	int x;
	int y;
	Color color = new Color(174, 174, 207);
	Font font = new Font("Impact", Font.PLAIN, 96);
	public Rectangle bounds;
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
}
