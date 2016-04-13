package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIButton extends UIComponent{
	private boolean visible = true;
	private int xSize;
	private int ySize;
	private int x;
	private int y;
	private String text;
	private Color color = new Color(204, 204, 255);
	private Font font = new Font("Impact", Font.PLAIN, 96);
	public Rectangle bounds;
	public MouseAdapter mouse;
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
				if(visible && bounds.contains(new Point(e.getX(), e.getY()))){
					nx = e.getX() - x;
					ny = e.getY() - y;
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(visible && bounds.contains(new Point(e.getX(), e.getY()))){
					setBounds(e.getX() - nx, e.getY() - ny);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(visible && bounds.contains(e.getPoint())){
					nx = e.getX();
					ny = e.getY();
				}
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				if(visible && bounds.contains(new Point(e.getX(), e.getY()))){
					color = new Color(174, 174, 207);
				}else if(visible){
					color = new Color(204, 204, 255);
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
				
			g.setColor(oldColor);
			g.drawString(text, x+20, y+90);
			g.setFont(oldFont);//Restores previous information
		}
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setBounds(int x, int y){
		this.x = x;
		this.y = y;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
}
