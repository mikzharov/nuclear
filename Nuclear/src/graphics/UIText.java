package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.Integrator;

public class UIText extends UIComponent{
	public UIText(int xPos, int yPos, int x1, int y1) {
		xSize = x1;
		ySize = y1;
		x = xPos;
		y = yPos;
		bounds = new Rectangle(x, y, xSize, ySize);
		
		mouse = new MouseAdapter(){
			int nx;
			int ny;
			@Override
			public void mousePressed(MouseEvent e) {
				if(visible && bounds.contains(e.getPoint())){
					nx = e.getX() - x;
					ny = e.getY() - y;
					active = true;
				}else{
					active = false;
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(usable() && bounds.contains(e.getPoint())){
					setBounds(e.getX() - nx, e.getY() - ny);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(visible && bounds.contains(e.getPoint())){
					active = true;
				}else{
					active = false;
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
	}
	
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
			g.setColor(oldColor);
			g.setFont(oldFont);//Restores previous information
		}
	}
	public void setBounds(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
}
