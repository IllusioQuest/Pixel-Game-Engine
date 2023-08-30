package placeable;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import mechanics.PixelScreen;
import toolkit.DefinitionConvert;
import toolkit.Pixel;

public class ScreenRenderer extends PixelObject{
	
	protected ArrayList<PixelObject> components;
	protected int scrollX;
	protected int scrollY;
	private int[][] binaryPixels;

	public ScreenRenderer(int x, int y, Dimension size) {
		super(x, y, size);
		components = new ArrayList<PixelObject>();
		binaryPixels = new int[getWidth()][getHeight()];
	}
	
	public void add(PixelObject obj) {
		components.add(obj);
		obj.setScreen(screen());
	}
	
	public void add(PixelObject[] objs) {
		for (PixelObject obj : objs) {
			add(obj);
		}
	}
	
	public void setScreen(PixelScreen screen) {
		super.setScreen(screen);
		for (PixelObject obj : components) {
			obj.setScreen(screen);
		}
	}
	
	/**
	 * Always returns the next higher {@link PixelScreen} in the component's hierarchy. It's needed to set the screens of the components in {@link #add(PixelObject)} correctly
	 * because PixelScreen can override this method unlike add(PixelObject) correctly.
	 */
	protected PixelScreen screen() {
		return screen;
	}
	
	public void scrollX(int value) {
		scrollX += value;
	}
	
	public void scrollY(int value) {
		scrollY += value;
	}
	
	public int getScrollX() {
		return scrollX;
	}
	
	public int getScrollY() {
		return scrollY;
	}
	
	public void update() {
		for (int x = 0; x < getWidth(); x++) { for (int y = 0; y < getHeight(); binaryPixels[x][y] = 0, y++) {}}
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		for (PixelObject obj : components) {
			for (Pixel p : obj.getPixels()) {
				int x = obj.getX() + p.getX() - scrollX;
				int y = obj.getY() + p.getY() - scrollY;
				if (0 <= x && x < getWidth() && 0 <= y && y < getHeight() &&  binaryPixels[x][y] == 0) {
					binaryPixels[x][y] = 1;
					pixels.add(new Pixel(x, y, 1));
				}
				
			}
		}
		this.pixels = pixels.toArray(new Pixel[pixels.size()]);
	}

	@Override
	public String getDefinition() {
		return DefinitionConvert.binaryToBinary(DefinitionConvert.pixelsToBinary(pixels));
	}
	
	public static void main(String[] args) {
		ScreenRenderer sr = new ScreenRenderer(0,0,new Dimension(10,10));
		PixelObject a = new PixelObject("110101011", 9, 0, new Dimension(3,3));
		PixelObject b = new PixelObject("11", 2,2,new Dimension(2,1));
		
		long time1 = System.currentTimeMillis();
		sr.add(a);
		sr.add(b);
		long time2 = System.currentTimeMillis();
 		System.out.println(time2 - time1);
	}
}
