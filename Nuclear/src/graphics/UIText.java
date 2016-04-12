package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class UIText extends UIComponent{//This will make the UIText be also recognized as a UIComponent
	private String text;
	private int xSize;
	private int ySize;
	private int x;
	private int y;
	private Color color = new Color(204, 204, 255);

	private Font font = new Font("Impact", Font.PLAIN, 96);
	public UIText(int xPos, int yPos, int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.x = xPos;
		this.y = yPos;
		//SystemUI.addMouseListener(this); //how do I do this?
	}
	
	public void drawObj(Graphics2D g) {
		
		Font oldFont = g.getFont();
		Color oldColor = g.getColor();//Saves pervious information
		Stroke oldStroke = g.getStroke();
		
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(5));
		g.fillRect(x, y, x+xSize, y+ySize);
		
		g.setColor(color);
		g.setFont(font);
		g.setStroke(new BasicStroke(5));
		g.fillRect(x+2, y+2, x+xSize-4, y+ySize-4);
		
		
		
		g.setColor(oldColor);
		g.drawString(text, x+20, y+90);
		g.setFont(oldFont);//Restores pervious information
		g.setStroke(oldStroke);
		
	}
	
	public void setText(String text) {
		this.text = text;
	}

}
