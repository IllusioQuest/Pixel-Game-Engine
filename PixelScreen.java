
package mechanics;
import java.awt.Dimension;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import constants.*;
import constants.DisplayMode;
import placeable.*;

import java.awt.*;

import toolkit.Pixel;

public class PixelScreen extends ScreenRenderer {

	public Color[][] pixelColors;
	public Color[][] stampingLayer;
	
	private HashMap<Integer, ArrayList<PixelObject>> layers;
	
	private boolean darkMode;
	
	private Color color = Color.BLACK;
	private Color backgroundColor = Color.WHITE;
	
	private ArrayList<Pixel> pixelsList;
	
	private PixelPanel panel;
	//private ScreenDisplay display;
	
	//Constructors
	
	public PixelScreen(Dimension size) {
		super(0, 0, size);
		initialize();
	}
	
	public PixelScreen(int x, int y, Dimension size) {
		super(x, y, size);
		initialize();
	}
	
	//Changing the color of single pixels
	
	private void changePixel (int x, int y, DisplayMode mode) {
		switch (mode) {
		case SWITCH: {
			if (pixelColors[x][y].equals(getCurrentColor())) {
			pixelColors[x][y] = getCurrentBackgroundColor();
			pixelsList.add(new Pixel(x,y,0));
		    }
		    else {
			pixelColors[x][y] = getCurrentColor();
			pixelsList.add(new Pixel(x,y,1));
		    }
			break;
		}
		case DRAW_BINARY: {
			pixelColors[x][y] = getCurrentColor();
			pixelsList.add(new Pixel(x,y,1));
			break;
		}
		case ERASE: {
			pixelColors[x][y] = getCurrentBackgroundColor();
			pixelsList.add(new Pixel(x,y,0));
			break;
		}
		//TODO Other DisplayModes support
		}
	}
	
	//Changing the color in bigger objects
	
	private void draw (int xPos, int yPos, Pixel[] pixels, DisplayMode mode) {
		for (Pixel p : pixels) {
			int x = xPos + p.getX() - scrollX;
			int y = yPos + p.getY() - scrollY;
			if (0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
				changePixel(x,y,mode);
			}
		}
	}
	
	
	//PixelStamp
	
	public void stamp(int x, int y, PixelStamp stamp) {
		for (Pixel p : stamp.getPixels()) {
			if (p.getValue() == 1)
				if (0 <= p.getX() + x && p.getX() + x < getWidth() && 0 <= p.getY() + y && p.getY() + y < getHeight())
			        stampingLayer[p.getX() + x][p.getY() + y] = getCurrentColor();
		}
	}
	
	//Changing the whole screen
	
	/**
	 * This method makes the whole screen appear in the current {@link #getCurrentBackgroundColor() background color}. Therefore, it
	 * hides all PixelObjects and clears the stamping layer. Due to this, it can't be undone. The PixelObjects can be made visible again
	 * with {@link #setVisibleAll(boolean)}. If, instead, you want to remove them, use {@link #removeAll()}.<br/>
	 * It is equivalent to {@link #fillScreen(Color) fillScreen(getCurrentBackgroundColor())}.
	 */
	public void clearScreen() {
		setVisibleAll(false);
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				stampingLayer[x][y] = getCurrentBackgroundColor();
			}
		}
	}
	
	public void fillScreen(Color color) {
		setVisibleAll(false);
		for (int y = 0; y < getHeight(); y ++) {
			for (int x = 0; x < getWidth(); x++) {
				stampingLayer[x][y] = color;
			}
		}
	}
	
	public void setVisibleAll(boolean state) {
		for (PixelObject obj : components) {
			obj.setVisible(state);
		}
	}
	
	public void removeAll() {
		components.clear();
	}
	
	//DarkMode and colors
	
	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
		renderDarkModeInStampingLayer();
	}
	
	public void toggleDarkMode() {
		darkMode = !darkMode;
		renderDarkModeInStampingLayer();
	}
	
	public Color getCurrentColor() {
		return darkMode ? backgroundColor : color;
	}
	
	public Color getCurrentBackgroundColor() {
		return darkMode ? color : backgroundColor;
	}
	
	private void renderDarkModeInStampingLayer() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (stampingLayer[x][y].equals(getCurrentBackgroundColor()))
					stampingLayer[x][y] = getCurrentColor();
				else
					stampingLayer[x][y] = getCurrentBackgroundColor();
			}
		}
	}
	
	//Rendering
	
	private void renderStamps() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (!stampingLayer[x][y].equals(getCurrentBackgroundColor()))
					pixelColors[x][y] = stampingLayer[x][y];
			}
		}
	}
	
	public void update() {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				pixelColors[x][y] = getCurrentBackgroundColor();
			}
		}
		renderStamps();
		pixelsList = new ArrayList<Pixel>();
		for (PixelObject obj : components) {
			if (obj.getVisible()) {
			    obj.update();
			    draw(obj.getX(), obj.getY(), obj.getPixels(), obj.getDisplayMode());
			}
		}
		this.pixels = pixelsList.toArray(new Pixel[pixelsList.size()]);
	}
	
	//Layers
	
	public int getLayerMax() {
		Set<Integer> keys = layers.keySet();
		int currMax = Integer.MIN_VALUE;
		for (int k : keys) {
			if (k > currMax)
				currMax = k;
		}
		return currMax;
	}
	
	public int getLayerMin() {
		Set<Integer> keys = layers.keySet();
		int currMin = Integer.MAX_VALUE;
		for (int k : keys) {
			if (k < currMin)
				currMin = k;
		}
		return currMin;
	}
 	
	//Other stuff
	
	private void initialize() {
		screen = this;
		layers = new HashMap<Integer, ArrayList<PixelObject>>();
		stampingLayer = new Color[getWidth()][getHeight()];
		pixelColors = new Color[getWidth()][getHeight()];
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				stampingLayer[x][y] = getCurrentBackgroundColor();
			}
		}
	}
	
	public Color getPixelColors(int x, int y){
		return pixelColors[x][y];
	};
	
	@Override
	public Pixel[] getPixels() {
		update(); 
		ArrayList<Pixel> list = new ArrayList<Pixel>();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				if (!pixelColors[x][y].equals(getCurrentBackgroundColor()))
					list.add(new Pixel(x, y, 1));
			} 
		}
		return list.toArray(new Pixel[list.size()]);
	}
	
	void setPanel(PixelPanel panel) { //TODO REMOVE SD
		this.panel = panel;
	}
	
	/*void setDisplay(ScreenDisplay display) {
		this.display = display;
	}*/
	
	public PixelPanel getPanel() {//TODO REMOVE SD
		return panel;
	}
	
	/*public ScreenDisplay getDisplay() {
		return display;
	}*/
	
	public void setScreen(PixelScreen screen) {
		this.screen = screen;
	}
	
	protected PixelScreen screen() {
		return this;
	}
	
	public void add(PixelObject obj) {
		super.add(obj);
		ArrayList<PixelObject> layer = layers.get((obj.getLayer() == null) ? getLayerMax()+1 : obj.getLayer());
		if (layer == null) {
			layer = new ArrayList<PixelObject>();
			layers.put((obj.getLayer() == null) ? getLayerMax()+1 : obj.getLayer(), layer);
		}
		layer.add(obj);
	} 
	
	/*public static void main(String[] args) {
		PixelScreen scr = new  PixelScreen(new Dimension(20,20));
		PixelObject cat = new PixelObject("0101011111101011111101110", 5, 5, new Dimension(5,5));
		PixelObject dog = new PixelObject("111111111111011010110100111111100001110111000011000110000111111100", 5,8,new Dimension(11,6));
		scr.add(cat);
		scr.add(dog);
		//scr.toggleDarkMode();
		scr.update();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(20,20));
		JButton[] buttons = new JButton[400];
		for (int y = 0, i = 0; y < 20; y++) {
			for (int c = 0; c < 20; c++, i++) {
				buttons[i] = new JButton();
			    buttons[i].setBackground(scr.pixelColors[c][y]);
			    panel.add(buttons[i]);
			}
		}
		frame.add(panel);
		frame.setSize(500,500);
		frame.setVisible(true);
		
		//The code below switches the pixels of cat MAX times, where MAX is
		final int MAX = 0;
		//and gets the time difference:
		
		long time1 = System.currentTimeMillis();
		
		for(int i = 0; i < MAX; i++) {
			scr.add(cat);
			scr.update();
			for (int y = 0, j = 0; y < 20; y++) {
				for (int c = 0; c < 20; c++, j++) {
				    buttons[j].setBackground(scr.pixelColors[c][y]);
				}
			}
		}
		
		long time2 = System.currentTimeMillis();
		
		System.out.println(time2-time1);
		
	}*/
	
}
 