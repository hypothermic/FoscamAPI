package nl.hypothermic.foscamlib.containers;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/******************************\
 * > AccessPoint.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class AccessPoint {
	
	/** SSID of the AP */
	public String ssid;
	/** MAC address of the AP */
	public String mac;
	/** Signal strength (0-100) */
	public int quality;
	/** Is the AP encrypted */
	public boolean isEncrypt;
	/** Encryption type of AP */
	public EncryptType encryptType;
	
	/** Constructor for an AccessPoint 
	 * @param ssid = SSID of the AP
	 * @param mac = MAC address of the AP
	 * @param quality = Signal strength of the AP (must be within 0-100)
	 * @param isEncrypt = Is the AP encrypted
	 * @param encryptType = Encryption type of the AP
	 */
	public AccessPoint(String ssid, String mac, int quality, boolean isEncrypt, EncryptType encryptType) {
		this.ssid = ssid;
		this.mac = mac;
		this.quality = quality;
		this.isEncrypt = isEncrypt;
		this.encryptType = encryptType;
	}

	/**
	 * Enum of Wi-Fi Encrypt Types with their respective integers attached.
	 */
	public static enum EncryptType {
		
		// Javadoc comments are directly from the user guide
		
		/** Open mode */
		OPEN(0),
		/** WEP */
		WEP(1),
		/** WPA */
		WPA(2),
		/** WPA2 */
		WPA2(3),
		/** WPA/WPA2 */
		WPA_WPA2(4);
		
		private final int value;
		
		private EncryptType(final int value) {
			this.value = value;
		}
		
		/**
		 * Returns int value of EncryptType instance
		 * @return Value of EncryptType
		 */
		public int getValue() {
			return this.value;
		}
		
		/**
		 * Get EncryptType instance from int
		 * @param x = int to get EncryptType instance from
		 * @return EncryptType instance
		 */
		public static EncryptType match(int x) {
	        switch(x) {
	        case 0:
	            return OPEN;
	        case 1:
	            return WEP;
	        case 2:
	            return WPA;
	        case 3:
	            return WPA2;
	        case 4:
	            return WPA_WPA2;
	        }
	        return null;
	    }
	}

	@Override public String toString() {
		return "AccessPoint [ssid=" + this.ssid + ", mac=" + this.mac + ", quality=" + this.quality + ", isEncrypt=" + this.isEncrypt + ", encryptType=" + this.encryptType + "]";
	}
}
