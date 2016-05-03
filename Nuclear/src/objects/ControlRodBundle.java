package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import logic.Integrator;

public class ControlRodBundle extends GameObject{
	static BufferedImage sprite = null;
	public static final String path = "res/rods.png";
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
	public static ArrayList<GameObject> makeColumn(int x1, int y1, int xLoc, int yLoc){
		if(x1<=0||y1<=0){
			throw new UnsupportedOperationException();
		}
		ArrayList<GameObject> all = new ArrayList<GameObject>();
		for(int x2=0; x2 < x1; x2++){
			for(int y2=0; y2 < x1; y2++){
				ControlRodBundle b = new ControlRodBundle();
				b.setX(xLoc + x2 * 20);
				b.setY(yLoc + y2 * 20);
				all.add(b);
			}
		}
		return all;
	}
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
	public int getImageX(){
		return xSize + this.x;
	}
	public int getImageY(){
		return ySize + this.y;
	}
	public void drawObj(Graphics2D g){
		g.drawImage(sprite, getX() + (Integrator.intLastXOffset), getY() + Integrator.intLastYOffset, getImageX() + Integrator.intLastXOffset,  getImageY() + Integrator.intLastYOffset, 0, 0, sprite.getWidth(), sprite.getHeight(), null);
	}
}
