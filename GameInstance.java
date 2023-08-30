package mechanics;

import java.awt.*;
import java.util.EventListener;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import game.Game;
import placeable.PixelObject;
import toolkit.KeySensor;

public class GameInstance extends JFrame implements KeyListener{
	
	private Game game;
	private int i;
	
	private boolean active;
	
	public KeySensor keys;
	
	public GameInstance(PixelPanel panel) {
		super();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(panel);
		setSize(800,800);
		addKeyListener(this);
		setVisible(true);
		keys = new KeySensor();
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public void setContentPane(PixelPanel panel) {
		super.setContentPane(panel);
		panel.setGameInstance(this);
	}
	
	@Override
	public PixelPanel getContentPane() {
		return (PixelPanel) super.getContentPane();
	} 
	
	/**
	 * Initializes the game and opens a new thread, running the game's tick method continuously.
	 */
	public void startGame() {
		active = true;
		game.initialize(this);
		i = 0;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while(active) {
			        try {
				        Thread.sleep(50);
			        } catch (InterruptedException e) {}
			        requestFocus();
			        game.tick(i);
			        getContentPane().update();
			        i++;
			        if(i % Integer.MAX_VALUE == 0) 
				        i = 0;
		        }
			}
		});
		thread.start();
	}
	
	public void stopGame() {
		active = false;
	}
	
	public static void main(String args[]) {
		GameInstance x = new GameInstance(new PixelPanel(new PixelScreen(new Dimension(20,20))));
		x.getContentPane().getScreen().add(new PixelObject("1",0,0,new Dimension(1,1)));
		x.setTitle("TEST");
		x.getContentPane().update();
	}
	
	protected void buttonPressed(int x, int y) {
		game.buttonPressed(x,y);
	}

	@Override
	public void keyTyped(KeyEvent e) {	
		keys.keyTyped(e);
		game.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys.keyPressed(e);
		game.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.keyReleased(e);
		game.keyReleased(e);
	}

}
