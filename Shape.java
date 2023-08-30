package constants;

import placeable.PixelObject;

import java.awt.Dimension;
import toolkit.*;
import java.util.*;

import game.Game;
import game.GameBuilder;
import mechanics.GameInstance;
import mechanics.PixelScreen;

public class Shape extends PixelObject {

	private Shape (int x, int y, Dimension size) {
		super(x, y, size);
	}
	
	private Shape (int x, int y, Dimension size, DisplayMode displayMode) {
		super(x, y, size, displayMode);
	}
	
	public static Shape rectangle(int x, int y, int width, int height) {
		return rectangle(x, y, width, height, DisplayMode.DRAW_BINARY);
	}
	
	public static Shape rectangle(int x, int y, int width, int height, DisplayMode displayMode) {
		ArrayList<Pixel> pixelsList = new ArrayList<Pixel>();
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				pixelsList.add(new Pixel(i, j, 1));
			}
		}
		Shape shape = new Shape(x, y, new Dimension(width, height), displayMode);
		shape.setPixels(pixelsList.toArray(new Pixel[pixelsList.size()]));
		return shape;
	}
	
	public static Shape square(int x, int y, int size) {
		return square(x, y, size, DisplayMode.DRAW_BINARY);
	}
	
	public static Shape square(int x, int y, int size, DisplayMode displayMode) {
		return rectangle(x, y, size, size, displayMode);
	}
	
	public static Shape triangle(int x, int y, int size) {
		return triangle(x, y, size, DisplayMode.DRAW_BINARY);
	}
	
	private static Shape triangle(int x, int y, int size, DisplayMode displayMode) {
		ArrayList<Pixel> pixelsList = new ArrayList<Pixel>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < i+1; j++) {
				pixelsList.add(new Pixel(j,i, 1));
			}
		}
		Shape shape = new Shape(x, y, new Dimension(size, size), displayMode);
		shape.setPixels(pixelsList.toArray(new Pixel[pixelsList.size()]));
		return shape;
	}

	public static void main (String[] args) {
		GameBuilder builder = new GameBuilder(new Dimension(20,20), new Game() {
			public Shape shape1, shape2, shape3, shape4, shape5;
			public GameInstance inst;
			public void initialize(GameInstance inst) {
				this.inst = inst;
				shape1 = Shape.square(7, 7, 2);
				shape2 = Shape.rectangle(0, 0, 20, 2);
				shape3 = Shape.rectangle(15, 3, 3, 7);
				shape4 = Shape.rectangle(0, 19, 20, 1);
				shape5 = Shape.triangle(7, 13, 6);
				PixelScreen screen = inst.getContentPane().getScreen();
				screen.add(shape1);
				screen.add(shape2);
				screen.add(shape3);
				screen.add(shape4);
				screen.add(shape5);
				screen.stamp(0,17,shape4.toStamp());
				screen.scrollY(-20);
			}
			public void tick(int i) {if (/*i % 2 == 1*/true) inst.getContentPane().getScreen().scrollY(1); if (/*i % 80 == 79*/i % 40 == 39) inst.getContentPane().getScreen().scrollY(-40);}
		});
	}
}
