import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
	
	/** User class creates users (accounts) with their own image library
	 * 
	 * @author Patrica Lennon
	 * @version 0.2
	 * @since 10-14-2020
	 */
	private String userName;
	private String password;
	private ImageLibrary imagelibrary;
	
	/** Basic constructor that creates a new User
	 * 
	 * @param u users Username
	 * @param p users Password
	 */
	public User(String u, String p) {
		// TODO Auto-generated constructor stub
		userName = u;
		password = p;
		imagelibrary = new ImageLibrary();
	}
	
	/** Allows the user to log into their account
	 * 
	 * @param user users Username
	 * @param pass users Password
	 * @return <p> true if credentials are correct; user matches the instance variable userName and pass equals </p>
	 * the instance variable password. False if otherwise
	 */
	public boolean login(String user, String pass) {
		if(user.equals(this.userName) && pass.equals(this.password)) return true;
		return false;
	}
	
	/** Return the ImageLibrary for this user
	 * 
	 * @return ImageLibrary object stored in the instance variable imageLibrary
	 */
	public ImageLibrary getImageLibrary() {
		return imagelibrary;
	}
	
	public String toString(){
		return userName;
	}
}
