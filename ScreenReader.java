package io;

import placeable.*;
import toolkit.Pixel;
import mechanics.*;

/**
 * This class contains various methods to read something directly from a {@link PixelScreen}. As every PixelScreen is a {@link PixelObject} too, it is placeable and, therefore, 
 * it contains its own Pixel array. Even though this makes the whole screen directly readable, 
 */

public class ScreenReader {
	
	private PixelScreen screen;

	public ScreenReader(PixelScreen screen) {
		this.screen = screen;
	}
	
	/*public Pixel[] readAsPixels(int x, int y, int width, int height) {
		
	}
	
	public String readBinary(int x, int y, int width, int height) {
		
	}
	
	public PixelStamp readAsStamp(int x, int y, int width, int height) {
		
	}
	
	public PixelObject readAsObject(int x, int y, int width, int height) {
		
	}*/
	
}
