package placeable;

import toolkit.*;
import java.awt.Dimension;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import constants.DisplayMode;
import mechanics.PixelScreen;

public class PixelObject {

	private int posX;
	private int posY;
	private Integer layer;
	private Dimension size;
	private String definition;
	private DisplayMode displayMode;
	private boolean visible;
	protected Pixel[] pixels;
	protected PixelScreen screen;
	
	public PixelObject(String definition, int x, int y, Dimension size) {
		posX = x;
		posY = y;
		this.size = size;
		this.definition = definition;
		pixels = DefinitionConvert.binaryToPixels(definition, size);
		visible = true;
		displayMode = DisplayMode.DRAW_BINARY;
	}
	
	public PixelObject(String definition, int x, int y, Dimension size, DisplayMode displayMode) {
		posX = x;
		posY = y;
		this.size = size;
		this.definition = definition;
		pixels = DefinitionConvert.binaryToPixels(definition, size);
		visible = true;
		this.displayMode = displayMode;
	}
	
	protected PixelObject(int x, int y, Dimension size) {
		posX = x;
		posY = y;
		this.size = size;
		definition = "";
		pixels = new Pixel[0];
		visible = true;
		displayMode = DisplayMode.DRAW_BINARY;
	}
	
	protected PixelObject(int x, int y, Dimension size, DisplayMode displayMode) {
		posX = x;
		posY = y;
		this.size = size;
		definition = "";
		pixels = new Pixel[0];
		visible = true;
		this.displayMode = displayMode;
	}
	
	protected void setScreen(PixelScreen screen) {
		this.screen = screen;
	}
	
	public void moveX(int value) {
		posX += value;
	}
	
	public void moveY(int value) {
		posY += value;
	}
	
	public void move(int x, int y) {
		posX += x;
		posY += y;
	}
	
	public void setPosition(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public void moveForward(int amount) {
		setLayer(layer + amount);
	}
	
	public void moveBackwards(int amount) {
		setLayer(layer - amount);
	}
	
	public void moveToFront() {
		setLayer(screen.getLayerMax() + 1);
	}
	
	public void moveToBack() {
		setLayer(screen.getLayerMin() - 1);
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public Integer getLayer() {
		return layer;
	}
	
	public String getDefinition() {
		return definition;
	}
	
	public Pixel[] getPixels() {
		return pixels;
	}
	
	public void setPixels(Pixel[] pixels) {
		this.pixels = pixels;
		definition = DefinitionConvert.binaryToBinary(DefinitionConvert.pixelsToBinary(pixels));
	}
	
	public int getWidth() {
		return (int) size.getWidth();
	}
	
	public int getHeight() {
		return (int) size.getHeight();
	}
	
	public PixelStamp toStamp() {
		return new PixelStamp(getDefinition(), size);
	}
	
	public void update() {
		//gets overrided
	}
	
	public void setVisible(boolean state) {
		visible = state;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public static void main(String[] args) {
		PixelObject a = new PixelObject(
				""
				+ "00100"
				+ "01110"
				+ "10101"
				+ "00100"
				+ "00100"
				,0,0,new Dimension(5,5));
		System.out.println("S(" + a.getX() + "|" + a.getY() + ")");
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("image.pbm"));
			writer.write(String.format("P1\n%d %d\n%s", a.getWidth(),a.getHeight(),a.getDefinition()));
			writer.close();
		}
		catch(IOException e) {} 
	}

	public DisplayMode getDisplayMode() {
		return displayMode;
	}
	
}
