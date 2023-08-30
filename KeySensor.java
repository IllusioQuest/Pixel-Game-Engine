package toolkit;

import java.awt.event.*;
import java.util.ArrayList;

/**
  This class can be seen as an alternative or an extension to {@link KeyListener}. While KeyListener invokes a method each time, a key is 
  pressed, typed or released, KeySensor remembers which keys were pressed and released and so, can check which are hold down right now. It 
  is recommended for e.g. moving engines, where keys are mostly held down. It must be assigned to a class implementing KeyListener and each 
  of KeyListener's methods has to call those of KeySensor.
  <br/><br/>
  Example usage:
  <pre>
  public class MovingEngine implements KeyListener {
  
    private KeySensor keySensor;
  
  	public MovingEngine() {
		keySensor = new KeySensor();
  	}
  
  	public void keyTyped(KeyEvent e) {
		keySensor.keyTyped(e);
  	}
  
  //...

  }
  </pre>
  
  Existing systems can still be used and - by getting the KeyListener's output - some more functionalities are made possible by KeySensor.
 */

public class KeySensor {
	
	private ArrayList<Integer> currentKeys;
	
	/**
	 * Standard constructor declaring all needed variables.
	 */
	public KeySensor() {
		currentKeys = new ArrayList<Integer>();
	}

	/**
	 * This class should be invoked by a {@link KeyListener} to get its data.
	 * @param e {@link KeyEvent} passed by the KeyListener's method
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * This class should be invoked by a {@link KeyListener} to get its data. It adds the pressed key to the ArrayList of currently pressed keys.
	 * @param e {@link KeyEvent} passed by the KeyListener's method
	 */
	public void keyPressed(KeyEvent e) {
		if (!currentKeys.contains(e.getKeyCode())) {
			currentKeys.add(e.getKeyCode());
		}
	}

	/**
	 * This class should be invoked by a {@link KeyListener} to get its data. It removes the released key from the ArrayList of currently pressed keys.
	 * @param e {@link KeyEvent} passed by the KeyListener's method
	 */
	public void keyReleased(KeyEvent e) {
		currentKeys.remove((Object)e.getKeyCode());
	}
	
	/**
	 * This method returns an array of the keyCodes of the currently pressed keys.
	 * @return keyCodes
	 * @see KeyEvent
	 */
	public int[] pressedKeys() {
		return currentKeys.stream().mapToInt(i -> i).toArray();
	}
	
	/**
	 * Checks if a key is currently pressed
	 * @param keyCode The keyCode of the desired key
	 * @return {@code true} if the key is currently pressed, otherwise {@code false}
	 */
	public boolean keyPressed(int keyCode) {
		return currentKeys.contains(keyCode);
	}
	
	/**
	 * Checks if a key is currently pressed
	 * @param keyChar The char associated with the desired key
	 * @return {@code true} if the key is currently pressed, otherwise {@code false}
	 */
	public boolean keyPressed(char keyChar) {
		return currentKeys.contains(KeyEvent.getExtendedKeyCodeForChar(keyChar));
	}
	
}
