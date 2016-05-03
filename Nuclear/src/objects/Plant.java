package objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import logic.Integrator;

public class Plant extends GameObject{
	private PipeSystem sys = null;
	private Turbine tSys = null;//This is for the components that need to be controlled by the reactor
	BufferedImage floorPlans = null;
	public Plant(String path){
		try {
			floorPlans = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}

	public int getImageX(){
		return floorPlans.getWidth() + this.x;
	}
	public int getImageY(){
		return floorPlans.getHeight() + this.y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void drawObj(Graphics2D g){
		g.drawImage(floorPlans, this.getX() + (Integrator.intLastXOffset), this.getY() + Integrator.intLastYOffset, this.getImageX() + Integrator.intLastXOffset,  this.getImageY() + Integrator.intLastYOffset, 0, 0, floorPlans.getWidth(), floorPlans.getHeight(), null);
	}
	public void setPipeSystem(PipeSystem s){
		objects.remove(sys);//Removes old one
		sys = s;
		objects.add(sys);
	}
	public void setTurbineSystem(Turbine s){
		objects.remove(tSys);//Removes old one
		tSys = s;
		objects.add(tSys);
	}
}
