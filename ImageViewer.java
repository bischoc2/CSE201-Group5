import java.awt.*;
import javax.swing.*;

public class ImageViewer {

	public ImageViewer(Image img) {
		JFrame iframe = new JFrame();
		JLabel pic = new JLabel(new ImageIcon(img.getPicture()));
		JScrollPane ipane = new JScrollPane(pic, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		ipane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 13));
		ipane.getVerticalScrollBar().setPreferredSize(new Dimension(13, 0));
		iframe.add(ipane);
		iframe.setSize(500, 500);
		iframe.setResizable(false);
		iframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iframe.setVisible(true);
	}

}
