package graphics;
import java.awt.Graphics2D;
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
}
