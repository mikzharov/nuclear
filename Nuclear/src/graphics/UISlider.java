package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.Integrator;

public class UISlider extends UIComponent{
	public boolean clicked = false;
	public Color green = new Color(150, 212, 144);
	public Color red = new Color(242, 102, 102);
	private float percentage = 1;
	public boolean disabled = false;
	public UISlider(int xPos, int yPos, int x1, int y1) {
		y_displace=84;//Default for this obj
		this.xSize = x1;
		this.ySize = y1;
		this.x = xPos;
		this.y = yPos;
		bounds = new Rectangle(x, y, xSize, ySize);
		setFontSize(70);
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
				if(usable() && bounds.contains(e.getPoint())){
					clicked = true;
					active = true;
				}else{
					active = false;
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				if(visible && bounds.contains(e.getPoint())){
					color = new Color(204, 204, 255);
				}else if(visible){
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
		key = new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				
				if(usable() && !disabled && e.getKeyCode() == KeyEvent.VK_UP){
					if(percentage + 0.01 <=1){
						percentage += 0.01f;
					}else{
						percentage = 1f;
					}
				}
				if(usable() && !disabled && e.getKeyCode() == KeyEvent.VK_DOWN){
					if(percentage - 0.01 >=0){
						percentage -= 0.01f;
					}else{
						percentage = 0f;
					}
				}
			}
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
				
			//Draws slider
			g.setColor(red);
			g.fillRect(x+20, y+20, xSize-40, ySize-40);
			
			g.setColor(green);
			g.fillRect(x+20, y+20, (int)((xSize-40)*percentage), ySize-40);
			//Draws slider
			
			if(text!=null){
				g.setColor(textColor);
				g.drawString(text, x+x_displace, y+y_displace);
				//System.out.println((int)(y+84+ySize/2.0));
			}
			g.setColor(oldColor);
			g.setFont(oldFont);//Restores previous information
		}
	}
	public void setBounds(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	public float getPercentage(){
		return percentage;
	}
	public void setPercantage(float per){
		if(per >=0 || per <=1){
			percentage = per;
		}
	}
}

