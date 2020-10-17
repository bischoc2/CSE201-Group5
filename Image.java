import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class Image {
	private BufferedImage picture;
	
	/**
	 * Basic Constructor that reads in the image file
	 * @param fileName the image file name
	 * @throws IOException
	 */
	public Image(String fileName) throws IOException {
		picture = ImageIO.read(new File(fileName));
	}
	
	/**
	 * Crop the image
	 * @param height the height user want to crop
	 * @param width the width user want to crop
	 */
	public void crop(int height, int width) {
		
	}
	
	/**
	 * restore all the changes user made to the image
	 * @return true if restore successfully, else otherwise
	 */
	public boolean restore() {
		return false;
	}
	
	/**
	 * Stretch the image with user specified height and width
	 * @param height the height user want to stretch to 
	 * @param width the width user want to stretch to
	 */
	public void stretch(int height, int width) {
		
	}
	
	/**
	 * Change the saturation of the image with user specified number
	 * @param degree the number of degree that user want to change to
	 */
	public void saturation(int degree) {
		
	}
	
	/**
	 * Change the image into single color based on the user's choice
	 * @param R the red value for the color of the image
	 * @param G the green value for the color of the image
	 * @param B the blue value for the color of the image 
	 */
	public void monochrome(int R, int G, int B) {
		
	}
	
	/**
	 * Change the image to black and white
	 */
	public void blackNWhite() {
		
	}
	
	/**
	 * Mirror the image
	 */
	public void mirror() {
		
	}
}
