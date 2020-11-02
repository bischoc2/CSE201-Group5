import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class ImageViewer {
	
	private JFrame imageFrame;
	private Image image;
	private JScrollPane imagePane;
	private JLabel imgLabel;

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
		iframe.add(ipane);
		iframe.setSize(500, 800);
		iframe.setResizable(true);
		iframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		imageFrame = iframe;
		imagePane = ipane;
		controlPanelSetUp();
		iframe.setVisible(true);
		
	}
	
	private void controlPanelSetUp() {
		JPanel ctrl = new JPanel();
		ctrl.setSize(500, 300);
		
		//Saturation 
		JTextField sat = new JTextField(6);
		JLabel satText = new JLabel("Saturation");
		sat.setActionCommand("sat");
		
		JButton mirror = new JButton("Mirror");
		mirror.setActionCommand("mir");
		
		JButton restore = new JButton("Restore");
		restore.setActionCommand("rest");
		
		JButton strech = new JButton("Strech Image");
		strech.setActionCommand("str");
		
		JButton mono = new JButton("Monochrome");
		mono.setActionCommand("mono");
		
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
					
				}
				else if (e.getActionCommand().equals("mono")) {
					
				}
				
			}
			
		};
		
		mirror.addActionListener(imgList);
		sat.addActionListener(imgList);
		restore.addActionListener(imgList);
		strech.addActionListener(imgList);
		mono.addActionListener(imgList);
		
		ctrl.add(satText);
		ctrl.add(sat);
		ctrl.add(mirror);
		ctrl.add(restore);
		ctrl.add(strech);
		ctrl.add(mono);
		imageFrame.add(ctrl);
	}
}
