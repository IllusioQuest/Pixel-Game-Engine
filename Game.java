package game;

import java.awt.event.*;

import mechanics.GameInstance;
import mechanics.PixelScreen;

public interface Game {
	
	public void initialize(GameInstance instance);
	public void tick(int i);
	
	
	//Optional
	public default void buttonPressed(int x, int y) {};
	public default void keyTyped(KeyEvent e) {};
	public default void keyPressed(KeyEvent e) {};
	public default void keyReleased(KeyEvent e) {};
	
}
