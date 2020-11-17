import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/** Class that converts any image on disk into a BufferedImage, allowing it to me modified with the given tools 
 * 
 * @author Zeyu Su
 * @version 0.2
 * @since 10-14-2020
 */
public class Image implements Serializable {
	private transient BufferedImage picture;
	private String ipath;
	private int key;
	private String Name;

	/**
	 * Basic Constructor that reads in the image file
	 * 
	 * @param fileName the image file name
	 * @throws IOException
	 */
	public Image(String fileName) throws IOException {
		ipath = fileName;
		picture = ImageIO.read(new File(fileName));
		Name = ipath.substring(ipath.lastIndexOf("\\", ipath.length() - 1) + 1);
	}
	
	/**
	 * Sets records the key (position) of the image in the image library.
	 * 
	 * @param akey int key value
	 */
	public void setKey(int akey) {
		key = akey;
	}
	
	public int getKey() {
		return key;
	}

	/**
	 * Crop the image
	 * 
	 * @param x the x-coordinate of the cropped image
	 * @param y the y-coordinate of the cropped image
	 * @param height the height user want to crop
	 * @param width  the width user want to crop
	 */
	public void crop(int x, int y, int width, int height) {
		int maxWidth = picture.getWidth();
		int maxHeight = picture.getHeight();
		width = width < maxWidth ? width : maxWidth;
		height = height < maxHeight ? height : maxHeight;
		x = x < (maxWidth - width) ? x : (maxWidth - width);
		y = y < (maxHeight - height) ? y : (maxHeight - height);
		picture = picture.getSubimage(x, y, width, height);
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
	 * @param height the scale of height user want to stretch to
	 * @param width  the scale of width user want to stretch to
	 */
	public void stretch(double width, double height) {
		int w = picture.getWidth();
		int h = picture.getHeight();
		BufferedImage temp = deepCopy(picture);
		int scaledHeight = (int)(h*height);
		int scaledWidth = (int)(w*width);
		BufferedImage after = new BufferedImage(scaledWidth, scaledHeight, picture.getType());
		Graphics2D g2d = after.createGraphics();
		g2d.drawImage(temp, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();
		picture = after;
	}
	
	private BufferedImage deepCopy(BufferedImage temp) {
		ColorModel cm = temp.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = temp.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
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
		int width = picture.getWidth();
		int height = picture.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(picture.getRGB(j, i));
				int red = (int)(c.getRed() * R / 255);
				int green = (int)(c.getGreen() * G / 255 );
				int blue = (int)(c.getBlue() * B / 255);
				Color newColor = new Color(red, green, blue);
				picture.setRGB(j, i, newColor.getRGB());
			}
		}
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
	
	public void setName(String str) {
		Name = str;
	}

	public String toString() {
		return Name;
	}
}
