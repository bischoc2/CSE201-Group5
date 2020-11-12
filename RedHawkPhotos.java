

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

/** The RedHawkPhotos class in the driver for the entire RedHawk Photos project. Lets users login and interact with their own image libraries
 *  @author Josha Bonsu
 *  @version 0.2
 *  @since 10-14-2020
 */
public class RedHawkPhotos {
	public static void main (String[] args) {
		User a = new User("alex", "1234");
		
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
}
