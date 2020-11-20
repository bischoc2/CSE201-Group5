import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/** Class that converts any image on disk into a BufferedImage, allowing it to me modified with the given tools.
 * 
 * @author Zeyu Su
 * @version 0.2
 * @since 10-14-2020
 * 
 */
@SuppressWarnings("serial")
public class Image implements Serializable {
	
	/**
	 * data representation of the physical image
	 */
	private transient BufferedImage picture;
	/**
	 * Path of the image on the disk
	 */
	private String ipath;
	/**
	 * Key for retrieval of this image
	 */
	private int key
	/**
	 * Name of the image
	 */;
	private String Name;
	private Color[][] colorSet;

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
		colorSet = new Color[picture.getHeight()][picture.getWidth()];
		recordColorSet();
	}

	private void recordColorSet() {
		for (int i = 0; i < picture.getHeight(); i++) {
			for (int j = 0; j < picture.getWidth(); j++) {
				colorSet[i][j] = new Color(picture.getRGB(j, i));
			}
		}
	}
	
	/**
	 * Sets records the key (position) of the image in the image library.
	 * 
	 * @param akey int key value
	 */
	public void setKey(int akey) {
		key = akey;
	}
	
	/**
	 * returns records the key (position) of the image in the image library.
	 * 
	 * @return int key value
	 */
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
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException();
		}
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
	 * @see Image#deepCopy(BufferedImage)
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
	
	/** Returns of a BufferedImage object identical to the one sent to it
	 * 
	 * @param temp BufferedImage to be copied
	 * @return copy of the BufferedImage object
	 */
	private BufferedImage deepCopy(BufferedImage temp) {
		ColorModel cm = temp.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = temp.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	
	/**
	 * Changes the saturation of the image with user specified number
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
	public void monochrome(int R, int G, int B) throws Exception {
		if(R > 255 || R < 0 || B > 255 || B < 0 || G > 255 || G < 0) {
			throw new IllegalArgumentException();
		}
		int width = picture.getWidth();
		int height = picture.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = colorSet[i][j];
				int red = (int)(c.getRed() * R / 255);
				int green = (int)(c.getGreen() * G / 255 );
				int blue = (int)(c.getBlue() * B / 255);
				Color newColor = new Color(red, green, blue);
				picture.setRGB(j, i, newColor.getRGB());
			}
		}
	}

	
	/** Flips the image over the Y-Axis
	 * 
	 */
	public void mirror() {
		int w = picture.getWidth();
		int h = picture.getHeight();
		BufferedImage temp = new BufferedImage(w, h, picture.getType());
		AffineTransform affineTransform = new AffineTransform(-1,0,0,1,w,0);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		picture = affineTransformOp.filter(picture, temp);
	}

	/** Returns the instance variable representing the modifiable image
	 * 
	 * @return picture instance variable
	 */
	public BufferedImage getPicture() {
		return picture;
	}

	/** Return the path of the current image
	 * 
	 * @return path instance variable
	 */
	public String getPath() {
		return ipath;
	}
	
	/** Sets the name of the current image
	 * 
	 * @param str New name for the current image
	 */
	public void setName(String str) {
		Name = str;
	}

	/**
	 * Return the name of the image
	 * @return the instance variable Name
	 */
	public String toString() {
		return Name;
	}
	
	/**
	 * Writes the object data for this object. Write the BufferedImage seperately
	 * @param oout2 stream currently writing out
	 * @see java.io.ObjectOutputStream#writeObject(Object)
	 * @see RedHawkPhotos#writeOut
	 */
	private void writeObject(ObjectOutputStream oout2) {
		try {
			oout2.defaultWriteObject();
			ImageIO.write(picture, "jpg", oout2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read in the object data for this object give the file UserList. Reads the BufferedImage seperately
	 * @param oin2 stream currently being read in
	 * @see java.io.ObjectInputStream#readObject(Object)
	 * @see RedHawkPhotos#writeIn
	 */
	private void readObject(ObjectInputStream oin2) {
		try {
			oin2.defaultReadObject();
			picture = ImageIO.read(oin2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
