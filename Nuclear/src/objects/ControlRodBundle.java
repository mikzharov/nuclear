package objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.Integrator;

public class ControlRodBundle extends GameObject{
	static BufferedImage sprite = null;
	static BufferedImage red = null;
	static BufferedImage orange = null;
	static BufferedImage green = null;
	private Rectangle bounds;
	public ControlRodBundle(int x, int y){
		bounds = new Rectangle(x, y, 15, 15);
		try {
			if(sprite==null){
				sprite = ImageIO.read(new File("res/control.jpg"));
				green = sprite.getSubimage(0, 0, 15, 15);
				orange = sprite.getSubimage(0, 15, 15, 15);
				red = sprite.getSubimage(0, 30, 15, 15);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x=x;
		this.y=y;
	}
	public ControlRodBundle(){
		bounds = new Rectangle(x, y, 15, 15);
		try {
			if(sprite==null){
				sprite = ImageIO.read(new File("res/control.jpg"));
				green = sprite.getSubimage(0, 0, 15, 15);
				orange = sprite.getSubimage(0, 15, 15, 15);
				red = sprite.getSubimage(0, 30, 15, 15);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getImage(){
		return green;
	}
	public int getImageX(){
		return 15 + this.x;
	}
	public int getImageY(){
		return 15 + this.y;
	}
	public void mouseClicked(MouseEvent e){
		bounds.setLocation(x+Integrator.int_last_x_offset, y + Integrator.int_last_y_offset);
		AffineTransform g = new AffineTransform();
		g.translate(Integrator.x/2.0, Integrator.y/2.0);
		g.scale(Integrator.scale, Integrator.scale);
		g.translate(-Integrator.x/2.0, -Integrator.y/2.0);
		Shape temp = g.createTransformedShape(bounds);
		if(temp.contains((e.getX()), (e.getY()))){
			//Hit
		}
		bounds.setLocation(x, y);
	}
	public void drawObj(Graphics2D g){
		g.drawImage(green, this.getX() + (Integrator.int_last_x_offset), this.getY() + Integrator.int_last_y_offset, x + 15 + Integrator.int_last_x_offset,  this.getImageY() + Integrator.int_last_y_offset, 0, 0, 15, 15, null);
	}
}
