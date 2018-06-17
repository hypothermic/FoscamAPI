package nl.hypothermic.foscamlib.containers;

/******************************\
 * > Credentials.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class Credentials {
	
	public String user;
	public String password;

	public Credentials(String user, String password) {
		this.user = user;
		this.password = password;
	}

	@Override public String toString() {
		return "Credentials [user=" + this.user + ", password=" + this.password + "]";
	}
}
