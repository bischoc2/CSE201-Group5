import java.io.Serializable;

@SuppressWarnings("serial")
/** User class creates users (accounts) with their own image library
 * 
 * @author Patrica Lennon
 * @version 0.2
 * @since 10-14-2020
 */

public class User implements Serializable {
	
	/**
	 * represents this users username
	 */
	private String userName;
	/**
	 * represents this users password
	 */
	private String password;
	/**
	 * represents the image library that belong this this user, containing all their photos
	 */
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
	
	/**
	 * Return the username of the this user
	 * @return the instance variable userName
	 */
	public String toString(){
		return userName;
	}
}
