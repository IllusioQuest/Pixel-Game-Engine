package constants;

import java.awt.Dimension;

import placeable.*;
import toolkit.Pixel;

public class PixelText extends PixelObject {

	public PixelText(int x, int y, String text) {
		super(x, y, new Dimension(10,10));
	}
	
	public PixelText(int x, int y, String text, DisplayMode displayMode) {
		super(x, y, new Dimension(10,10), displayMode);
	}
	
	private Pixel[] render(String text) {
		return new Pixel[0];
	}
}
