import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageTest {

	@Test
	void testGetPicture() {
		try {
		Image a = new Image("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg");
		assertEquals(1200, a.getPicture().getHeight());
		assertEquals(804, a.getPicture().getWidth());
		assertEquals(-7370935, a.getPicture().getRGB(100, 100));
		}
		catch(Exception e) {
			
		}
	}
	
	@Test
	void testMirror() {
		try {
		Image a = new Image("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg");
		int preRGB = a.getPicture().getRGB(402, 100);
		int preRGB2 = a.getPicture().getRGB(412, 60);
		a.mirror();
		assertEquals(preRGB, a.getPicture().getRGB(401, 100));
		assertEquals(preRGB2, a.getPicture().getRGB(391, 60));
		}
		catch(Exception e) {
			
		}
	}
	
	@Test
	void testGetPath() {
		try {
		Image a = new Image("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg");
		assertEquals("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg", a.getPath());
		}
		catch(Exception e) {
			
		}
		
	}
	
	@Test
	void testToString() {
		try {
		Image a = new Image("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg");
		assertEquals(a.toString(), "character.jpg");
		}
		catch(Exception e) {
			
		}
	}
	
	@Test
	void testSaturation() {
		try {
		Image a = new Image("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg");
		a.saturation(10);
		assertEquals(-7369080, a.getPicture().getRGB(100, 100));
		}
		catch(Exception e) {
			
		};
	}
	
	@Test
	void testRestore() {
		try {
		Image a = new Image("C:\\Users\\joshb\\Documents\\1RedHawk\\character.jpg");
		assertTrue(a.restore());
		}
		catch(Exception e) {
			
		}
	}
}
