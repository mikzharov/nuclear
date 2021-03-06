package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import objects.UITutorial;
import logic.Integrator;
/**
 * 
 * A class for a custom button
 *
 */
public class UIButton extends UIComponent{
	/**
	 * This variable is true if the button has ever been clicked
	 */
	public boolean clicked = false;
	/**
	 * This constructor creates the button
	 * @param xPos The X position of the button
	 * @param yPos The Y position of the button
	 * @param x1 The width of the button
	 * @param y1 The length of the button
	 */
	public UIButton(int xPos, int yPos, int x1, int y1) {
		this.xSize = x1;
		this.ySize = y1;
		this.x = xPos;
		this.y = yPos;
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
					clicked = true;
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
	 * This method draws the button
	 */
	public void drawObj(Graphics2D g) {
		if(visible){
			Font oldFont = g.getFont();
			Color oldColor = g.getColor();//Saves previous information
				
			g.setColor(Color.black);
			g.fillRect(x, y, xSize, ySize);
				
			g.setColor(color);
			g.setFont(font);
			g.setStroke(new BasicStroke(5));
			g.fillRect(x+2, y+2, xSize-4, ySize-4);
				
			g.setColor(textColor);
			g.drawString(text, x+x_displace, y+y_displace);
			g.setFont(oldFont);//Restores previous information
			g.setColor(oldColor);
		}
	}
	/**
	 * This sets the text of the button
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * This sets the x and y position and updates the boundaries AKA the hit box
	 * @param x The new X position
	 * @param y The new Y position
	 */
	public void setBounds(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
}
