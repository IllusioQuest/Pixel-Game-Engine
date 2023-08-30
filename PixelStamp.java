package placeable;

import java.awt.Dimension;

import toolkit.DefinitionConvert;
import toolkit.Pixel;

/**
 * An object that can be placed on a {@link OldTestPixelPanel}. It is only used like a stamp and can't be moved.
 * 
 * @author JR
 */

public class PixelStamp {

	/**
	 * This array of integers symbolizes every pixel the PixelStamp contains. It is used in the form
	 * {@code binaryDefinition[x][y]} where each integer has the value 0 if the pixel is blank or 1
	 * if it's coloured. In order to use it, the {@link #size} of the stamp is needed.
	 * @see #PixelStamp(String, Dimension)
	 */
	private int[][] binaryDefinition;
	
	
	/**
	 * This contains both, the width and the height of the stamp.
	 */
	private Dimension size;

	
	/**
	 * Creates a new {@link PixelStamp} based on its binary definition and its size.
	 * @param def a String containing a series of 0's and 1's, being the flatmapped value of {@link #binaryDefinition}
	 * @param size size of the stamp in pixels
	 */
	public PixelStamp(String def, Dimension size) {
		this.size = size;
		binaryDefinition = DefinitionConvert.binaryToBinary(def, size);
		pixels = DefinitionConvert.binaryToPixels(binaryDefinition);
	}
	
	/**
	 * @return width of the stamp
	 * @see #size
	 */
	public int getWidth() {
		return (int) size.getWidth();
	}
	
	/**
	 * @return height of the stamp
	 * @see #size
	 */
	public int getHeight() {
		return (int) size.getHeight();
	}
	
	/**
	 * @return {@link #binaryDefinition}
	 */
	public int[][] getBinaryDefinition() {
		return binaryDefinition;
	}
	
	/**
	 * @param def a String containing a series of 0's and 1's, being the flatmapped value of {@link #binaryDefinition}
	 */
	public void setBinaryDefinition(String def) {
		binaryDefinition = new int[(int) size.getWidth()][(int) size.getHeight()];
		int i = 0;
		for (int x = 0; x < size.getWidth(); x++) {
			for (int y = 0; y < size.getHeight(); y++) {
				binaryDefinition[x][y] = Integer.parseInt(new String(new char[] {def.charAt(i)}));
				i++;
			}
		}
	}
	
	//Everything below this line's deprecated. Still needed because some other classes are still using those old methods and the two constructors.
	
	/**
	 * A String consisting of numbers that defines the PixelStamp. It has the form
	 * <b>xxyy xxyy</b>..., where the number of x's and the number of y's per block is defined by
	 * {@link #coordinatesLength}. 
	 * @deprecated Use {@link #binaryDefinition} instead.
	 */
	private String definition;

	/**
	 * Defines the length of one coordinate in {@link #definition}.
	 * @deprecated Only needed for the also deprecated definition. Use {@link #binaryDefinition} instead.
	 */
	private int coordinatesLength;	
	
	private Pixel[] pixels;
	
	/**
	 * Creates a new {@link PixelStamp} and automatically sets
	 * {@link #coordinatesLength} to 2.
	 * @param def a String containing the value of {@link #definition}
	 * @deprecated Use {@link #PixelStamp(String, Dimension)} instead.
	 */
	public PixelStamp(String def) {
		setDefinition(def);
		coordinatesLength = 2;
		pixels = DefinitionConvert.singlePointsToPixels(def, coordinatesLength);
	}

	/**
	 * Creates a new {@link PixelStamp} based on its definition and
	 * coordinatesLength.
	 * @param def  a String containing the value of {@link #definition}
	 * @param coLe an int containing the value of {@link #coordinatesLength}
	 * @deprecated Use {@link #PixelStamp(String, Dimension)} instead.
	 */
	public PixelStamp(String def, int coLe) {
		setDefinition(def);
		this.coordinatesLength = coLe;
		pixels = DefinitionConvert.singlePointsToPixels(def, coLe);
	}
	
	
	/**
	 * @return current {@link #definition}
	 * @deprecated Definition shouldn't be used anymore. Instead you should use {@link #binaryDefinition}
	 */
	public String getDefinition() {
		return definition;
	}
	
	public Pixel[] getPixels() {
		return pixels;
	}

	/**
	 * @param definition {@link #definition} to set
	 * @see #setDefinition(String, int)
	 * @deprecated Definition shouldn't be used anymore. Instead you should use {@link #binaryDefinition}
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * @param definition {@link #definition} to set
	 * @param coLe {@link #coordinatesLength} to set
	 * @see #setDefinition(String)
	 * @deprecated Definition shouldn't be used anymore. Instead you should use {@link #binaryDefinition}
	 */
	public void setDefinition(String definition, int coLe) {
		this.definition = definition;
		this.coordinatesLength = coLe;
	}

	/**
	 * @return current {@link coordinatesLength}
	 * @deprecated Definition shouldn't be used anymore. Instead you should use {@link #binaryDefinition}
	 */
	public int getCoordinatesLength() {
		return coordinatesLength;
	}

	/**
	 * Extracts one x- or one y-Coordinate out of the {@link #definition} based on the {@link #coordinatesLength}
	 * 
	 * @param place int that defines the character's index to start at
	 * @return coordinate
	 * @deprecated Definition shouldn't be used anymore. Instead you should use {@link #binaryDefinition}
	 */
	public int coordinates(int place) {
		int x = 0;
		for (int i = place + coordinatesLength - 1, y = 0; y < coordinatesLength; i--, y++) {
			x += Integer.parseInt(String.valueOf(definition.charAt(i))) * Math.pow(10, y);
		}
		return x;
	}
	
}
