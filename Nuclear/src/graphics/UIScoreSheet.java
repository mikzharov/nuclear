package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import main.HighScores;

public class UIScoreSheet extends UIComponent {
	
	private List<List<String>> namesAndScores = HighScores.namesAndScoresSorted;
	
	public UIScoreSheet(int xPos, int yPos, int x1, int y2) {
		xSize = x1;
		ySize = y2;
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
				if(movable && usable() && bounds.contains(e.getPoint())){
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
			
			int counter =0;
			
			g.setColor(textColor);
			for (int i=0; i<namesAndScores.size(); i++) {
				g.drawString((i+1)+".", x+10, y+(80*i)+50);
				g.drawString(namesAndScores.get(i).get(0), x+50, y+(80*i)+50);
				g.drawString(namesAndScores.get(i).get(1)+" kW", x+350, y+(80*i)+50);
				counter++;
				if (counter == 5)
					break;
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
}
