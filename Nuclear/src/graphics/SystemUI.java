package graphics;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Graphics;


import logic.Integrator;
import main.Main;

public class SystemUI {
	
	private int xSize;
	private int ySize;
	private int xPos;
	private int yPos;
	private Color objColor;
	private float alpha = 0.05f;
	
	public SystemUI(int xSize, int ySize, int xPos, int yPos, Color objColor) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.xPos = xPos;
		this.yPos = yPos;
		this.objColor = objColor;
		
		//SystemUI.addMouseListener(this); //how do I do this?
	}
	
	public void drawObj(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setComposite(AlphaComposite.getInstance(
		            AlphaComposite.SRC_OVER, alpha));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(objColor);
		g2d.drawRect(xPos, yPos, xSize, ySize);
	}
	
	public void setText(Graphics g, String text, int x, int y) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font font = new Font("Serif", Font.PLAIN, 96);
	    g2d.setFont(font);

	    g2d.drawString(text, x, y); 
	}

}
