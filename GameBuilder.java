package game;

import mechanics.*;
import placeable.*;
import java.awt.Dimension;

public class GameBuilder {
	
	private GameInstance instance;
	private PixelPanel panel;
	private PixelScreen screen;
	private Game game;
	
	public GameBuilder(Dimension size, Game game) {
		this.game = game;
		initialize(size);
	}
	
	private void initialize(Dimension size) {
		screen = new PixelScreen(size);
		panel = new PixelPanel(screen);
		instance = new GameInstance(panel);
		instance.setGame(game);
		instance.startGame();
	}
	
	public static void main(String[] args) {
		GameBuilder build = new GameBuilder(new Dimension(20,20), new Game() {
			@Override
			public void initialize(GameInstance instance) {
			}
			@Override
			public void tick(int i) {}
		});
	}


}
