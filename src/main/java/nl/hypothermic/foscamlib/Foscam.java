package nl.hypothermic.foscamlib;

import java.net.URLEncoder;

import nl.hypothermic.foscamlib.containers.Credentials;
import nl.hypothermic.foscamlib.containers.FTPConfig;
import nl.hypothermic.foscamlib.containers.PortInfo;
import nl.hypothermic.foscamlib.exception.ConnectException;
import nl.hypothermic.foscamlib.net.NetExecutor;
import nl.hypothermic.foscamlib.net.NetManager;
import nl.hypothermic.foscamlib.net.NetParser;

/** ------------------------ **\
 * > Foscam.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\** ------------------------ **/

public class Foscam {

	private String address;
	private int port;
	private String protocol;
	private Credentials creds;
	private final NetParser p = new NetParser();
	private final NetManager nm;

	/** Connect to a Foscam without HTTPS
	 * @param address
	 * @param port
	 * @param user
	 * @param password
	 * @throws ConnectException
	 */
	public Foscam(String address, int port, String user, String password) throws ConnectException {
		this(address, port, user, password, false);
	}
	
	/** Connect to a Foscam
	 * @param address
	 * @param port
	 * @param user
	 * @param password
	 * @param https
	 * @throws ConnectException
	 */
	public Foscam(String address, int port, String user, String password, boolean https) throws ConnectException {
		// Check if address is valid and if device is active
		this.creds = new Credentials(URLEncoder.encode(user), URLEncoder.encode(password));
		if (https) {
			this.protocol = "https";
			nm = new NetManager(new NetExecutor(), p, "https://" + address + ":" + port, this.creds);
		} else {
			this.protocol = "http";
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return boolean if change succeeded or not
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
	 * @return This Foscam's IP address as String (null if none)
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
	 * @return This Foscam's network gateway address as String (null if none)
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
	 * @return This Foscam's subnet mask as String (null if none)
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
	 * @return This Foscam's primary DNS resolver as String (null if none)
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
	 * @return This Foscam's secondary DNS resolver as String (null if none)
	 */
	public String getNetworkDNSSecondary() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "dns2");
	}
	
	/** 
	 * If Foscam's DHCP mode is enabled.
	 * @return This Foscam's DHCP status as Boolean (null if none)
	 */
	public Boolean isDHCP() {
		RxData out = nm.exec("getIPInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isDHCP") == "1";
	}
	
	/** 
	 * Get if this Foscam supports onvif.
	 * @return True if camera supports onvif and false if it doesn't.
	 */
	public Boolean doesCameraSupportOnvif() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		// From docs: "onvifPort param only for onvif camera, the camra which does not has onvif function does not have this param"
		if (out.xml.contains("onvifPort")) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Get if this Foscam supports RTSP.
	 * Warning: RTSP is not listed inside of the CGI docs. Use with caution!
	 * @return True if camera supports onvif and false if it doesn't.
	 */
	public Boolean doesCameraSupportRtsp() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		if (out.xml.contains("rtspPort")) {
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Get PortInfo for this Foscam.
	 * @return PortInfo container with information
	 */
	public PortInfo getPortInfo() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		String webPort = p.getTagValue(out.xml, "webPort");
		String mediaPort = p.getTagValue(out.xml, "mediaPort");
		String httpsPort = p.getTagValue(out.xml, "httpsPort");
		// Not all camera's support onvif and rtsp, so we return null if this cam doesn't support it.
		String onvifPort = null;
		if (out.xml.contains("onvifPort")) {
			onvifPort = p.getTagValue(out.xml, "onvifPort");
		}
		String rtspPort = null;
		if (out.xml.contains("rtspPort")) {
			rtspPort = p.getTagValue(out.xml, "rtspPort");
		}
		return new PortInfo(webPort, mediaPort, httpsPort, onvifPort, rtspPort);
	}
	
	/** 
	 * Set PortInfo for this Foscam. (experimental, use at own risk).  
	 * If this returns null, please check if sending a packet without Onvif and RTSP params works. Your camera might not support those protocols.
	 * @param pi = PortInfo object with data attached.
	 * @return PortInfo container with information
	 */
	public Boolean setPortInfo(PortInfo pi) {
		RxData out;
		if (pi.webPort == null || pi.mediaPort == null || pi.httpsPort == null) {
			// These three must not be null (thanks Foscam!)
			
		}
		if (pi.onvifPort != null || pi.rtspPort == null) {
			// Data is for supports Onvif but not RTSP
			out = nm.exec("getPortInfo", "webPort", pi.webPort,
					"mediaPort", pi.mediaPort,
					"httpsPort", pi.httpsPort,
					"onvifPort", pi.onvifPort);
		} else if (pi.onvifPort == null || pi.rtspPort != null) {
			// Data is for RTSP but not Onvif
			out = nm.exec("getPortInfo", "webPort", pi.webPort,
					"mediaPort", pi.mediaPort,
					"httpsPort", pi.httpsPort,
					"rtspPort", pi.rtspPort);
		} else if (pi.onvifPort != null || pi.rtspPort != null) {
			// Data is for both RTSP and Onvif
			out = nm.exec("getPortInfo", "webPort", pi.webPort,
					"mediaPort", pi.mediaPort,
					"httpsPort", pi.httpsPort,
					"onvifPort", pi.onvifPort,
					"rtspPort", pi.rtspPort);
		} else {
			// Data is only for standard ports
			out = nm.exec("getPortInfo", "webPort", pi.webPort,
					"mediaPort", pi.mediaPort,
					"httpsPort", pi.httpsPort);
		}
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Quick method for getting the Web Port. You can also get it from getPortInfo().
	 * @return Web Port as String
	 */
	public String getWebPort() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "webPort");
	}
	
	/** 
	 * Quick method for getting the Media Port. You can also get it from getPortInfo().
	 * @return Media Port as String
	 */
	public String getMediaPort() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "mediaPort");
	}
	
	/** 
	 * Quick method for getting the HTTPS Port. You can also get it from getPortInfo().
	 * @return HTTPS Port as String
	 */
	public String getHttpsPort() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "httpsPort");
	}
	
	/** 
	 * Quick method for getting the Onvif Port. You can also get it from getPortInfo().
	 * @return Onvif Port as String (or null if camera doesn't support it)
	 */
	public String getOnvifPort() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		if (out.xml.contains("onvifPort")) {
			return p.getTagValue(out.xml, "onvifPort");
		} else {
			return null;
		}
	}
	
	/** 
	 * Quick method for getting the RTSP Port. You can also get it from getPortInfo().
	 * @return RTSP Port as String (or null if camera doesn't support it)
	 */
	public String getRtspPort() {
		RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		if (out.xml.contains("rtspPort")) {
			return p.getTagValue(out.xml, "rtspPort");
		} else {
			return null;
		}
	}
	
	/** 
	 * Get Foscam's UPnP status.
	 * @return This Foscam's UPnP status as Boolean (null if none)
	 */
	public Boolean isUPnPEnabled() {
		RxData out = nm.exec("getUPnPConfig");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isEnable").contains("1");
	}
	
	/** 
	 * Set Foscam's UPnP state
	 * @return True if succeeded
	 */
	public Boolean setUPnP(boolean state) {
		int upnpstate;
		if (state) {
			upnpstate = 1;
		} else {
			upnpstate = 0;
		}
		RxData out = nm.exec("setUPnPConfig", "isEnable", upnpstate + "");
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Set Foscam's FTP config
	 * @return True if succeeded
	 */
	public Boolean setFTPConfig(FTPConfig ftpc) {
		RxData out = nm.exec("setFtpConfig", "ftpAddr", ftpc.ftpAddr,
											 "ftpPort", ftpc.ftpPort,
											 "mode", ftpc.mode,
											 "userName", ftpc.userName,
											 "password", ftpc.password);
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Test Foscam's FTP config (experimental, use at own risk)
	 * @return True if succeeded, false if not succeeded, null if http-get not succeeded or invalid.
	 */
	public Boolean testFTPConfig(FTPConfig ftpc) {
		RxData out = nm.exec("testFtpConfig", "ftpAddr", ftpc.ftpAddr,
											 "ftpPort", ftpc.ftpPort,
											 "mode", ftpc.mode,
											 "userName", ftpc.userName,
											 "password", ftpc.password);
		if (out.result != Result.SUCCESS) {
			// This is the result of the post
			return null;
		}
		if (!out.xml.contains("testResult")) {
			return null;
		}
			// This is the result of the test
		return p.getTagValue(out.xml, "testResult").contains("0");
	}
	
	/** 
	 * Get Foscam's FTP config
	 * @return FTPConfig object, null if not succeeded.
	 */
	public FTPConfig getFTPConfig() {
		RxData out = nm.exec("getFtpConfig");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new FTPConfig(p.getTagValue(out.xml, "ftpAddr"), p.getTagValue(out.xml, "ftpPort"), p.getTagValue(out.xml, "mode"), p.getTagValue(out.xml, "userName"), p.getTagValue(out.xml, "password"));
	}
	
	/** 
	 * Check if Foscam's P2P is enabled
	 * @return This Foscam's P2P status as Boolean (null if none)
	 */
	public Boolean isP2PEnabled() {
		RxData out = nm.exec("getP2PEnable");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "enable").contains("1");
	}
	
	/** 
	 * Set Foscam's P2P state
	 * @return True if succeeded
	 */
	public Boolean setP2P(boolean state) {
		int p2pstate;
		if (state) {
			p2pstate = 1;
		} else {
			p2pstate = 0;
		}
		RxData out = nm.exec("setP2PEnable", "enable", p2pstate + "");
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's P2P port
	 * @return True if succeeded
	 */
	public String getP2PPort(String value) {
		RxData out = nm.exec("getP2PPort");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "port");
	}
	
	/** 
	 * Set Foscam's P2P port
	 * @return True if succeeded
	 */
	public Boolean setP2PPort(String value) {
		RxData out = nm.exec("setP2PPort", "port", value + "");
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Force open Foscam's infra LED
	 * @return True if succeeded, false is not succeeded, null if error in http-get
	 */
	public Boolean openInfraLed() {
		RxData out = nm.exec("openInfraLed");
		if (out.result != Result.SUCCESS) {
			// This is the result of the post
			return null;
		}
		if (!out.xml.contains("ctrlResult")) {
			return null;
		}
			// This is the result of the test
		return p.getTagValue(out.xml, "ctrlResult").contains("0");
	}
	
	/** 
	 * Force close Foscam's infra LED
	 * @return True if succeeded, false is not succeeded, null if error in http-get
	 */
	public Boolean closeInfraLed() {
		RxData out = nm.exec("closeInfraLed");
		if (out.result != Result.SUCCESS) {
			// This is the result of the post
			return null;
		}
		if (!out.xml.contains("ctrlResult")) {
			return null;
		}
			// This is the result of the test
		return p.getTagValue(out.xml, "ctrlResult").contains("0");
	}
	
	/** 
	 * Get Foscam's infra led mode
	 * @return "0" for auto or "1" for manual if succeeded, otherwise null
	 */
	public String getInfraLedMode() {
		RxData out = nm.exec("getInfraLedConfig");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "mode");
	}
	
	/** 
	 * Set Foscam's infra led mode
	 * @param mode 0 for auto or 1 for manual
	 * @return True if succeeded
	 */
	public Boolean setInfraLedMode(int mode) {
		if (mode < 0 || mode > 1) {
			return null;
		}
		RxData out = nm.exec("setInfraLedConfig", "mode", mode + "");
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's infra led mode
	 * @return "0" for auto or "1" for manual if succeeded, otherwise null
	 */
	public String getName() {
		RxData out = nm.exec("getDevName");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "devName");
	}
	
	/** 
	 * Set Foscam's infra led mode
	 * @param mode 0 for auto or 1 for manual
	 * @return True if succeeded
	 */
	public Boolean setName(String value) {
		RxData out = nm.exec("setDevName", "devName", value);
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's URL 
	 * @return This Foscam's URL as String
	 */
	public String getURL() {
		return protocol + "://" + address + ":" + port;
	}
	
	/** 
	 * Get Foscam's CGI URL 
	 * @return This Foscam's CGI URL as String
	 */
	public String getInterfaceURL() {
		return protocol + "://" + address + ":" + port + "/cgi-bin/CGIProxy.fcgi";
	}
}
