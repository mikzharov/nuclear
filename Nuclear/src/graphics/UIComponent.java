package graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

import logic.Integrator;

//This class is just a shell class for other classes to inherit methods from, the code is actually written
//In individual classes such as the UIText class etc.
public class UIComponent {
	protected boolean usable=false;
	public MouseAdapter mouse;
	protected String text;
	public KeyAdapter key;
	public static int defaultHeight = 110;
	public boolean active = false;
	protected boolean visible = true;
	protected int xSize;
	protected int ySize;
	protected int x;
	protected int y;
	protected int x_displace=20; //default setting
	protected int y_displace=90; //default setting
	protected Color color = new Color(174, 174, 207);
	protected Color textColor = Color.black; //default setting
	protected Font font = new Font("Impact", Font.PLAIN, 96);
	protected Rectangle bounds;
	protected boolean movable = true;
	/**
	 * The default UI button constructor
	 * @param xPos The x position of the thing
	 * @param yPos The y position of the ui
	 * @param xSize The width
	 * @param ySize The height
	 */
	public UIComponent(int xPos, int yPos, int xSize, int ySize) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Self explanatory
	 */
	public UIComponent(){
		//Do nothing
	}
	/**
	 * The text doesn't always align with the box of the UI component
	 * @param x The x offset for the text
	 * @param y The y offset for the text
	 */
	public void setTextDisplacement(int x, int y) {
		this.x_displace=x;
		this.y_displace=y;
	}
	/**
	 * Draws the UI component
	 * @param g The Graphics2d object to draw on
	 */
	public void drawObj(Graphics2D g) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Sets the text on the object (if applicable)
	 * @param text The new text
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * Returns the text
	 * @return the text of the object
	 */
	public String getText(){
		return text;
	}
	/**
	 * Sets the components visiblility
	 * @param bool Whether the component should be visible or not
	 */
	public void setVisible(boolean bool){
		visible = bool;
	}
	/**
	 * Updates the X and Y
	 * @param x2 The new X location
	 * @param y2 The new Y location
	 */
	public void setLocation(int x2, int y2) {
		x=x2;
		y=y2;
		bounds = new Rectangle(x, y, xSize, ySize);
	}
	/**
	 * Returns the X location
	 * @return X location
	 */
	public int getX(){
		return x;
	}
	/**
	 * Returns the Y location
	 * @return Y location
	 */
	public int getY(){
		return y;
	}
	/**
	 * Returns width
	 * @return Width
	 */
	public int getWidth(){
		return xSize;
	}
	/**
	 * Returns height
	 * @return Height
	 */
	public int getHeight(){
		return ySize;
	}
	/**
	 * Returns bounds (used for hit detection)
	 * @return Bounds
	 */
	public Rectangle getBounds(){
		return bounds;
	}
	/**
	 * Sets the font of the text
	 * @param font Font of the text
	 */
	public void setFont(Font font){
		this.font = font;
	}
	/**
	 * Sets the font size
	 * @param size Font size
	 */
	public void setFontSize(int size) {
		this.font = new Font("Impact", Font.PLAIN, size); 
	}
	/**
	 * Sets the color of the component
	 * @param color The color
	 */
	public void setColor(Color color){
		this.color = color;
	}
	/**
	 * Sets the transparency
	 * @param opacity Integer transparency (0-255)
	 */
	public void changeColor(int opacity){
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
	}
	/**
	 * Returns whether this component is usable
	 * @return Boolean value as to whether it can be clicked etc.
	 */
	public boolean getActive(){
		return active;
	}
	/**
	 * Whether it is visible
	 * @return Boolean visiblility value
	 */
	public boolean getVisible(){
		return visible;
	}
	/**
	 * Should this component be usable when the game is paused
	 * @param usable 
	 */
	public void setUsableDuringPaused(boolean usable){
		this.usable = usable;
	}
	/**
	 * Returns a boolean which signifies that the game is paused, the object is not visible
	 * or not active, unless it is usable during pause time.
	 * @return
	 */
	protected boolean usable(){
		if(this.active && (!Integrator.paused||usable) && visible){
			return true;
		}
		return false;
	}
	/**
	 * Sets the text color
	 * @param co The text color
	 */
	public void setTextColor(Color co) {
		this.textColor = co;
	}
	/**
	 * Whether the component can be dragged
	 * @param move Boolean moved
	 */
	public void setMovable(boolean move){
		movable = move;
	}
	/**
	 * Sets the active state of the component
	 * @param bol Active state
	 */
	public void setActive(boolean bol){
		active = bol;
	}
}
