package graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

//This class is just a shell class for other classes to inherit methods from, the code is actually written
//In individual classes such as the UIText class etc.
public class UIComponent {
	public MouseAdapter mouse;
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

}
