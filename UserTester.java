import static org.junit.Assert.*;

public class UserTester {

	public void testUser() {
		User u = new User("Patricia", "1234");
		assertNotNull(u);
	}
	
	public void testLogin() {
		User u = new User("Patricia", "1234");
		assertFalse(u.login("Patricia", "1235"));
		
		assertTrue(u.login("Patricia", "1234"));
	}

	

}
