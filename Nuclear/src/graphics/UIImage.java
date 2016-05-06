package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import objects.UITutorial;
import logic.Integrator;

public class UIImage extends UIComponent{
	BufferedImage img = null;
	/**
	 * Constructor
	 * @param xPos X position
	 * @param yPos Y position
	 * @param xSize Width
	 * @param ySize Height
	 * @param path Path of image file
	 */
	public UIImage(int xPos, int yPos, int xSize, int ySize, String path) {
		this.setMovable(false);
		this.x = xPos;
		this.y = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			//Not loaded
		}
		bounds = new Rectangle(x, y, xSize, ySize);
		
		mouse = new MouseAdapter(){
			int nx;
			int ny;
			@Override
			public void mousePressed(MouseEvent e) {
				if((!Integrator.paused||usable) && visible && bounds.contains(e.getPoint())){
					nx = e.getX() - x;
					ny = e.getY() - y;
					active = true;
				}else{
					active = false;
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(movable && usable() && bounds.contains(e.getPoint())){
					setBounds(e.getX() - nx, e.getY() - ny);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if((!Integrator.paused||usable) && visible && bounds.contains(e.getPoint())){
					active = true;
				}else{
					active = false;
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				if(visible && bounds.contains(e.getPoint())){
					if (!UITutorial.reactorTutorialOn && !UITutorial.turbineTutorialOn && !UITutorial.pumpTutorialOn) //this was messing with the color changes in the tutorial
						color = new Color(204, 204, 255);
				}else if(visible){
					if (!UITutorial.reactorTutorialOn && !UITutorial.turbineTutorialOn && !UITutorial.pumpTutorialOn) //this was messing with the color changes in the tutorial
						color = new Color(174, 174, 207);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
	}
	/**
	 * Draws the object
	 * @param g Graphics to be drawn on
	 */
	public void drawObj(Graphics2D g) {
		if(visible && img != null){
			g.drawImage(img, x, y, xSize + x, ySize + y, 0, 0, img.getWidth(), img.getHeight(), null);
		}
	}
	/**
	 * Sets the bounds of the image and X + Y coords
	 * @param x New X position
	 * @param y New Y postiion
	 */
	public void setBounds(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
}
