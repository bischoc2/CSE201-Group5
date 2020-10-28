import static org.junit.Assert.*;

import org.junit.Test;

public class UserTester {

	@Test
	public void testUser() {
		User u = new User("Patricia", "1234");
		assertNotNull(u);
	}
	@Test
	public void testLogin() {
		User u = new User("Patricia", "1234");
		assertFalse(u.login("Patricia", "1235"));
		
		assertTrue(u.login("Patricia", "1234"));
	}


}
