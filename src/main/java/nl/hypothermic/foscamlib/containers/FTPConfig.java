package nl.hypothermic.foscamlib.containers;

import java.net.URLEncoder;

/******************************\
 * > FTPConfig.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class FTPConfig {
	
	/** Full FTP server address with "ftp://" protocol and optional subdirs */
	public final String ftpAddr;
	/** Port of FTP server. */
	public final String ftpPort;
	/** FTP Mode: 0=PASV, 1=PORT */
	public final String mode;

	// What the fuck Foscam? Why is a password visible with only visitor permissions?
	public final String userName;
	public final String password;
	
	/** Construct a FTPConfig
	 * @param ftpAddr
	 * @param ftpPort
	 * @param mode as integer
	 */
	public FTPConfig(final String ftpAddr, final String ftpPort, final String mode, final String userName, final String password) {
		this.ftpAddr = ftpAddr;
		this.ftpPort = ftpPort;
		this.mode = mode;
		this.userName = URLEncoder.encode(userName);
		this.password = URLEncoder.encode(password);
	}
	
	/** Construct a FTPConfig 
	 * @param ftpAddr
	 * @param ftpPort
	 * @param mode as FTPMode instance
	 */
	public FTPConfig(final String ftpAddr, final String ftpPort, final FTPMode mode, final String userName, final String password) {
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
		 */
		public int getValue() {
			return this.value;
		}
	}
}
