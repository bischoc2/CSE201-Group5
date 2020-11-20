import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.HashMap;
import java.util.Set;

/** <p>ImageLibrary is a class that creates a User Interface for uploading and storing images. The user class will call this function.
 * user will have there own ImageLibrary.
 * </p>
 * 
 * @author Josha Bonsu
 * @version 0.2
 * @since 10-14-2020
 * @see 
 */
@SuppressWarnings("serial")
public class ImageLibrary implements Serializable {

	/**
	 * Main JFrame where images in the image library can be selected and managed
	 */
	transient protected JFrame mainFrame;
	/**
	 * Text displaying the path of the image
	 */
	private JTextArea infoText;
	/**
	 * List of images to be displayed in the image library
	 */
	transient private JList<Image> albumList;
	/**
	 * List of images the user has uploaded
	 */
	transient private HashMap<Integer, Image> album;
	/**
	 * List model for albumList
	 */
	transient private DefaultListModel<Image> model;
	/**
	 * Records the current image selected in the image library
	 */
	private Image selectedImage;
	/**
	 * Records the current size of the image library
	 */
    private int size;

	/**
	 * Basic Constructor, creates a new ImageLibrary and calls methods to set up the UI
	 * 
	 */
	public ImageLibrary() {
		album = new HashMap<Integer, Image>();
		frameSetUp();
		scrollSetUp();
		infoSetUp();
	}
	
	/**
	 * Sets up the main JFrame, stores it in the instance variable mainFrame
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
				if (selectedImage == null) {
					infoText.setText("Path: ");
				} else {
					String infstr = "Path: " + selectedImage.getPath();
					infoText.setText(infstr);
				}
			}

		});

		JScrollPane pane = new JScrollPane(imageFile, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 13));
		pane.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));
		mainFrame.add(pane);
		albumList = imageFile;
	}
	
	void InscrollSetUp() {
		JList<Image> imageFile = new JList<>();
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
				if (selectedImage == null) {
					infoText.setText("Path: ");
				} else {
					String infstr = "Path: " + selectedImage.getPath();
					infoText.setText(infstr);
				}
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
	 * Sets up info pane; UI where users will manage their images
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
		JButton search = new JButton("search");
		JButton rename = new JButton("rename");
		
		save.setToolTipText("Save the image to disk");
		upload.setToolTipText("Upload a new image from disk to the Photo Library");
		delete.setToolTipText("Delete the selected image from the Photo Library");
		view.setToolTipText("Open and edit the selected image from the Photo Library");
		search.setToolTipText("Search for an image in the Photo Library");
		rename.setToolTipText("Renames the selected image");

		upload.setActionCommand("upload");
		view.setActionCommand("view");
		save.setActionCommand("save");
		delete.setActionCommand("delete");
		search.setActionCommand("search");
		rename.setActionCommand("rename");
		
		ActionListener upList = new UpList();
		upload.addActionListener(upList);
		view.addActionListener(upList);
		save.addActionListener(upList);
		delete.addActionListener(upList);
		search.addActionListener(upList);
		rename.addActionListener(upList);
		
		mButtons.add(save);
		mButtons.add(upload);
		mButtons.add(delete);
		mButtons.add(view);
		mButtons.add(search);
		mButtons.add(rename);

		JTextArea info = new JTextArea("Path: ");
		info.setEditable(false);
		imageInfo.add(info);
		imageInfo.add(mButtons);
		mainFrame.add(imageInfo);
		infoText = info;
	}
	
	/**<p>The UpList Class is an inner class acting as an action listener, meant to track events on the main UI:</p>
	 * <p>Save</p>
	 * <p>Upload</p>
	 * <p>Delete</p>
	 * <p>And view</p>
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
				viewImage(selectedImage);
			}
			else if(e.getActionCommand().equals("save")) {
				saveImage(selectedImage);
			}
			else if(e.getActionCommand().equals("delete")) {
				deleteImage(selectedImage);
			}
			else if(e.getActionCommand().equals("search")) {
				searchImage();
			}
			else if(e.getActionCommand().equals("rename")) {
				JPanel namePanel = new JPanel();
				namePanel.setLayout(new GridLayout(0, 2, 3, 3));
				
				JLabel namel = new JLabel("New Name:");
				JTextField nl = new JTextField(10);
				
				namePanel.add(namel);
				namePanel.add(nl);
				
				int confirm = JOptionPane.showConfirmDialog(new JFrame(), namePanel, "Enter new name", JOptionPane.OK_CANCEL_OPTION);
				if(confirm == JOptionPane.OK_OPTION) {
					selectedImage.setName(nl.getText());
					albumList.updateUI();
				}
			}
		}
	}

	/**
	 * Creates a new image object and adds it to the hashMap of images
	 * 
	 * @return true if the images was successfully uploaded, false if otherwise.
	 */
	boolean uploadImage() {
		FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("Images", "png", "jpg", "jpeg");
		JFileChooser inImg = new JFileChooser();
		inImg.setFileFilter(imgFilter);
		inImg.setAcceptAllFileFilterUsed(false);
		int approved = inImg.showOpenDialog(mainFrame);
		if (approved == JFileChooser.APPROVE_OPTION) {
			File imf = inImg.getSelectedFile();
			try {
				File f = new File(imf.getPath());
				BufferedImage temp = ImageIO.read(f);
				album.put(size, new Image(imf.getPath()));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid File Type");
				return false;
			}
			System.out.println(size);
			album.get(size).setKey(size); 
			System.out.println(album.get(size).getKey());
			model.addElement(album.get(size));
			System.out.println(model.indexOf(album.get(size)));
			albumList.setModel(model);
			System.out.println(size);
			size += 1;
			RedHawkPhotos.writeOut();
		}
		return true;
	}

	/** Create ImageViewer object; creates UI where images can be viewed and edited
	 *  
	 * @return true if the imageViewer was created. False if otherwise
	 */
	boolean viewImage(Image img) {
		if(img == null) {
			JOptionPane.showMessageDialog(new JFrame(), "No File Selected");
			throw new NullPointerException();
		}
		try {
			ImageViewer a = new ImageViewer(img);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid file");
			e.printStackTrace();
			return false;
		}
		RedHawkPhotos.writeOut();
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
		if(img == null) {
			JOptionPane.showMessageDialog(new JFrame(), "No File Selected");
			throw new NullPointerException();
		}
		try {
			int imageKey = img.getKey();
			model.removeElement(img);
			System.out.println(imageKey);
			album.remove(imageKey);
			System.out.println(imageKey);
			System.out.println(size);
			for (int j = 0; j < size; j++) {
				if (j > imageKey) {
					int i;
					i = album.get(j).getKey();
					i = i - 1;
					album.get(j).setKey(i);
					album.put(i, album.get(j));
				}
			}
			albumList.setModel(model);
			size -= 1;
			RedHawkPhotos.writeOut();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Saves adjustments made to the image that is currently opened. It will
	 * override the current object with a new image object
	 * 
	 * @param img Image object to override/save
	 * @return true if the image was successfully saved, false if otherwise
	 */
	boolean saveImage(Image img) {
		if(img == null) {
			JOptionPane.showMessageDialog(new JFrame(), "No File Selected");
			throw new NullPointerException();
		}
		try {
			File savedImage = new File(img.getPath());
			ImageIO.write(img.getPicture(), "jpg", savedImage);
			RedHawkPhotos.writeOut();
			return true;
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Unkown Error when saving");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Searches for an image give a string
	 * 
	 * @param img Image object to be searched
	 * @return index where image is stored
	 */
	void searchImage() {
		JPanel searchOptions = new JPanel();
		searchOptions.setLayout(new GridLayout(0, 2, 3, 3));
		
		JLabel fileName = new JLabel("File Name: ");
		JTextField namef = new JTextField(10);

		searchOptions.add(fileName);
		searchOptions.add(namef);
		
		int confirm = JOptionPane.showConfirmDialog(new JFrame(), searchOptions, "Input File Name", JOptionPane.OK_CANCEL_OPTION);
		if(confirm == JOptionPane.OK_OPTION) {
			try {
				DefaultListModel<Image> searchmodel = new DefaultListModel<>();
				Set<Integer> keys = album.keySet();
				for (int elt : keys) {
					if (album.get(elt).toString().equals(namef.getText())) {
						searchmodel.addElement(album.get(elt));
					}
				}
				JList<Image> searchList = new JList<>();
				searchList.setModel(searchmodel);
				JScrollPane searchpane = new JScrollPane(searchList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				searchpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 13));
				searchpane.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));
				int confirm2 = JOptionPane.showConfirmDialog(new JFrame(), searchpane, "Select Image", JOptionPane.OK_CANCEL_OPTION);
				if(confirm2 == JOptionPane.OK_OPTION) {
					viewImage(searchList.getSelectedValue());
				}
			}
			catch(Exception e1) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid File Name");
			}
		}
	}
	
	/**
	 * writes the object data for this object
	 * @param oout1 stream currently writing out
	 * @see java.io.ObjectOutputStream#writeObject(Object)
	 * @see RedHawkPhotos#writeOut
	 */
	private void writeObject(ObjectOutputStream oout1) {
		try {
			oout1.defaultWriteObject();
			oout1.writeObject(albumList);
			oout1.writeObject(album);
			oout1.writeObject(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the object data for this object and repreforms setup for the main JFrame
	 * @param oin1 stream currently reading in
	 * @see java.io.ObjectInputStream#readObject(Object)
	 * @see RedHawkPhotos#writeIn
	 */
	private void readObject(ObjectInputStream oin1) {
		try {
			oin1.defaultReadObject();
			albumList = (JList<Image>)oin1.readObject();
			album = (HashMap<Integer, Image>)oin1.readObject();
			model = (DefaultListModel<Image>)oin1.readObject();
			mainFrame = new JFrame();
			frameSetUp();
			InscrollSetUp();
			infoSetUp();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
