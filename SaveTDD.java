import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class SaveTDD {

	@Test
	public void testSaveImage() throws IOException {
		Image image = new Image("redhawk.jpg");
		ImageLibrary lib = new ImageLibrary();
		lib.uploadImage();
		image.saturation(50);
		
		int pre = lib.album.size();
		
		assertTrue(lib.saveImage(image));
		
		assertTrue(pre < lib.album.size());
		
	}
}
