package toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import constants.DisplayMode;
import constants.Shape;
import game.Game;
import game.GameBuilder;
import mechanics.GameInstance;
import mechanics.PixelScreen;
import placeable.PixelObject;

import java.awt.Dimension;

public class TextReader {
	
	public static HashMap<Character, ArrayList<Object>> map;
	
	public static HashMap<Character, ArrayList<Object>> read(String font) {
		HashMap<Character, ArrayList<Object>> map = new HashMap<Character, ArrayList<Object>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(font + ".txt"));
			for (int i = 32; i <= 126; i++) {
				String line = reader.readLine();
				if (!line.equals("/")) {
					String[] parts = line.split(" ");
					System.out.println(line);
					ArrayList<Object> list = new ArrayList<Object>();
					list.add(parts[0]);
					list.add(new Dimension(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
					map.put((char) i, list);
				}
			}
			reader.close();
			return map;
		} catch (Exception e) {e.printStackTrace();return map;}//TODO
	}
	
	public static void editFont(String font) {
		map = new HashMap<Character, ArrayList<Object>>();
		if (new File(font + ".txt").exists())
			map = read(font);
		
		GameBuilder builder = new GameBuilder(new Dimension(20,20), new Game() {
			
			PixelScreen screen;
			GameInstance instance;
			char currChar;
			
			@Override
			public void initialize(GameInstance instance) {
				this.instance = instance;
				screen = instance.getContentPane().getScreen();
				screen.stamp(6, 0, Shape.rectangle(0, 0, 1, 5).toStamp());
				currChar = (char) 32;
			}
			
			@Override
			public void buttonPressed(int x, int y) {
				if (x + y == 38) {
					updateChar();
					currChar++;
					screen.removeAll();
					loadChar();
				}
				else if (x == 0 && y == 19) {
					updateChar();
					currChar--;
					screen.removeAll();
					loadChar();
				}
				else {
					screen.add(new PixelObject("1", x, y, new Dimension(1,1), DisplayMode.SWITCH));
				}
				instance.setTitle(String.valueOf(currChar));
			}
			
			private void updateChar() {
				ArrayList<Object> list = new ArrayList<Object>();
				//map.put(currChar, /*Hier muss der Bildschirm eingelesen werden*/null);
			}
			
			private void loadChar() {
				ArrayList<Object> list = map.get(currChar);
				if (list != null) {
					PixelObject letter = new PixelObject((String) list.get(0), 0, 0, (Dimension) list.get(1));
					screen.add(letter);
				}
				
			}

			@Override
			public void tick(int i) {
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		TextReader.editFont("pixagonal");
	}
}
