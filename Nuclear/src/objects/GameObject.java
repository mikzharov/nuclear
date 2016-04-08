package objects;

import java.awt.image.BufferedImage;

public class GameObject {
	int x = 0;
	int y = 0;
	BufferedImage image = null;
	public int getX(){
		return x;
	}
	public int getY(){
		return x;
	}
	public int getImageX(){
		return x;
	}
	public int getImageY(){
		return y;
	}
	public BufferedImage getImage(){
		throw new UnsupportedOperationException();
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
}
