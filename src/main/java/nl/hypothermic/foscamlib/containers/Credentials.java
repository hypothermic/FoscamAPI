package nl.hypothermic.foscamlib.containers;

/******************************\
 * > Credentials.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class Credentials {
	
	public final String user;
	public final String password;

	public Credentials(final String user, final String password) {
		this.user = user;
		this.password = password;
	}
}
