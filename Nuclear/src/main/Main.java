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
	static boolean run = false;
	public static String playerName = "";
	/**
	 * Main constructor
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		try {
			// Makes the JPanel look the same as your system default.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {// Find the exceptions(errors).
			e.printStackTrace();// Prints the error to the console.
		}
		new Main();

	}

	// frame and panel
	public static JFrame frame = new JFrame("Learn to run a Nuclear Reactor");
	Image background;
	JPanel panel = new JPanel();

	// these buttons will be fixed
	// start page buttons
	JButton start = new JButton("Start");
	JButton settings = new JButton("Settings");
	JButton credits = new JButton("Credits");
	JButton quit = new JButton("Quit");
	// other page buttons
	JButton back = new JButton("Back");
	//start game buttons
	JButton enter = new JButton("Start Game");

	// labels
	JLabel title = new JLabel("Learn to run a Nuclear Reactor");
	static JLabel backgroundLabel = new JLabel();
	// credit page labels
	JLabel programmers = new JLabel("Senior Software Engineers");
	JLabel thomas = new JLabel("Thomas Kidd");
	JLabel misha = new JLabel("Misha Zharov");
	JLabel art = new JLabel("Lead Graphic Designers");
	JLabel thomas2 = new JLabel("Thomas Kidd");
	JLabel misha2 = new JLabel("Misha Zharov");
	// settings page labels
	JLabel difficultyChoice = new JLabel("Difficulty");
	JLabel soundVolume = new JLabel("Sound Volume");
	JLabel musicVolume = new JLabel("Music Volume");
	//start game labels
	JLabel name = new JLabel("Enter name:");

	// sliders
	JSlider soundSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
	JSlider musicSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
	static Toolkit tk = Toolkit.getDefaultToolkit();
	public final static int xSize = ((int) tk.getScreenSize().getWidth());
	public final static int ySize = ((int) tk.getScreenSize().getHeight());
	// combo boxes
	static JComboBox<String> difficultyLevels = new JComboBox<String>();
	//text fields
	JTextField enterName = new JTextField(30);
	
	/**
	 * Starts listening to when the start button is clicked, this code can't be in the actionlistener because it blocks the EDT thread (big no no)
	 */
	public static void start(){
		while(!run){//Weird work around to prevent this running in the EDT.
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		run = false;
		if((String) difficultyLevels.getSelectedItem() != null){
			Integrator.setLevel((int)((String) difficultyLevels.getSelectedItem()).charAt(0)-48);
		}
		Integrator integrator = new Integrator(xSize, ySize);
		frame.remove(backgroundLabel);
		frame.add(Integrator.canvas);
		frame.repaint();
		frame.revalidate();
		frame.setIgnoreRepaint(true);
		integrator.start();
	}
	/**
	 * The main constructor, how to start the game
	 */
	public Main() {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get screen sizes

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

		// frame.add(outsidePanel);
		frame.setSize(xSize, ySize);
		frame.setVisible(true);
		frame.setResizable(false);

		// button settings
		start.setBounds(xSize / 2 - 150, 330, 300, 80);
		settings.setBounds(xSize / 2 - 150, 430, 300, 80);
		credits.setBounds(xSize / 2 - 150, 530, 300, 80);
		quit.setBounds(xSize / 2 - 150, 630, 300, 80);
		title.setBounds(xSize / 2 - 360, 100, 900, 200);
		back.setBounds(xSize / 2 - 150, 630, 300, 80);

		// label setting
		title.setFont(new Font("Barial", Font.PLAIN, 55));
		start.setFont(new Font("Sans Serif", Font.BOLD, 25));
		quit.setFont(new Font("Sans Serif", Font.BOLD, 25));
		credits.setFont(new Font("Sans Serif", Font.BOLD, 25));
		settings.setFont(new Font("Sans Serif", Font.BOLD, 25));
		back.setFont(new Font("Sans Serif", Font.BOLD, 25));

		// add to panel
		panel.add(title);
		panel.add(start);
		panel.add(settings);
		panel.add(credits);
		panel.add(quit);
		
		difficultyLevels.setBounds(xSize / 2, 280, 200, 50);
		difficultyLevels.setFont(new Font("Sans Serif", Font.BOLD, 25));
		difficultyLevels.addItem("1. Tutorial");
		difficultyLevels.addItem("2. Normal");
		difficultyLevels.addItem("3. Chernobyl");
		
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
				panel.remove(start);
				panel.remove(settings);
				panel.remove(credits);
				panel.remove(quit);
				
				panel.add(back);
				
				//players must enter a name before starting
				panel.add(name);
				name.setBounds(xSize / 2 - 150, 360, 300, 80);
				name.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(enterName);
				enterName.setBounds(xSize / 2 - 150, 430, 300, 50);
				enterName.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(enter);
				enter.setBounds(xSize / 2 - 150, 530, 300, 80);
				enter.setFont(new Font("Sans Serif", Font.BOLD, 25));
				enter.setEnabled(false);
				
				frame.repaint();
				frame.revalidate();
			}
		});
		
		enterName.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				//make sure the player entered a name before allowing the game to begin
				playerName = enterName.getText();
				if (!playerName.equals("")) {
					enter.setEnabled(true);
				}
				else {
					enter.setEnabled(false);
				}
				frame.repaint();
				frame.revalidate();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				playerName = enterName.getText();
				// Starts the game engine
				run = true;
			}
		});

		credits.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.remove(start);
				panel.remove(settings);
				panel.remove(credits);
				panel.remove(quit);

				panel.add(back);

				// programming credits
				panel.add(programmers);
				programmers.setBounds(xSize / 2 - 360, 200, 900, 200);
				programmers.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(thomas);
				thomas.setBounds(xSize / 2, 200, 900, 200);
				thomas.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(misha);
				misha.setBounds(xSize / 2, 250, 900, 200);
				misha.setFont(new Font("Sans Serif", Font.BOLD, 25));

				// art credits
				panel.add(art);
				art.setBounds(xSize / 2 - 360, 400, 900, 200);
				art.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(thomas2);
				thomas2.setBounds(xSize / 2, 400, 900, 200);
				thomas2.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(misha2);
				misha2.setBounds(xSize / 2, 450, 900, 200);
				misha2.setFont(new Font("Sans Serif", Font.BOLD, 25));

				frame.repaint();
				frame.revalidate();
			}
		});
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll();
				panel.add(title);
				panel.add(back);

				// labels
				panel.add(difficultyChoice);
				difficultyChoice.setBounds(xSize / 2 - 360, 200, 900, 200);
				difficultyChoice.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(soundVolume);
				soundVolume.setBounds(xSize / 2 - 360, 300, 900, 200);
				soundVolume.setFont(new Font("Sans Serif", Font.BOLD, 25));
				panel.add(musicVolume);
				musicVolume.setBounds(xSize / 2 - 360, 400, 900, 200);
				musicVolume.setFont(new Font("Sans Serif", Font.BOLD, 25));

				// combo box
				panel.add(difficultyLevels);
				

				// sliders
				panel.add(soundSlider);
				soundSlider.setBounds(xSize / 2, 390, 300, 40);
				panel.add(musicSlider);
				musicSlider.setBounds(xSize / 2, 490, 300, 40);

				frame.repaint();
				frame.revalidate();
			}
		});
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.removeAll();
				panel.add(title);
				panel.add(start);
				panel.add(settings);
				panel.add(credits);
				panel.add(quit);

				frame.repaint();
				frame.revalidate();
			}
		});
		start();
	}
	/**
	 * Adds the panels back after the game ends
	 */
	public static void resume(){
		frame.add(backgroundLabel);
		frame.remove(Integrator.canvas);
		frame.repaint();
		frame.revalidate();
		frame.setIgnoreRepaint(false);
		start();
	}
}
