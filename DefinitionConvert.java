package toolkit;

import java.awt.Dimension;
import java.util.ArrayList;

public class DefinitionConvert {

	public static int[][] binaryToBinary(String binaryDef, Dimension size) {
		int [][] binaryDefinition = new int[(int) size.getWidth()][(int) size.getHeight()];
		int i = 0;
		for (int y = 0; y < size.getHeight(); y++) { 
			for (int x = 0; x < size.getWidth(); x++) {
				binaryDefinition[x][y] = Integer.parseInt(new String(new char[] {binaryDef.charAt(i)}));
				i++;
			}
		}
		return binaryDefinition;
	}
	
	public static Pixel[] singlePointsToPixels(String singlePointsDef, int coordinatesLength) {
		Pixel[] pixels = new Pixel[singlePointsDef.length() / 2 / coordinatesLength];
		for (int i = 0, x = 0; i < singlePointsDef.length(); i += coordinatesLength*2, x++) {
			pixels[x] = new Pixel(coordinates(singlePointsDef, coordinatesLength, i),coordinates(singlePointsDef, coordinatesLength, i+coordinatesLength), 1);
			}
		return pixels;
	}
	
	public static String binaryToBinary(int[][] binaryDefinition) {
		String binaryDef = "";
		for (int y = 0; y < binaryDefinition[0].length; y++) {
			for (int x = 0; x < binaryDefinition.length; x++) {
				binaryDef += binaryDefinition[x][y];
			}
		}
		return binaryDef;
	}
	
	public static Pixel[] binaryToPixels(int[][] binaryDefinition) {
		
		ArrayList<Pixel> pixelsList = new ArrayList<Pixel>();
		for (int y = 0; y < binaryDefinition[0].length; y++) {
			for (int x = 0; x < binaryDefinition.length; x++) {
				if (binaryDefinition[x][y] > 0)
					pixelsList.add(new Pixel(x,y,binaryDefinition[x][y]));
			}
		}
		return pixelsList.toArray(new Pixel[pixelsList.size()]);
	}
	
	public static int[][] pixelsToBinary(Pixel[] pixels) {
		Dimension size = getMinSize(pixels);
		int[][] binaryDefinition = new int[(int) size.getWidth()][(int) size.getHeight()];
		for (int x = 0; x < binaryDefinition.length; x++) {
			for (int y = 0; y < binaryDefinition[0].length; y++) {
				binaryDefinition[x][y] = 0;
			}	
		}
		for (Pixel pixel : pixels) {
			binaryDefinition[pixel.getX()][pixel.getY()] = pixel.getValue();
		}
		return binaryDefinition;
	}
	
	public static String pixelsToSinglePoints(Pixel[] pixels, int coordinatesLength) {
		String singlePointsDef = "";
		String temp = "";
		for (Pixel p : pixels) {
			temp = String.valueOf(p.getX());
			while (temp.length() < coordinatesLength) {
				temp = "0" + temp;
			}
			singlePointsDef += temp;
			temp = String.valueOf(p.getY());
			while (temp.length() < coordinatesLength) {
				temp = "0" + temp;
			}
			singlePointsDef += temp;
		}
		return singlePointsDef;
	}
	
	public static Pixel[] binaryToPixels(String binaryDef, Dimension size) {
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		int i = 0;
		for (int y = 0; y < size.getHeight(); y++) { 
			for (int x = 0; x < size.getWidth(); x++) {
				if (binaryDef.charAt(i) != '0') {
					pixels.add(new Pixel(x,y,1/*TODO*/));
				}
				i++;
			}
		}
		return pixels.toArray(new Pixel[pixels.size()]);
	}
	
	public static Dimension getMinSize(Pixel[] pixels) {
		int minX = 0, minY = 0, maxX = 0, maxY = 0;
		for (Pixel p : pixels) {
			if (p.getX() < minX) {
				minX = p.getX();
			}
			if (p.getY() < minY) {
				minY = p.getY();
			}
			if (p.getX() > maxX) {
				maxX = p.getX();
			}
			if (p.getY() > maxY) {
				maxY = p.getY();
			}
		}
		return new Dimension(maxX - minX + 1, maxY - minY + 1);
	}
	
	private static int coordinates(String singlePointsDef, int coordinatesLength, int place) {
		int x = 0;
		for (int i = place + coordinatesLength - 1, y = 0; y < coordinatesLength; i--, y++) {
			x += Integer.parseInt(String.valueOf(singlePointsDef.charAt(i))) * Math.pow(10, y);
		}
		return x;
	}
	
	public static void main(String[] args) {
		//See debug mode to get the results
		Pixel[] pixelsA = singlePointsToPixels("00000200040001010201030102020103030300040404", 2);
		int[][] binaryDefinitionA = pixelsToBinary(pixelsA);
		String x = binaryToBinary(binaryDefinitionA);
		
		int[][] binaryDefinitionB = binaryToBinary("1010101110001000101010001", new Dimension(5,5));
		Pixel[] pixelsB = binaryToPixels(binaryDefinitionB);
		String y = pixelsToSinglePoints(pixelsB, 1);
		
		Pixel[] pixelsC = binaryToPixels("1010101110001000101010001", new Dimension(5,5));
		
		System.out.println();
	}
	
}
