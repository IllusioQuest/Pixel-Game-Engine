package test;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import constants.DisplayMode;
import game.*;
import mechanics.*;
import placeable.*;
import toolkit.*;

//This is the first test of creating a complete small game. It should use alot of different mechanics to get to know which things might be quite common in a game and should get
// an own class.

public class TestGame2 implements Game{

	private PixelObject car;
	private PixelObject obstacle[];
	
	private Thread moving;
	private boolean stillRunning = true;
	
	private static PixelScreen screen;
	
	private double startingTime;
	
	private KeySensor keys;
	
	public static void main(String[] args) {
		TestGame2 game = new TestGame2();
		GameBuilder builder = new GameBuilder(new Dimension(20,20), game);
	}

	@Override
	public void initialize(GameInstance instance) {
		screen = instance.getContentPane().getScreen();
		keys = instance.keys;
		instance.setTitle("Moving & Scrolling Game - Test");
		car = new PixelObject("010111111111111101", 9, 10, new Dimension(3,6));
		obstacle = new PixelObject[4];
		obstacle[0] = new PixelObject("101010101", (int) (Math.random()*8), (int) (Math.random()*20), new Dimension(3,3));
		obstacle[1] = new PixelObject("101010101", (int) (Math.random()*8), (int) (Math.random()*20), new Dimension(3,3));
		obstacle[2] = new PixelObject("101010101", (int) (Math.random()*7)+13, (int) (Math.random()*20), new Dimension(3,3));
		obstacle[3] = new PixelObject("101010101", (int) (Math.random()*7)+13, (int) (Math.random()*20), new Dimension(3,3));
		screen.add(obstacle);
		screen.add(car);
		
		moving = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					if (stillRunning) {
					if (keys.keyPressed(KeyEvent.VK_LEFT))
		    	        car.moveX(-1);
		            if(keys.keyPressed(KeyEvent.VK_RIGHT))
			            car.moveX(1);
	    	        if (keys.keyPressed(KeyEvent.VK_UP))
	            		car.moveY(-1);
	            	if (keys.keyPressed(KeyEvent.VK_DOWN))
	    	        	car.moveY(1);
	            	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				}
				}});
		moving.start();
		startingTime = System.currentTimeMillis();
	}

	private int a[] = new int[4]; private int[]  b = new int[4];
	
	@Override
	public void tick(int i) {
		screen.getPanel().getGameInstance().setTitle(String.format("Obstacle1 : (%d, %d)  |  You: (%d, %d)", obstacle[0].getX(), obstacle[0].getY(), car.getX(), car.getY()));
		for (int j = 0; j < 4; j++) {
			if (TouchingSensor.isTouching(car, obstacle[j], screen) && stillRunning) {
				double time = System.currentTimeMillis();
			    screen.fillScreen(screen.getCurrentColor());
			    //screen.add(new PixelObject("11100011100100000101001110001010010010001010011101011101", 3,5, new Dimension(11,5), DisplayMode.ERASE));
			    stillRunning = false;
			    screen.getPanel().update();
			    screen.getPanel().getGameInstance().stopGame();
			    JOptionPane.showMessageDialog(null, String.format("You survived %s seconds.",(float)((int)((time-startingTime)/10))/100));
			}
			
		if ( i % 20 == 0) {
			a[j] = (int) (Math.random()*3-1);
			b[j] = (int) (Math.random()*3-1);
		}
		if (i % 20 < 20 - 5*j && i % 20 > 15 - 5*j) { 
			obstacle[j].move(a[j],b[j]);
		}}
		if (stillRunning) {
		if(car.getX() < screen.getScrollX() + 2) {
			screen.scrollX(-1);
		}
		if(car.getX() > screen.getScrollX() +15) {
			screen.scrollX(1);
		}
		if(car.getY() < screen.getScrollY() + 2) {
			screen.scrollY(-1);
		}
		if(car.getY() > screen.getScrollY() +12) {
			screen.scrollY(1);
		}}
	}

	@Override
	public void buttonPressed(int x, int y) {
		if (x + y == 0) 
			screen.toggleDarkMode();
		else
			car.setPosition(x + screen.getScrollX(), y + screen.getScrollY());
		
	}
	
}
