import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

/** The RedHawkPhotos class in the driver for the entire RedHawk Photos project. Lets users login and interact with their own image libraries
 *  @author Josha Bonsu
 *  @version 0.2
 *  @since 10-14-2020
 */
public class RedHawkPhotos {
	
	static ArrayList<User> users = new ArrayList<User>();
	
	/** Main driver; Allows users to log in and open said users' ImageLibrary
	 * 
	 * @param args
	 */
	public static void main (String[] args) {
		writeIn();
		User a = userSetUp();
		JFrame logPanel = new JFrame();
		logPanel.setSize(400, 200);
		logPanel.setResizable(false);
		logPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel logpanel = new JPanel();
		JLabel un = new JLabel("User Name");
		JLabel pw = new JLabel("Password");
		JTextField userN = new JTextField(30);
		JTextField passW = new JTextField(30);
		JButton login = new JButton("login");
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(a.login(userN.getText(), passW.getText())){
					a.getImageLibrary().mainFrame.setVisible(true);
					logPanel.setVisible(false);
				
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "Invalid Credentials");
				}
			}
		});
		
		a.getImageLibrary().mainFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				  logPanel.setVisible(true);
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		logpanel.add(un);
		logpanel.add(userN);
		logpanel.add(pw);
		logpanel.add(passW);
		logpanel.add(login);
		logPanel.add(logpanel);
		logPanel.setVisible(true);
	}
	
	/** Displays the interface for creating new users and logging in to a specific account
	 * 
	 * @return the User object representing the user currently logging in
	 */
	public static User userSetUp() {
		JLabel message = new JLabel("Are you a retuning user?");
		int yesorno = JOptionPane.showConfirmDialog(new JFrame(), message, "User setup", JOptionPane.YES_NO_OPTION);
		if (yesorno == JOptionPane.YES_OPTION) {
			JList<User> userList = new JList<User>();
			DefaultListModel<User> usermodel = new DefaultListModel<>();
			for(User elt : users) {
				usermodel.addElement(elt);
			}
			userList.setModel(usermodel);
			JScrollPane userpane = new JScrollPane(userList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			userpane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 13));
			userpane.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));
			
			int confirm = JOptionPane.showConfirmDialog(new JFrame(), userpane, "Select User", JOptionPane.OK_CANCEL_OPTION);
			if(confirm == JOptionPane.OK_OPTION) {
				return userList.getSelectedValue();
			}
			return null;
		}
		else {
			JPanel userOptions = new JPanel();
			userOptions.setLayout(new GridLayout(0, 2, 3, 3));
			
			JLabel ausername = new JLabel("Username: ");
			JLabel apassword = new JLabel("Password: ");
			JTextField uname = new JTextField(10);
			JTextField pword = new JTextField(10);
			
			userOptions.add(ausername);
			userOptions.add(uname);
			userOptions.add(apassword);
			userOptions.add(pword);
			
			int confirm = JOptionPane.showConfirmDialog(new JFrame(), userOptions, "Input Credentials", JOptionPane.OK_CANCEL_OPTION);
			if(confirm == JOptionPane.OK_OPTION) {
				User a = new User(uname.getText(), pword.getText());
				users.add(a);
				return a;
			}
			return null;
		}
	}
	
	/** Writes out user data represented in the instance ArrayList <i>users</i> to a file named UserList
	 * 
	 */
	public static void writeOut() {
		try {
			FileOutputStream fout = new FileOutputStream("UserList");
			ObjectOutputStream oout = new ObjectOutputStream(fout);
			oout.writeObject(users);
			fout.close();
			oout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	/** Reads in the UserList file and loads data into the instance ArrayList <i>users</i>
	 * 
	 */
	public static void writeIn() {
		try {
			FileInputStream fin = new FileInputStream("UserList");
			ObjectInputStream oin = new ObjectInputStream(fin);
			users = (ArrayList<User>)oin.readObject();
			fin.close();
			oin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
