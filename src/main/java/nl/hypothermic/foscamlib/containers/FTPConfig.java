package nl.hypothermic.foscamlib.containers;

import java.net.URLEncoder;

/******************************\
 * > FTPConfig.java			< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class FTPConfig {
	
	/** Full FTP server address with "ftp://" protocol and optional subdirs */
	public String ftpAddr;
	/** Port of FTP server. */
	public String ftpPort;
	/** FTP Mode: 0=PASV, 1=PORT */
	public String mode;

	// What the fuck Foscam? Why is a password visible with only visitor permissions?
	/** Username */
	public String userName;
	/** Password */
	public String password;
	
	/** Construct a FTPConfig
	 * @param ftpAddr = FTP address
	 * @param ftpPort = FTP port
	 * @param mode as integer
	 * @param userName = FTP user name
	 * @param password = Password of FTP user
	 */
	public FTPConfig(String ftpAddr, String ftpPort, String mode, String userName, String password) {
		this.ftpAddr = ftpAddr;
		this.ftpPort = ftpPort;
		this.mode = mode;
		this.userName = URLEncoder.encode(userName);
		this.password = URLEncoder.encode(password);
	}
	
	/** Construct a FTPConfig 
	 * @param ftpAddr = FTP remote address
	 * @param ftpPort = FTP remote port
	 * @param mode = FTPMode instance
	 * @param userName = FTP user name
	 * @param password = Password of FTP user
	 */
	public FTPConfig(String ftpAddr, String ftpPort, FTPMode mode, String userName, String password) {
		this.ftpAddr = ftpAddr;
		this.ftpPort = ftpPort;
		this.mode = mode.getValue() + "";
		this.userName = URLEncoder.encode(userName);
		this.password = URLEncoder.encode(password);
	}
	
	/**
	 * Enum of FTP modes with their respective integers attached.
	 */
	public static enum FTPMode {
		PASV(0),
		PORT(1);
		
		private final int value;
		
		private FTPMode(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of FTPMode instance
		 * @return Value of FTPMode instance
		 */
		public int getValue() {
			return this.value;
		}
	}

	@Override public String toString() {
		return "FTPConfig [ftpAddr=" + this.ftpAddr + ", ftpPort=" + this.ftpPort + ", mode=" + this.mode + ", userName=" + this.userName + ", password=" + this.password + "]";
	}
}
