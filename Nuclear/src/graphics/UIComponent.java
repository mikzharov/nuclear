package graphics;
import java.awt.Graphics2D;

//This class is just a shell class for other classes to inherit methods from, the code is actually written
//In individual classes such as the UIText class etc.
public class UIComponent {
	public UIComponent(int xSize, int ySize, int xPos, int yPos) {
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
