public class User {
	
	/** User class creates users (accounts) with their own image library
	 * 
	 * @author Patrica Lennon
	 * @version 0.2
	 * @since 10-14-2020
	 */
	private String userName;
	private String password;
	private ImageLibrary imagelibrary;
	
	public User(String u, String p) {
		// TODO Auto-generated constructor stub
		userName = u;
		password = p;
		imagelibrary = new ImageLibrary();
	}
	public boolean login(String user, String pass) {
		if(user.equals(this.userName) && pass.equals(this.password)) return true;
		return false;
	}
	
	public ImageLibrary getImageLibrary() {
		return imagelibrary;
	}
}
