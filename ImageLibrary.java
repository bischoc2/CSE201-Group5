import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;

/** <p>ImageLibrary is a class that creates a User Interface for uploading and storing images. The user class will call this function.
 * user will have there own ImageLibrary.
 * </p>
 * 
 * @author Josha Bonsu
 * @version 0.2
 * @since 10-14-2020
 */
public class ImageLibrary {

	protected JFrame mainFrame;
	protected JTextArea infoText;
	protected JList<Image> albumList;
	protected HashMap<Integer, Image> album;
	protected DefaultListModel<Image> model;
	protected Image selectedImage;

	/**
	 * Basic Constructor
	 * 
	 */
	public ImageLibrary() {
		album = new HashMap<Integer, Image>();
		frameSetUp();
		scrollSetUp();
		infoSetUp();
		//mainFrame.setVisible(true); // this line might be put in another class?
	}
	
	/**
	 * Sets up the main JFrame
	 * 
	 */
	void frameSetUp() {
		JFrame testFrame = new JFrame();
		testFrame.setSize(700, 400);
		testFrame.setResizable(false);
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.setLayout(new GridLayout(0, 2));
		mainFrame = testFrame;
	}

	/**
	 * Sets up the scroll plane in which the objects in the image album are listed
	 * 
	 */
	void scrollSetUp() {
		JList<Image> imageFile = new JList<>();
		model = new DefaultListModel<>();
		imageFile.setModel(model);
		imageFile.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedImage = imageFile.getSelectedValue();
				/*
				 * Generic image info for testing String name = "File Name: " +
				 * selectedImage.name + "\n"; String size = "File Size: " + selectedImage.size +
				 * "\n"; more/accurate info will go here later
				 */

				String infstr = "Path: " + selectedImage.getPath();
				infoText.setText(infstr);
			}

		});

		JScrollPane pane = new JScrollPane(imageFile, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 13));
		pane.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));
		mainFrame.add(pane);
		albumList = imageFile;
	}


	/**
	 * Sets up info plane
	 * 
	 */
	void infoSetUp() {
		JPanel imageInfo = new JPanel();
		imageInfo.setLayout(new GridLayout(2, 0));
		JPanel mButtons = new JPanel();

		JButton save = new JButton("save");
		JButton upload = new JButton("upload");
		JButton delete = new JButton("delete");
		JButton view = new JButton("open");
		
		save.setToolTipText("Save the image to disk");
		upload.setToolTipText("Upload a new image from disk to the Photo Library");
		delete.setToolTipText("Delete the selected image from the Photo Library");
		view.setToolTipText("Open and edit the selected image from the photo library");

		upload.setActionCommand("upload");
		view.setActionCommand("view");
		save.setActionCommand("save");
		ActionListener upList = new UpList();
		upload.addActionListener(upList);
		view.addActionListener(upList);
		save.addActionListener(upList);
		mButtons.add(save);
		mButtons.add(upload);
		mButtons.add(delete);
		mButtons.add(view);

		JTextArea info = new JTextArea("Path: ");
		info.setEditable(false);
		imageInfo.add(info);
		imageInfo.add(mButtons);
		mainFrame.add(imageInfo);
		infoText = info;
	}
	
	/**<p>The UpList Class is an inner class acting as an action listener, meant to track events on the main UI:</p>
	 * Save
	 * Upload
	 * Delete
	 * And view
	 * 
	 * @author Josha Bonsu
	 * @version 0.12
	 * @since 10-14-2020
	 */
	class UpList implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("upload"))
				uploadImage();
			else if (e.getActionCommand().equals("view")) {
				viewImage();
			}
			else if(e.getActionCommand().equals("save")) {
				saveImage(selectedImage);
			}
		}
	}

	/**
	 * Creates a new image object and adds it to the hashMap of images
	 * 
	 * @return true if the images was successfully uploaded, false if otherwise.
	 */
	boolean uploadImage() {
		JFileChooser inImg = new JFileChooser();
		int approved = inImg.showOpenDialog(mainFrame);
		if (approved == JFileChooser.APPROVE_OPTION) {
			File imf = inImg.getSelectedFile();
			try {
				album.put(album.size(), new Image(imf.getPath()));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid File Path");
				return false;
			}
			model.addElement(album.get(album.size() - 1));
			albumList.setModel(model);
		}
		return true;
	}

	/** Create ImageViewer object; creates UI where images can be viewed and edited
	 *  
	 * @return true if the imageViewer was created. False if otherwise
	 */
	boolean viewImage() {
		try {
			ImageViewer a = new ImageViewer(selectedImage);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid file");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * removes an image from the image hashMap
	 * 
	 * @param img Image object to be removed
	 * @return true if the image object was successfully removed from the hashmap,
	 *         false if otherwise
	 */
	boolean deleteImage(Image img) {
		return false;
	}

	/**
	 * Saves adjustments made to the image that is currently opened. It will
	 * override the current object with a new image object
	 * 
	 * @param img Image object to override/save
	 * @return true if the image was successfully saved, false if otherwise
	 */
	boolean saveImage(Image img) {
		try {
			File savedImage = new File(img.getPath());
			ImageIO.write(img.getPicture(), "jpg", savedImage);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * searches for an image
	 * 
	 * @param img Image object to be searched
	 * @return index where image is stored
	 */
	int seachImage(Image img) {
		return 1;
	}
}
