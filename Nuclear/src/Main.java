//the main class
//Start Date: April 5, 2016
//Authors: Thomas Kidd & Misha Zharov

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
	
	public static void main (String[] args) {
		
		new Main();
		
	}
	
	JFrame frame = new JFrame("Learn to run a Nuclear Reactor");
	JPanel panel = new JPanel();
	JPanel outsidePanel = new JPanel();
	
	//these buttons will be fixed
	JButton start = new JButton("Start");
	JButton settings = new JButton("Settings");
	JButton credits = new JButton("Credits");
	
	//labels
	JLabel title = new JLabel("Learn to run a Nuclear Reactor");
	
	public Main() {
		
		frame.addWindowListener
        (new WindowAdapter()
          {
            public void windowClosing(WindowEvent e)
            {
              System.exit(0);
            }
          }
        );
		
		//get screen sizes
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		
		//frame and panel settings
		panel.setLayout(null);
		panel.setBackground(new Color(82, 179, 217));
		panel.setBounds(275, 150, 700, 650);
		
		outsidePanel.setLayout(null);
		outsidePanel.add(panel);
        frame.setContentPane(outsidePanel);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
		
        //button settings
        start.setBounds(xSize/2-450, 330, 300, 80);
        start.setFont(new Font("Sans Serif", Font.BOLD, 25));
        settings.setBounds(xSize/2-450, 430, 300, 80);
        settings.setFont(new Font("Sans Serif", Font.BOLD, 25));
        credits.setBounds(xSize/2-450, 530, 300, 80);
        credits.setFont(new Font("Sans Serif", Font.BOLD, 25));
        
        //label settings
        title.setBounds(xSize/2-635, 100, 700, 200);
        title.setFont(new Font("Impact", Font.PLAIN, 55));
        
        //add to panel
        panel.add(title);
        panel.add(start);
        panel.add(settings);
        panel.add(credits);
	}
}
