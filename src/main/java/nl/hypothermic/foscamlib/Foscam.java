package nl.hypothermic.foscamlib;

import java.net.URLEncoder;

import nl.hypothermic.foscamlib.exception.ConnectException;
import nl.hypothermic.foscamlib.net.NetExecutor;
import nl.hypothermic.foscamlib.net.NetManager;
import nl.hypothermic.foscamlib.net.NetParser;

public class Foscam {

	private String address;
	private int port;
	private Credentials creds;
	private final NetParser p = new NetParser();
	private final NetManager nm;

	/** Connect to a Foscam without HTTPS
	 * @param address
	 * @param port
	 * @throws ConnectException 
	 */
	public Foscam(String address, int port, String user, String password) throws ConnectException {
		this(address, port, user, password, false);
	}
	
	public Foscam(String address, int port, String user, String password, boolean https) throws ConnectException {
		// Check if address is valid and if device is active
		this.creds = new Credentials(URLEncoder.encode(user), URLEncoder.encode(password));
		if (https) {
			nm = new NetManager(new NetExecutor(), p, "https://" + address + ":" + port, this.creds);
		} else {
			nm = new NetManager(new NetExecutor(), p, "http://" + address + ":" + port, this.creds);
		}
		Result testres = nm.testconn();
		if (testres == Result.INVALID_ADDRESS || testres == Result.INVALID_RESPONSE) {
			throw new ConnectException("Camera unreachable");
		}
		if (testres == Result.ACCESSDENIED) {
			// This error can have many reasons, unfortunately we'll never know what exactly is causing it, because it's camera-sided.
			// Make sure user and passwd are correct if you are getting this error.
			throw new ConnectException("Access denied");
		}
		// Success
		this.address = address;
		this.port = port;
	}
	
	/**
	 * "Set brightness of video"
	 * @param brightness (int 0-100)
	 * @return if change succeeded or not
	 */
	public boolean setBrightness(int brightness) {
		if (brightness < 0 || brightness > 100) {
			return false;
		}
		RxData out = nm.exec("setBrightness", "brightness", brightness + "");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set contrast of video"
	 * @param contrast (int 0-100)
	 * @return if change succeeded or not
	 */
	public boolean setContrast(int contrast) {
		if (contrast < 0 || contrast > 100) {
			return false;
		}
		RxData out = nm.exec("setContrast", "contrast", contrast + "");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set hue of video"
	 * @param hue (int 0-100)
	 * @return if change succeeded or not
	 */
	public boolean setHue(int hue) {
		if (hue < 0 || hue > 100) {
			return false;
		}
		RxData out = nm.exec("setHue", "hue", hue + "");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set saturation of video"
	 * @param hue (int 0-100)
	 * @return if change succeeded or not
	 */
	public boolean setSaturation(int saturation) {
		if (saturation < 0 || saturation > 100) {
			return false;
		}
		RxData out = nm.exec("setSaturation", "saturation", saturation + "");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set sharpness of video"
	 * @param sharpness (int 0-100)
	 * @return if change succeeded or not
	 */
	public boolean setSharpness(int sharpness) {
		if (sharpness < 0 || sharpness > 100) {
			return false;
		}
		RxData out = nm.exec("setSharpness", "sharpness", sharpness + "");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Reset color parameters to default value"
	 * @return if change succeeded or not
	 */
	public boolean resetImageSetting() {
		RxData out = nm.exec("resetImageSetting");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * Get mirror state
	 * @return Boolean (true=mirrored, false=not mirrored, null=error)
	 */
	public Boolean isMirrored() {
		RxData out = nm.exec("getMirrorAndFlipSetting");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isMirror") == "1";
	}
	
	/**
	 * Get flip state
	 * @return Boolean (true=flipped, false=not flipped, null=error)
	 */
	public Boolean isFlipped() {
		RxData out = nm.exec("getMirrorAndFlipSetting");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isFlip") == "1";
	}
	
	/**
	 * Set mirror state
	 * @param state (true=mirrored, false=not mirrored)
	 * @return if change succeeded or not
	 */
	public boolean setMirrored(boolean state) {
		int mirrorstate;
		if (state) {
			mirrorstate = 1;
		} else {
			mirrorstate = 0;
		}
		RxData out = nm.exec("mirrorVideo", "isMirror", mirrorstate + "");
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * Set flip state
	 * @param state (true=mirrored, false=not mirrored)
	 * @return if change succeeded or not
	 */
	public boolean setFlipped(boolean state) {
		int flipstate;
		if (state) {
			flipstate = 1;
		} else {
			flipstate = 0;
		}
		RxData out = nm.exec("flipVideo", "isFlip", flipstate + "");
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's LAN IP address
	 * @return This Foscam's IP address (null if none)
	 */
	public String getIP() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "ip");
	}
	
	/** 
	 * Get Foscam's network gateway address
	 * @return This Foscam's network gateway address (null if none)
	 */
	public String getNetworkGateway() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "gate");
	}
	
	/** 
	 * Get Foscam's subnet mask
	 * @return This Foscam's subnet mask (null if none)
	 */
	public String getNetworkMask() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "mask");
	}
	
	/** 
	 * Get Foscam's primary DNS resolver
	 * @return This Foscam's primary DNS resolver (null if none)
	 */
	public String getNetworkDNSPrimary() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "dns1");
	}
	
	/** 
	 * Get Foscam's secondary DNS resolver
	 * @return This Foscam's secondary DNS resolver (null if none)
	 */
	public String getNetworkDNSSecondary() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "dns2");
	}
	
	/** 
	 * Get Foscam's DHCP status
	 * @return This Foscam's DHCP status (null if none)
	 */
	public Boolean isDHCP() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isDHCP") == "1";
	}
	
	/** 
	 * Get Foscam's URL 
	 * @return This Foscam's URL
	 */
	public String getURL() {
		return "http://" + address + ":" + port;
	}
	
	/** 
	 * Get Foscam's CGI URL 
	 * @return This Foscam's CGI URL
	 */
	public String getInterfaceURL() {
		return "http://" + address + ":" + port + "/cgi-bin/CGIProxy.fcgi";
	}
}
