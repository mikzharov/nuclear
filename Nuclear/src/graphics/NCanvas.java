package graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import main.Main;

public class NCanvas extends JPanel{

	private static final long serialVersionUID = 1L;
	BufferedImage image;
	ImageIcon render = new ImageIcon();
	JLabel label = new JLabel();
	public Graphics2D g;
	
	public NCanvas(int x, int y){//This initializes the panel
		this.add(label);
		image = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		label.setIcon(render);
		g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
	}
	public void render(){//This repaints the panel
		this.repaint();
		this.revalidate();
		Main.frame.repaint();
		Main.frame.revalidate();
	}
	public void render(int fps){//This renders the panel with the fps in the corner
		g.drawString(fps+"",10,10);
		this.repaint();
		this.revalidate();
		Main.frame.repaint();
		Main.frame.revalidate();
	}
}
