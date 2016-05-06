package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.Integrator;

public class ControlRodBundle extends GameObject{
	static BufferedImage sprite = null;
	public static final String path = "res/rods.png";
	/**
	 * Constructor
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 * @param size The size of the bundle
	 */
	public ControlRodBundle(int x, int y, int size){
		super(x, y, size, size);
		try {
			if(sprite==null){
				sprite = ImageIO.read(new File(path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Empty constructor, should not be used
	 */
	public ControlRodBundle(){
		xSize = ySize = 15;
		bounds = new Rectangle(x, y, xSize, ySize);
		try {
			if(sprite==null){
				sprite = ImageIO.read(new File(path));
				//green = sprite.getSubimage(0, 0, xSize, ySize);
				//orange = sprite.getSubimage(0, ySize, xSize, ySize);
				//red = sprite.getSubimage(0, ySize*2, xSize, ySize);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the width + the X coordinate
	 */
	public int getImageX(){
		return xSize + this.x;
	}
	/**
	 * Returns the height + the Y coordinate
	 */
	public int getImageY(){
		return ySize + this.y;
	}
	/**
	 * Draws the object
	 */
	public void drawObj(Graphics2D g){
		g.drawImage(sprite, getX() + (Integrator.intLastXOffset), getY() + Integrator.intLastYOffset, getImageX() + Integrator.intLastXOffset,  getImageY() + Integrator.intLastYOffset, 0, 0, sprite.getWidth(), sprite.getHeight(), null);
	}
}
