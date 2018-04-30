package nl.hypothermic.foscamlib.containers;

/******************************\
 * > CloudConfig.java       < *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class CloudConfig {
	
	// Javadoc comments are directly from the user guide
	
	/** Is cloud enabled (0-1) */
	public final String isEnabled;
	/** Status message from cloud server <br><b>NOTE: </b> Call testCloudServer() to find this */
	public String statusMsg;
	/** Current cloud server */
	public final CloudServer server;
	/** Authorization addr to server */
	public String authAddr;
	/** Authorization code from server */
	public final String authCode;
	/** Access Token from authorization code <br><b>NOTE: </b> Call refreshCloudToken() to find this */
	public String accessToken;
	/** Cloud storage total size <br><b>NOTE: </b> Call refreshCloudQuota() to find this */
	public String quota;
	/** Cloud storage userd size <br><b>NOTE: </b> Call refreshCloudQuota() to find this */
	public String userd;
	
	public CloudConfig(final String isEnabled, final CloudServer server, final String authCode) {
		this.isEnabled = isEnabled;
		this.server = server;
		this.authCode = authCode;
	}
	
	public CloudConfig(final String isEnabled, final CloudServer server, final String authCode, final String authAddr, 
					   final String accessToken, final String quota, final String userd, final String statusMsg) {
		this.isEnabled = isEnabled;
		this.server = server;
		this.authCode = authCode;
		this.authAddr = authAddr;
		this.accessToken = accessToken;
		this.quota = quota;
		this.userd = userd;
		this.statusMsg = statusMsg;
	}
	
	/**
	 * CloudServer enum
	 */
	public static enum CloudServer {
		DROPBOX(1),
		BAIDU(2);
		
		private final int value;
		
		private CloudServer(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of CloudServer instance
		 * @return int value of CloudServer instance
		 */
		public int getValue() {
			return this.value;
		}
		
		public static CloudServer fromInt(int value) {
		      for (CloudServer l : CloudServer.values()) {
		          if (l.value == value) return l;
		      }
		      throw new IllegalArgumentException("Not found.");
		}
		
		public static CloudServer match(String value) {
			if (value.contains("1")) return DROPBOX;
			if (value.contains("2")) return BAIDU;
			return null;
		}
	}
}
