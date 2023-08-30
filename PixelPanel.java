package mechanics;
import javax.swing.*;
import java.awt.event.*;
import placeable.PixelObject;

import java.awt.*;

public class PixelPanel extends JPanel implements ActionListener{

	private PixelScreen screen;
	private JButton[][] buttons;
	private GameInstance game;
	
	public PixelPanel(PixelScreen screen) {
		super();
		this.screen = screen;
		screen.setPanel(this);
		setLayout(new GridLayout(screen.getWidth(), screen.getHeight()));
		initialize();
	}
	
	private void initialize() {
		System.out.println("PixelPanel.initialize()");
		screen.update();
		buttons = new JButton[screen.getWidth()][screen.getHeight()];
		for (int y = 0; y < screen.getHeight(); y++) {
			for (int x = 0; x < screen.getWidth(); x++) {
				buttons[x][y] = new JButton();
				buttons[x][y].addActionListener(this);
				buttons[x][y].setBackground(screen.getPixelColors(x,y));
				add(buttons[x][y]);
			}
		} 
	}
	
	public void update() {
		screen.update();
		for (int y = 0; y < screen.getHeight(); y++) {
			for (int x = 0; x < screen.getWidth(); x++) {
				buttons[x][y].setBackground(screen.getPixelColors(x,y));
			}
		}
	}
	
	protected void setGameInstance(GameInstance game) {
		this.game = game;
	}
	
	public GameInstance getGameInstance() {
		return game;
	}
	
	public PixelScreen getScreen() {
		return screen;
	}
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800,800);
		PixelScreen screen = new PixelScreen(new Dimension(20,20));
		PixelObject x = new PixelObject("101010101", 0,0, new Dimension(3,3));
		screen.add(x);
		PixelPanel panel = new PixelPanel(screen);
		frame.add(panel);
		frame.setVisible(true);
		panel.update();
		for (int i = 0; i < 400; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			x.moveX(1);
			if(i % 20 == 19) {
				screen.toggleDarkMode();
				x.move(-20, 1);
			}
			panel.update();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source =  e.getSource();
		for (int y = 0; y < screen.getHeight(); y++) {
			for (int x = 0; x < screen.getWidth(); x++) {
				if (buttons[x][y] == source) {
					game.buttonPressed(x, y);
				}
			}
		}
	}
	
}
