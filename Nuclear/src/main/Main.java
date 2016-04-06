package main;

//the main class
//Start Date: April 5, 2016
//Authors: Thomas Kidd & Misha Zharov

import javax.imageio.ImageIO;
import javax.swing.*;

import logic.Integrator;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
	boolean fullscreen = true;

	public static void main(String[] args) {
		try {
			// Makes the JPanel look the same as your system default.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {// Find the exceptions(errors).
			e.printStackTrace();// Prints the error to the console.
		}
		new Main();

	}
	
	//frame and panel
	public static JFrame frame = new JFrame("Learn to run a Nuclear Reactor");
	Image background;
	JPanel panel = new JPanel();
	
	
	// these buttons will be fixed
	JButton start = new JButton("Start");
	JButton settings = new JButton("Settings");
	JButton credits = new JButton("Credits");
	JButton quit = new JButton("Quit");
	
	// labels
	JLabel title = new JLabel("Learn to run a Nuclear Reactor");
	JLabel backgroundLabel = new JLabel();
	public Main() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// get screen sizes
		Toolkit tk = Toolkit.getDefaultToolkit();
		final int xSize = ((int) tk.getScreenSize().getWidth());
		final int ySize = ((int) tk.getScreenSize().getHeight());

		// frame and panel settings
		if (fullscreen) {
			frame.setUndecorated(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		panel.setLayout(null);
		panel.setBackground(new Color(82, 179, 217, 0));
		panel.setBounds(0, 0, xSize, ySize);
		boolean opaque = false;
		panel.setOpaque(opaque);
		
		BufferedImage background;
		try {
			background = ImageIO.read(new File("res/Nuclear_Power_Plant_Cattenom.jpg"));
			
			backgroundLabel.setIcon(new ImageIcon(background));
			backgroundLabel.add(panel);
			frame.add(backgroundLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//frame.add(outsidePanel);
		frame.setSize(xSize, ySize);
		frame.setVisible(true);
		frame.setResizable(false);

		// button settings
		start.setBounds(xSize/2-150, 330, 300, 80);
		settings.setBounds(xSize/2-150, 430, 300, 80);
		credits.setBounds(xSize/2-150, 530, 300, 80);
		quit.setBounds(xSize/2-150, 630, 300, 80);
		title.setBounds(xSize/2-370, 100, 900, 200);
		
		// label setting
		title.setFont(new Font("Barial", Font.PLAIN, 55));
		start.setFont(new Font("Sans Serif", Font.BOLD, 25));
		quit.setFont(new Font("Sans Serif", Font.BOLD, 25));
		credits.setFont(new Font("Sans Serif", Font.BOLD, 25));
		settings.setFont(new Font("Sans Serif", Font.BOLD, 25));

		// add to panel
		panel.add(title);
		panel.add(start);
		panel.add(settings);
		panel.add(credits);
		panel.add(quit);

		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Quits game when user clicks quit
				System.exit(0);
			}
		});
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Starts the game engine
				Integrator integrator = new Integrator(xSize, ySize);
				frame.remove(backgroundLabel);
				frame.add(integrator.canvas);
				frame.repaint();
				frame.revalidate();
				frame.setIgnoreRepaint(true);
				integrator.start();
			}
		});
	}
}
