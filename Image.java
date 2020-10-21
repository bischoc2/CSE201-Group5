import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;

public class Image {
	private BufferedImage picture;
	private String ipath;

	/**
	 * Basic Constructor that reads in the image file
	 * 
	 * @param fileName the image file name
	 * @throws IOException
	 */
	public Image(String fileName) throws IOException {
		ipath = fileName;
		picture = ImageIO.read(new File(fileName));
	}

	/**
	 * Crop the image
	 * 
	 * @param height the height user want to crop
	 * @param width  the width user want to crop
	 */
	public void crop(int height, int width) {

	}

	/**
	 * restore all the changes user made to the image
	 * 
	 * @return true if restore successfully, else otherwise
	 * @throws IOException 
	 */
	public boolean restore() throws IOException {
		picture = ImageIO.read(new File(ipath));
		return true;
	}

	/**
	 * Stretch the image with user specified height and width
	 * 
	 * @param height the height user want to stretch to
	 * @param width  the width user want to stretch to
	 */
	public void stretch(int height, int width) {

	}

	/**
	 * Change the saturation of the image with user specified number
	 * 
	 * @param degree the number of degree that user want to change to
	 */
	public void saturation(double degree) {
		int width = picture.getWidth();
		int height = picture.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(picture.getRGB(j, i));
				int r = (int)(c.getRed());
				int g = (int)(c.getGreen());
				int b = (int)(c.getBlue());
				float[] hsb = Color.RGBtoHSB(r, g, b, null);
				float hue = hsb[0];
				float saturation = hsb[1];
				float brightness = hsb[2];
				saturation = (float) (saturation * (degree / 100));
				int rgb = Color.HSBtoRGB(hue, saturation, brightness);
				r = (rgb>>16)&0xFF;
				g = (rgb>>8)&0xFF;
				b = rgb&0xFF;
				Color newColor = new Color(r, g, b);
				picture.setRGB(j, i, newColor.getRGB());
			}
		}
	}

	/**
	 * Change the image into single color based on the user's choice
	 * 
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
		int w = picture.getWidth();
		int h = picture.getHeight();
		BufferedImage temp = new BufferedImage(w, h, picture.getType());
		AffineTransform affineTransform = new AffineTransform(-1,0,0,1,w,0);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		picture = affineTransformOp.filter(picture, temp);
	}

	public BufferedImage getPicture() {
		return picture;
	}

	public String getPath() {
		return ipath;
	}

	public String toString() {
		return ipath.substring(ipath.lastIndexOf("\\", ipath.length() - 1) + 1);
	}
}
