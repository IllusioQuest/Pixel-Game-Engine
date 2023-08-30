package toolkit;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import mechanics.PixelScreen;
import placeable.PixelObject;
import placeable.ScreenRenderer;

public class TouchingSensor {
	
	public static boolean isTouching (PixelObject a, PixelObject b, ScreenRenderer screen) {
		ArrayList<Point> aPixels = new ArrayList<Point>();
		for (Pixel p : a.getPixels()) {
			int x = p.getX() + a.getX() - screen.getScrollX();
			int y = p.getY() + a.getY() - screen.getScrollY();
			aPixels.add(new Point(x,y));
		}
		for (Pixel p : b.getPixels()) {
			int x = p.getX() + b.getX() - screen.getScrollX();
			int y = p.getY() + b.getY() - screen.getScrollY();
			if (aPixels.contains(new Point(x,y)))
				return true;
		}
		return false;
	}
	
	private static class Point{
		public int x, y;
		public Point (int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public boolean equals(Object obj) {
			Point p = (Point) obj;
			return (x == p.x && y == p.y);
		}
	}
	
	public static void main(String args[]) {
		PixelScreen screen = new PixelScreen(new Dimension(5,5));
		PixelObject a = new PixelObject("11",1,0,new Dimension(2,1));
		PixelObject b = new PixelObject("1",0,0,new Dimension(1,1));
		System.out.println(isTouching(a,b,screen));
	}
	
}
