package nl.hypothermic.foscamlib.containers;

/******************************\
 * > CloudConfig.java       < *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class CloudConfig {
	
	// Javadoc comments are directly from the user guide
	
	/** Is cloud enabled (0-1) */
	public String isEnabled;
	/** Status message from cloud server <br><b>NOTE: </b> Call testCloudServer() to find this */
	public String statusMsg;
	/** Current cloud server */
	public CloudServer server;
	/** Authorization addr to server */
	public String authAddr;
	/** Authorization code from server */
	public String authCode;
	/** Access Token from authorization code <br><b>NOTE: </b> Call refreshCloudToken() to find this */
	public String accessToken;
	/** Cloud storage total size <br><b>NOTE: </b> Call refreshCloudQuota() to find this */
	public String quota;
	/** Cloud storage userd size <br><b>NOTE: </b> Call refreshCloudQuota() to find this */
	public String userd;
	
	public CloudConfig(String isEnabled, CloudServer server, String authCode) {
		this.isEnabled = isEnabled;
		this.server = server;
		this.authCode = authCode;
	}
	
	public CloudConfig(String isEnabled, CloudServer server, String authCode, String authAddr, 
					   String accessToken, String quota, String userd, String statusMsg) {
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

	@Override public String toString() {
		return "CloudConfig [isEnabled=" + this.isEnabled + ", statusMsg=" + this.statusMsg + ", server=" + this.server + ", authAddr=" + this.authAddr + ", authCode=" + this.authCode + ", accessToken=" + this.accessToken + ", quota=" + this.quota + ", userd=" + this.userd + "]";
	}
}
