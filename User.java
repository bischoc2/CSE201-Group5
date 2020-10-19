public class User {
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
