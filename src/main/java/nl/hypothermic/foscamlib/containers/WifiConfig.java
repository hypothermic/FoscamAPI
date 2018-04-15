package nl.hypothermic.foscamlib.containers;

import nl.hypothermic.foscamlib.containers.AccessPoint.EncryptType;

/******************************\
 * > WifiConfig.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class WifiConfig {
	
	// Most javadoc comments are directly from the user guide. I chose not to change them even though the grammar isn't always correct.
	
	/** Enable state */
	public boolean isEnabled;
	/** Use wifi or not */
	public boolean isUseWifi;
	/** Connected state */
	public boolean isConnected;
	/** Connected ap */
	public String connectedAP;
	/** SSID ("name") of the AP */
	public String ssid;
	/** Networking type */
	public NetType netType;
	/** Encryption type */
	public EncryptType encryptType;
	/** Private key */
	public String psk;
	/** Authentication mode */
	public AuthMode authMode;
	/** Key format (ASIC or Hex) */
	public KeyFormat keyFormat;
	/** Default key to use (1-4) */
	public int defaultKey;
	/** Private key #1*/
	public String key1;
	/** Key length for key1 (64 or 128) */
	public int key1Len;
	/** Private key #2*/
	public String key2;
	/** Key length for key2 (64 or 128) */
	public int key2Len;
	/** Private key #3*/
	public String key3;
	/** Key length for key3 (64 or 128) */
	public int key3Len;
	/** Private key #4*/
	public String key4;
	/** Key length for key4 (64 or 128) */
	public int key4Len;
	
	/** Constructor for WifiConfig */
	public WifiConfig(boolean isEnabled, boolean isUseWifi, boolean isConnected, String connectedAP, String ssid,
			NetType netType, EncryptType encryptType, String psk, AuthMode authMode, KeyFormat keyFormat,
			int defaultKey, String key1, String key2, String key3, String key4, int key1Len, int key2Len, int key3Len, int key4Len) {
		this.isEnabled = isEnabled;
		this.isUseWifi = isUseWifi;
		this.isConnected = isConnected;
		this.connectedAP = connectedAP;
		this.ssid = ssid;
		this.netType = netType;
		this.encryptType = encryptType;
		this.psk = psk;
		this.authMode = authMode;
		this.keyFormat = keyFormat;
		this.defaultKey = defaultKey;
		this.key1 = key1;
		this.key1Len = key1Len;
		this.key2 = key2;
		this.key2Len = key2Len;
		this.key3 = key3;
		this.key3Len = key3Len;
		this.key4 = key4;
		this.key4Len = key4Len;
	}

	/**
	 * Enum of Wi-Fi net types with their respective integers attached.
	 */
	public static enum NetType {
		
		// Javadoc comments are from the user guide
		
		/** Infra net */
		INFRANET(0),
		/** Ad-hoc (not supported yet) */
		ADHOC(1);
		
		private final int value;
		
		private NetType(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of NetType instance
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get NetType instance from int
		 */
		public static NetType match(int x) {
	        switch(x) {
	        case 0:
	            return INFRANET;
	        case 1:
	            return ADHOC;
	        }
	        return null;
	    }
	}
	
	/**
	 * Enum of Wi-Fi authenticate modes with their respective integers attached.
	 */
	public static enum AuthMode {
		
		// Javadoc comments are directly from the user guide
		
		/** Open mode */
		OPEN(0),
		/** Shared key */
		SHAREDKEY(1),
		/** Auto mode */
		AUTO(2);
		
		private final int value;
		
		private AuthMode(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of AuthMode instance
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get AuthMode instance from int
		 */
		public static AuthMode match(int x) {
	        switch(x) {
	        case 0:
	            return OPEN;
	        case 1:
	            return SHAREDKEY;
	        case 2:
	        	return AUTO;
	        }
	        return null;
	    }
	}
	
	/**
	 * Enum of Wi-Fi key formats with their respective integers attached.
	 */
	public static enum KeyFormat {
		
		// Javadoc comments are directly from the user guide
		
		/** ASIC */
		ASIC(0),
		/** Hex */
		HEX(1);
		
		private final int value;
		
		private KeyFormat(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of KeyFormat instance
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get KeyFormat instance from int
		 */
		public static KeyFormat match(int x) {
	        switch(x) {
	        case 0:
	            return ASIC;
	        case 1:
	            return HEX;
	        }
	        return null;
	    }
	}
}
