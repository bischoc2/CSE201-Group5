import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;

/** <p>ImageViewer is a class that creates a User Interface editing and modifying images. The user class will call this function.
 * user will have there own ImageLibrary.
 * </p>
 * 
 * @author Josha Bonsu
 * @version 0.2
 * @since 10-14-2020
 */
public class ImageViewer {
	
	/**
	 * The JFrame the image and tools will be displayed on
	 */
	private JFrame imageFrame;
	/**
	 * The image object being displayed
	 */
	private Image image;
	/**
	 * The imgLabel that represent the image object being display; stores what will actually appear in the GUI
	 */
	private JLabel imgLabel;
	/**
	 * Records x coordinates of the cursors current position when the mouse is clicked on the image
	 * @see Image#crop(int, int, int, int)
	 */
	private int mx1;
	/**
	 * Records y coordinates of the cursors current position when the mouse is clicked on the image
	 * @see Image#crop(int, int, int, int)
	 */
	private int my1;
	/**
	 * Records x coordinates of the cursors current position when the mouse is clicked on the image
	 * @see Image#crop(int, int, int, int)
	 */
	private int mx2;
	/**
	 * Records y coordinates of the cursors current position when the mouse is clicked on the image
	 * @see Image#crop(int, int, int, int)
	 */
	private int my2;
	/**
	 * <p> int values that records the state for the crop function </p>
	 * 0 => Not active
	 * 1 => Active; recording first click
	 * 2 =? Active; recording second click
	 * @see Image#crop(int, int, int, int)
	 */
	private int cropFlag;
	
	/** <p>Basic constructor. Calls methods to create imageViewerUI </p>
	 * @param img image object to be viewed
	 */
	public ImageViewer(Image img) {
		
		image = img;
		JFrame iframe = new JFrame();
		iframe.setLayout(new BoxLayout(iframe.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel pic = new JLabel(new ImageIcon(img.getPicture()));
		imgLabel = pic;
		
		JScrollPane ipane = new JScrollPane(pic, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ipane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 13));
		ipane.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));
		ipane.addMouseListener(new MouseListener() 
	
		/**
		 * Inner class that records clicks for the crop function. Sets the instance variables <i> mx1 and my1 </i>
		 * to the x and y coordinates of the cursor at the first click when the cropFlag is 1, and sets the instance
		 * variable of the second the cursor at a secondary click when the cropFlag is to. cropFlag is set to 0 afterwards.
		 * instance variables are used to calculate parameters for the crop function.
		 * @see Image#crop(int, int, int, int)
		 */
		{

			@Override
			public void mouseClicked(MouseEvent e) {
				if(cropFlag == 1) {
					mx1 = e.getX();
					my1 = e.getY();
					System.out.println(mx1 + " " + my1);
					cropFlag = 2;
				}
				else if(cropFlag == 2) {
					try {
					mx2 = e.getX();
					my2 = e.getX();
					System.out.println(mx2 + " " + my2);
					image.crop(mx1, my1, mx2 - mx1, my2 - my1);
					imgLabel.setIcon(new ImageIcon(image.getPicture()));
					imgLabel.repaint();
					imageFrame.revalidate();
					cropFlag = 0;
					RedHawkPhotos.writeOut();
					} catch (Exception ei) {
						JOptionPane.showMessageDialog(new JFrame(), "Invalid input. Read crop instructions");
						cropFlag = 0;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		iframe.add(ipane);
		iframe.setSize(500, 800);
		iframe.setResizable(true);
		iframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		imageFrame = iframe;
		controlPanelSetUp();
		iframe.setVisible(true);
	}
	
	/** Sets up UI where modification tools are stored.
	 * 
	 */
	private void controlPanelSetUp() {
		JPanel ctrl = new JPanel();
		ctrl.setSize(500, 300);
		
		JTextField sat = new JTextField(6);
		JLabel satText = new JLabel("Saturation");
		sat.setActionCommand("sat");
		
		JButton mirror = new JButton("Mirror");
		mirror.setActionCommand("mir");
		
		JButton restore = new JButton("Restore");
		restore.setActionCommand("rest");
		
		JButton stretch = new JButton("Stretch Image");
		stretch.setActionCommand("str");
		
		JButton mono = new JButton("Monochrome");
		mono.setActionCommand("mono");
		
		JButton crop = new JButton("Crop");
		crop.setActionCommand("crop");
		
		ActionListener imgList = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("sat")) {
					double tempDouble  = Double.parseDouble(sat.getText() + "\n");
					image.saturation(tempDouble);
					imgLabel.repaint();
				}
				
				else if (e.getActionCommand().equals("mir")) {
					image.mirror();
					imgLabel.setIcon(new ImageIcon(image.getPicture()));
					imgLabel.repaint();
					imageFrame.revalidate();
				}
				
				else if (e.getActionCommand().equals("rest")) {
					try {
						image.restore();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(new JFrame(), "Restore File Not Found");
						e1.printStackTrace();
					}

					imgLabel.setIcon(new ImageIcon(image.getPicture()));
					imgLabel.repaint();
					imageFrame.revalidate();

				}
				
				else if (e.getActionCommand().equals("str")) {
					JPanel stretchOptions = new JPanel();
					stretchOptions.setLayout(new GridLayout(0, 2, 3, 3));
					
					JLabel width = new JLabel("Width: ");
					JLabel height = new JLabel("Height: ");
					JTextField wid = new JTextField(4);
					JTextField hig = new JTextField(4);
					
					stretchOptions.add(width);
					stretchOptions.add(wid);
					stretchOptions.add(height);
					stretchOptions.add(hig);
					
					int confirm = JOptionPane.showConfirmDialog(new JFrame(), stretchOptions, "Input Stretch Factor", JOptionPane.OK_CANCEL_OPTION);
					if(confirm == JOptionPane.OK_OPTION) {
						try {
							image.stretch(Double.parseDouble(wid.getText()), Double.parseDouble(hig.getText()));
						}
						catch(Exception e1) {
							JOptionPane.showMessageDialog(new JFrame(), "Scalers are too big / too small");
						}
						imgLabel.setIcon(new ImageIcon(image.getPicture()));
						imgLabel.repaint();
						imageFrame.revalidate();
					}
					
				}
				else if (e.getActionCommand().equals("mono")) {
					JPanel monoOptions = new JPanel();
					monoOptions.setLayout(new GridLayout(0, 2, 3, 3));
					
					JLabel red = new JLabel("Red: ");
					JLabel green = new JLabel("Green: ");
					JLabel blue = new JLabel("Blue: ");
					JTextField r = new JTextField(4);
					JTextField g = new JTextField(4);
					JTextField b = new JTextField(4);
					
					
					monoOptions.add(red);
					monoOptions.add(r);
					monoOptions.add(green);
					monoOptions.add(g);
					monoOptions.add(blue);
					monoOptions.add(b);
					
					int confirm = JOptionPane.showConfirmDialog(new JFrame(), monoOptions, "Input RGB value (0 - 255)", JOptionPane.OK_CANCEL_OPTION);
					if(confirm == JOptionPane.OK_OPTION) {
						try {
							image.monochrome(Integer.parseInt(r.getText()), Integer.parseInt(g.getText()), Integer.parseInt(b.getText()));
						}
						catch(Exception e1) {
							JOptionPane.showMessageDialog(new JFrame(), "RBG values must be between 0 and 255");
						}
						imgLabel.setIcon(new ImageIcon(image.getPicture()));
						imgLabel.repaint();
						imageFrame.revalidate();
					}
				}
				
				else if(e.getActionCommand().equals("crop")){
					
					if(cropFlag == 0) {
						JOptionPane.showMessageDialog(new JFrame(), "Select top left and bottom right of the cropped image. Click crop again to cancel");
						cropFlag = 1;
					}
					else
						cropFlag = 0;
				}
				RedHawkPhotos.writeOut();
			}
			
		};
		
		mirror.addActionListener(imgList);
		sat.addActionListener(imgList);
		restore.addActionListener(imgList);
		stretch.addActionListener(imgList);
		mono.addActionListener(imgList);
		crop.addActionListener(imgList);
		
		sat.setToolTipText("Increase or decrease the saturation of the image. Decrease: 0-100, Increase: 100+ ");
		restore.setToolTipText("Restore the image to the version on disk. Undos all changes before saving");
		mirror.setToolTipText("Flips the image vertically");
		stretch.setToolTipText("Scales the image given 2 multiples. Width: Scales the width of the image by the given value. Height: Scales the height of the image by the given value");
		mono.setToolTipText("Monochromes the image based on a new color by the given RGB values");
		crop.setToolTipText("Crops the image. Top left X and Top left Y: Top left coordinates for the new Image Width and Height: Width and Height of the new image, relative to the given top left corner");;
		
		ctrl.add(satText);
		ctrl.add(sat);
		ctrl.add(mirror);
		ctrl.add(restore);
		ctrl.add(stretch);
		ctrl.add(mono);
		ctrl.add(crop);
		imageFrame.add(ctrl);
	}
}
