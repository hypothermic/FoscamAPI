package nl.hypothermic.foscamlib;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.hypothermic.foscamlib.containers.AccessPoint;
import nl.hypothermic.foscamlib.containers.Account;
import nl.hypothermic.foscamlib.containers.Credentials;
import nl.hypothermic.foscamlib.containers.DeviceInfo;
import nl.hypothermic.foscamlib.containers.FTPConfig;
import nl.hypothermic.foscamlib.containers.Info485;
import nl.hypothermic.foscamlib.containers.LocalAlarmRecordConfig;
import nl.hypothermic.foscamlib.containers.OSDSettings;
import nl.hypothermic.foscamlib.containers.PTZSelfTestMode;
import nl.hypothermic.foscamlib.containers.PTZSpeed;
import nl.hypothermic.foscamlib.containers.PortInfo;
import nl.hypothermic.foscamlib.containers.SnapConfig;
import nl.hypothermic.foscamlib.containers.SnapConfig.PicQuality;
import nl.hypothermic.foscamlib.containers.SnapConfig.SaveLocation;
import nl.hypothermic.foscamlib.containers.StreamChannel;
import nl.hypothermic.foscamlib.containers.SystemTime;
import nl.hypothermic.foscamlib.containers.SystemTime.DateFormat;
import nl.hypothermic.foscamlib.containers.SystemTime.NTPServer;
import nl.hypothermic.foscamlib.containers.SystemTime.TimeFormat;
import nl.hypothermic.foscamlib.containers.SystemTime.TimeSource;
import nl.hypothermic.foscamlib.containers.WifiConfig;
import nl.hypothermic.foscamlib.containers.WifiConfig.AuthMode;
import nl.hypothermic.foscamlib.containers.WifiConfig.KeyFormat;
import nl.hypothermic.foscamlib.containers.WifiConfig.NetType;
import nl.hypothermic.foscamlib.containers.ZoomSpeed;
import nl.hypothermic.foscamlib.exception.ConnectException;
import nl.hypothermic.foscamlib.net.NetExecutor;
import nl.hypothermic.foscamlib.net.NetManager;
import nl.hypothermic.foscamlib.net.NetParser;

/******************************\
 * > Foscam.java			< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class Foscam {

	private String address;
	private int port;
	private String protocol;
	private Credentials creds;
	private final NetParser p = new NetParser();
	private final NetManager nm;
	private final NetExecutor x;

	/** Connect to a Foscam IP-camera without HTTPS
	 * @param address
	 * @param port
	 * @param user
	 * @param password
	 * @throws ConnectException
	 */
	public Foscam(String address, int port, String user, String password) throws ConnectException {
		this(address, port, user, password, false);
	}
	
	/** Connect to a Foscam IP-camera
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
		x = new NetExecutor();
		if (https) {
			this.protocol = "https";
			nm = new NetManager(x, p, "https://" + address + ":" + port, this.creds);
		} else {
			this.protocol = "http";
			nm = new NetManager(x, p, "http://" + address + ":" + port, this.creds);
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
	public boolean setBrightness(final int brightness) {
		if (brightness < 0 || brightness > 100) {
			return false;
		}
		RxData out = nm.exec("setBrightness", new HashMap<String, String>() {{ put("brightness", brightness + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set contrast of video"
	 * @param contrast (int 0-100)
	 * @return boolean if change succeeded or not
	 */
	public boolean setContrast(final int contrast) {
		if (contrast < 0 || contrast > 100) {
			return false;
		}
		RxData out = nm.exec("setContrast", new HashMap<String, String>() {{ put("contrast", contrast + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set hue of video"
	 * @param hue (int 0-100)
	 * @return boolean if change succeeded or not
	 */
	public boolean setHue(final int hue) {
		if (hue < 0 || hue > 100) {
			return false;
		}
		RxData out = nm.exec("setHue", new HashMap<String, String>() {{ put("hue", hue + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set saturation of video"
	 * @param hue (int 0-100)
	 * @return boolean if change succeeded or not
	 */
	public boolean setSaturation(final int saturation) {
		if (saturation < 0 || saturation > 100) {
			return false;
		}
		RxData out = nm.exec("setSaturation", new HashMap<String, String>() {{ put("saturation", saturation + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Set sharpness of video"
	 * @param sharpness (int 0-100)
	 * @return boolean if change succeeded or not
	 */
	public boolean setSharpness(final int sharpness) {
		if (sharpness < 0 || sharpness > 100) {
			return false;
		}
		RxData out = nm.exec("setSharpness", new HashMap<String, String>() {{ put("sharpness", sharpness + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * "Reset color parameters to default value"
	 * @return boolean if change succeeded or not
	 */
	public boolean resetImageSetting() {
		RxData out = nm.exec("resetImageSetting", null);
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * Get mirror state
	 * @return Boolean (true=mirrored, false=not mirrored, null=error)
	 */
	public Boolean isMirrored() {
		RxData out = nm.exec("getMirrorAndFlipSetting", null);
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
		RxData out = nm.exec("getMirrorAndFlipSetting", null);
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
	public boolean setMirrored(final boolean state) {
		RxData out = nm.exec("mirrorVideo", new HashMap<String, String>() {{ put("isMirror", (state ? 1 : 0) + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * Set flip state
	 * @param state (true=mirrored, false=not mirrored)
	 * @return boolean if change succeeded or not
	 */
	public boolean setFlipped(final boolean state) {
		RxData out = nm.exec("flipVideo", new HashMap<String, String>() {{ put("isFlip", (state ? 1 : 0) + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/**
	 * Get frame shipping reference mode of H264 encoding stream
	 * @return Boolean (true=flipped, 1=two seperated - four skipped , null=error)
	 */
	public Integer getH264FrameRefMode() {
		RxData out = nm.exec("getH264FrmRefMode", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return Integer.parseInt(p.getTagValue(out.xml, "mode"));
	}
	
	/**
	 * Set frame shipping reference mode of H264 encoding stream
	 * @param state (0=normal ref, 1=two seperated - four skipped)
	 * @return boolean if change succeeded or not
	 */
	public boolean setH264FrameRefMode(final int mode) {
		if (mode < 0 || mode > 1) {
			return false;
		}
		RxData out = nm.exec("setH264FrmRefMode", new HashMap<String, String>() {{ put("mode", mode + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's LAN IP address
	 * @return This Foscam's IP address as String (null if none)
	 */
	public String getIP() {
		RxData out = nm.exec("getIPInfo", null);
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
		RxData out = nm.exec("getIPInfo", null);
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
		RxData out = nm.exec("getIPInfo", null);
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
		RxData out = nm.exec("getIPInfo", null);
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
		RxData out = nm.exec("getIPInfo", null);
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
		RxData out = nm.exec("getIPInfo", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isDHCP") == "1";
	}
	
	/** 
	 * Get if this Foscam supports onvif.
	 * @return True if camera supports onvif and false if it doesn't.
	 * @deprecated see the official method: isOnvifSupported()
	 */
	public Boolean doesCameraSupportOnvif() {
		/*RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		// From docs: "onvifPort param only for onvif camera, the camra which does not has onvif function does not have this param"
		if (out.xml.contains("onvifPort")) {
			return true;
		} else {
			return false;
		}*/
		return this.isOnvifSupported();
	}
	
	/** 
	 * Get if this Foscam supports RTSP.
	 * Warning: RTSP is not listed inside of the CGI docs. Use with caution!
	 * @return True if camera supports onvif and false if it doesn't.
	 * @deprecated renamed to isRtspSupported()
	 */
	public Boolean doesCameraSupportRtsp() {
		/*RxData out = nm.exec("getPortInfo");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		if (out.xml.contains("rtspPort")) {
			return true;
		} else {
			return false;
		}*/
		return this.isRtspSupported();
	}
	
	/** 
	 * Get PortInfo for this Foscam.
	 * @return PortInfo container with information
	 */
	public PortInfo getPortInfo() {
		RxData out = nm.exec("getPortInfo", null);
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
	 * @return True if success, false if not succeeded, null if error.
	 */
	public Boolean setPortInfo(final PortInfo pi) {
		if (pi.webPort == null || pi.mediaPort == null || pi.httpsPort == null) {
			return null;
		}
		RxData out;
		if (pi.onvifPort != null || pi.rtspPort == null) {
			// Data is for supports Onvif but not RTSP
			out = nm.exec("getPortInfo", new HashMap<String, String>() 
			{{
					put("webPort", pi.webPort);
					put("mediaPort", pi.mediaPort);
					put("httpsPort", pi.httpsPort);
					put("onvifPort", pi.onvifPort);
			}});
		} else if (pi.onvifPort == null || pi.rtspPort != null) {
			// Data is for RTSP but not Onvif
			out = nm.exec("getPortInfo", new HashMap<String, String>() 
			{{
					put("webPort", pi.webPort);
					put("mediaPort", pi.mediaPort);
					put("httpsPort", pi.httpsPort);
					put("rtspPort", pi.rtspPort);
			}});
		} else if (pi.onvifPort != null || pi.rtspPort != null) {
			// Data is for both RTSP and Onvif
			out = nm.exec("getPortInfo", new HashMap<String, String>() 
			{{
					put("webPort", pi.webPort);
					put("mediaPort", pi.mediaPort);
					put("httpsPort", pi.httpsPort);
					put("onvifPort", pi.onvifPort);
					put("rtspPort", pi.rtspPort);
			}});
		} else {
			// Data is only for standard ports
			out = nm.exec("getPortInfo", new HashMap<String, String>() {{
					put("webPort", pi.webPort);
					put("mediaPort", pi.mediaPort);
					put("httpsPort", pi.httpsPort);
			}});
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
		RxData out = nm.exec("getPortInfo", null);
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
		RxData out = nm.exec("getPortInfo", null);
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
		RxData out = nm.exec("getPortInfo", null);
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
		RxData out = nm.exec("getPortInfo", null);
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
		RxData out = nm.exec("getPortInfo", null);
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
		RxData out = nm.exec("getUPnPConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isEnable").contains("1");
	}
	
	/** 
	 * Set Foscam's UPnP state
	 * @return True if succeeded
	 */
	public Boolean setUPnP(final boolean state) {
		RxData out = nm.exec("setUPnPConfig", new HashMap<String, String>() {{ put("isEnable", (state ? 1 : 0) + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Set Foscam's FTP config
	 * @return True if succeeded
	 */
	public Boolean setFTPConfig(final FTPConfig ftpc) {
		RxData out = nm.exec("setFtpConfig", new HashMap<String, String>() 
										{{
											 put("ftpAddr", ftpc.ftpAddr);
											 put("ftpPort", ftpc.ftpPort);
											 put("mode", ftpc.mode);
											 put("userName", ftpc.userName);
											 put("password", ftpc.password);
										}});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Test Foscam's FTP config (experimental, use at own risk)
	 * @return True if succeeded, false if not succeeded, null if http-get not succeeded or invalid.
	 */
	public Boolean testFTPConfig(final FTPConfig ftpc) {
		RxData out = nm.exec("testFtpConfig", new HashMap<String, String>() 
										{{
											 put("ftpAddr", ftpc.ftpAddr);
											 put("ftpPort", ftpc.ftpPort);
											 put("mode", ftpc.mode);
											 put("userName", ftpc.userName);
											 put("password", ftpc.password);
										}});
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
		RxData out = nm.exec("getFtpConfig", null);
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
		RxData out = nm.exec("getP2PEnable", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "enable").contains("1");
	}
	
	/** 
	 * Set Foscam's P2P state
	 * @return True if succeeded
	 */
	public Boolean setP2P(final boolean state) {
		RxData out = nm.exec("setP2PEnable", new HashMap<String, String>() {{ put("enable", (state ? 1 : 0) + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's P2P port
	 * @return True if succeeded
	 */
	public String getP2PPort(String value) {
		RxData out = nm.exec("getP2PPort", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "port");
	}
	
	/** 
	 * Set Foscam's P2P port
	 * @return True if succeeded
	 */
	public Boolean setP2PPort(final String value) {
		RxData out = nm.exec("setP2PPort", new HashMap<String, String>() {{ put("port", value + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Force open Foscam's infra LED
	 * @return True if succeeded, false is not succeeded, null if error in http-get
	 */
	public Boolean openInfraLed() {
		RxData out = nm.exec("openInfraLed", null);
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
		RxData out = nm.exec("closeInfraLed", null);
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
		RxData out = nm.exec("getInfraLedConfig", null);
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
	public Boolean setInfraLedMode(final int mode) {
		if (mode < 0 || mode > 1) {
			return null;
		}
		RxData out = nm.exec("setInfraLedConfig", new HashMap<String, String>() {{ put("mode", mode + ""); }});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Get Foscam's infra led mode
	 * @return "0" for auto or "1" for manual if succeeded, otherwise null
	 */
	public String getName() {
		RxData out = nm.exec("getDevName", null);
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
	public Boolean setName(final String value) {
		RxData out = nm.exec("setDevName", new HashMap<String, String>() {{ put("devName", value); }});
		return out.result == Result.SUCCESS;
	}
	
	/** 
	 * Snap a picture and get raw jpeg data (experimental)
	 * @return Raw jpeg image data.
	 * @throws IOException 
	 */
	// Don't use nm.exec for this one, we want raw data.
	public String snapPicture() throws IOException {
		return x.get(nm.addr + "cmd=snapPicture2&usr=" + creds.user + "&pwd=" + creds.password);
	}
	
	/** 
	 * Reboot the camera
	 * @return True if reboot initiated
	 */
	public Boolean rebootSystem() {
		RxData out = nm.exec("rebootSystem", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Restore to Factory Settings (untested, because I don't want to reset my camera :P)
	 * @return True if restore initiated
	 */
	public Boolean restoreToFactorySettings() {
		RxData out = nm.exec("restoreToFactorySetting", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Export camera's settings into a file
	 * @return True if export initiated
	 */
	public Boolean exportConfig() {
		RxData out = nm.exec("exportConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/**
	 * Default URL to the exported config.
	 * @return URL in a String.
	 */
	public String exportedConfigURL() {
		return protocol + "://" + address + ":" + port + "/configs/export/configs.bin";
	}
	
	/**
	 * Get log entries
	 * @return URL in a String.
	 */
	public ArrayList<String> getLogEntries(int count, final int offset) {
		// See limitations in user guide.
		if (offset < 0 || offset > 980 || count < 1 || count > 19) {
			return null;
		}
		RxData out = nm.exec("getLog", new HashMap<String, String>() 
									{{
										put("offset", offset + "");
									    put("count", "0");
									}});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		ArrayList list = new ArrayList<String>();
		count = Integer.parseInt(p.getTagValue(out.xml, "curCnt").trim());
		while (count > 0) {
			// Subtract before use because our log starts at 0.
			count--;
			list.add(p.getTagValue(out.xml, "log" + count));
		}		
		return list;
	}
	
	/** 
	 * Get Foscam's main video stream type
	 * @return Main video stream type
	 */
	public String getMainVideoStreamType() {
		RxData out = nm.exec("getMainVideoStreamType", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "streamType");
	}
	
	/** 
	 * Get Foscam's sub video stream type
	 * @return Sub video stream type
	 */
	public String getSubVideoStreamType() {
		RxData out = nm.exec("getSubVideoStreamType", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "streamType");
	}
	
	/**
	 * Set Foscam's main video stream type
	 * @param streamType (int 0-3)
	 * @return True if succeeded
	 */
	public Boolean setMainVideoStreamType(final int streamType) {
		if (streamType < 0 || streamType > 3) {
			return null;
		}
		RxData out = nm.exec("setMainVideoStreamType", new HashMap<String, String>() {{ put("streamType", streamType + ""); }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/**
	 * Set Foscam's main video stream type<br><br>
	 * > 0 = H264<br>
	 * > 1 = MotionJpeg
	 * @param format (0=H264, 1=MJ)
	 * @return True if succeeded
	 */
	public Boolean setSubStreamFormat(final int format) {
		if (format < 0 || format > 1) {
			return null;
		}
		RxData out = nm.exec("setSubStreamFormat", new HashMap<String, String>() {{ put("format", format + ""); }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Set Foscam's stream channel for the scheduled recording
	 * @return Main video stream type
	 */
	public StreamChannel getScheduleRecordStreamChn() {
		RxData out = nm.exec("getScheduleRecordStreamChn", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return StreamChannel.match(p.getTagValue(out.xml, "chn"));
	}
	
	/**
	 * Set Foscam's stream channel for the scheduled recording
	 * @param streamType (int 0-3)
	 * @return True if succeeded
	 */
	public Boolean setScheduledRecordStreamChn(final StreamChannel sc) {
		RxData out = nm.exec("setScheduleRecordStreamChn", new HashMap<String, String>() {{ put("chn", sc.getValue() + ""); }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's MotionJpeg stream URL
	 * @return This Foscam's MotionJpeg stream URL as String
	 */
	// Hardcoded, should be the same for every camera.
	public String getMJStreamURL() {
		return protocol + "://" + address + ":" + port + "/cgi-bin/CGIStream.cgi?cmd=GetMJStream";
	}
	
	/** 
	 * Get Foscam's device info
	 * @return DeviceInfo object
	 */
	public OSDSettings getOSDSettings() {
		RxData out = nm.exec("getOSDSetting", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		// Not all camera's support isEnableTempAndHumid because it's not in the user guide
		if (out.xml.contains("isEnableTempAndHumid")) {
			return new OSDSettings(p.getTagValue(out.xml, "isEnableTimeStamp"), p.getTagValue(out.xml, "isEnableTempAndHumid"),
								   p.getTagValue(out.xml, "isEnableDevName"), p.getTagValue(out.xml, "dispPos"), p.getTagValue(out.xml, "isEnableOSDMask"));
		} else {
			return new OSDSettings(p.getTagValue(out.xml, "isEnableTimeStamp"), null, p.getTagValue(out.xml, "isEnableDevName"), 
								   p.getTagValue(out.xml, "dispPos"), p.getTagValue(out.xml, "isEnableOSDMask"));
		}
	}
	
	/**
	 * Set camera's OSD setting from OSDSetting object
	 * @param osd = OSDSettings object
	 * @return True if succeeded
	 */
	public Boolean setOSDSettings(final OSDSettings osd) {
		RxData out;
		// If camera supports isEnableTempAndHumid (we can only determine it from OSDSetting preset)
		if (osd.isEnableTempAndHumid == null) {
			out = nm.exec("setOSDSetting", new HashMap<String, String>()
										{{
										   put("isEnableTimeStamp", osd.isEnableTimeStamp);
										   put("isEnableDevName", osd.isEnableDevName);
										   put("dispPos", osd.dispPos);
										   put("isEnableOSDMask", osd.dispPos);
										}});
		} else {
			out = nm.exec("setOSDSetting", new HashMap<String, String>()
										{{
										   put("isEnableTimeStamp", osd.isEnableTimeStamp);
										   put("isEnableTempAndHumid", osd.isEnableTempAndHumid);
										   put("isEnableDevName", osd.isEnableDevName);
										   put("dispPos", osd.dispPos);
										   put("isEnableOSDMask", osd.dispPos);
										}});
		}
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Test if camera's OSD mask is enabled
	 * @return Whether camera's OSD mask is enabled
	 */
	public Boolean isOSDMaskEnabled() {
		RxData out = nm.exec("getOSDMask", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isEnableOSDMask").contains("1");
	}
	
	/**
	 * Set Foscam's OSD mask status
	 * @param state (true/false)
	 * @return True if succeeded
	 */
	public Boolean setOSDMaskState(final boolean state) {
		final int osdstate = state ? 1 : 0;
		RxData out = nm.exec("setOSDMask", new HashMap<String, String>() {{ put("isEnableOSDMask", osdstate + ""); }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/**
	 * Add user account
	 * @param Account object (with at least username, password and privilege assigned)
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean addAccount(final Account account) {
		if (account.username == null || account.password == null) {
			return false;
		}
		RxData out = nm.exec("addAccount", new HashMap<String, String>()
										{{
										   put("usrName", account.username);
										   put("usrPwd", account.password);
										   put("privilege", account.privilege.getValue() + "");
										}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Add user account
	 * @param Credentials object
	 * @param Privilege enum instance
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean addAccount(final Credentials creds, final Account.Privilege privilege) {
		RxData out = nm.exec("addAccount", new HashMap<String, String>()
										{{
										   put("usrName", creds.user);
				  						   put("usrPwd", creds.password);
				  						   put("privilege", privilege.getValue() + "");
										}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Delete user account
	 * @param Account object with username that needs to be deleted
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean deleteAccount(final Account account) {
		if (account.username == null) {
			return false;
		}
		RxData out = nm.exec("delAccount", new HashMap<String, String>() {{ put("usrName", account.username); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Delete user account
	 * @param Credentials object with username that needs to be deleted
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean deleteAccount(final Credentials creds) {
		RxData out = nm.exec("delAccount", new HashMap<String, String>() {{ put("usrName", creds.user); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Delete user account
	 * @param Username of user that needs to be deleted
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean deleteAccount(final String username) {
		RxData out = nm.exec("delAccount", new HashMap<String, String>() {{ put("usrName", username); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change password of user account
	 * @param account = Account object that requires password change (with at least username and password assigned)
	 * @param newPassword = New password of user account
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean changePassword(final Account account, final String newPassword) {
		if (account.username == null || account.password == null || account.password == newPassword) {
			return false;
		}
		RxData out = nm.exec("changePassword", new HashMap<String, String>()
											{{
											   put("usrName", account.username);
											   put("oldPwd", account.password);
											   put("newPwd", newPassword);
											}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change password of user account
	 * @param creds = Credentials object that requires password change
	 * @param newPassword = New password of user account
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean changePassword(final Credentials creds, final String newPassword) {
		if (creds.password == newPassword) {
			return false;
		}
		RxData out = nm.exec("changePassword", new HashMap<String, String>()
											{{
											   put("usrName", creds.user);
											   put("oldPwd", creds.password);
											   put("newPwd", newPassword);
											}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change password of user account
	 * @param username = Username of user that requires password change
	 * @param oldPassword = Old (current) password of user account
	 * @param newPassword = New password of user account
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean changePassword(final String username, final String oldPassword, final String newPassword) {
		if (oldPassword == newPassword) {
			return false;
		}
		RxData out = nm.exec("changePassword", new HashMap<String, String>()
											{{
											   put("usrName", username);
											   put("oldPwd", oldPassword);
											   put("newPwd", newPassword);
											}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change username of user account
	 * @param account = Account object that requires username change (with at least username assigned)
	 * @param newUsername = New username of user account
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean changeUsername(final Account account, final String newUsername) {
		if (account.username == null || account.username == newUsername) {
			return false;
		}
		RxData out = nm.exec("changeUserName", new HashMap<String, String>()
											{{
											   put("usrName", account.username);
				   							   put("newUsrName", newUsername);
											}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change username of user account
	 * @param creds = Credentials object that requires username change
	 * @param newUsername = New username of user account
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean changeUsername(final Credentials creds, final String newUsername) {
		if (creds.user == null || creds.user == newUsername) {
			return false;
		}
		RxData out = nm.exec("changeUserName", new HashMap<String, String>()
											{{
											   put("usrName", creds.user);
				   							   put("newUsrName", newUsername);
											}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/**
	 * Change username of user account
	 * @param oldUsername = Old (current) username of user account
	 * @param newUsername = New username of user account
	 * @return True if succeeded, false if not succeeded
	 */
	public boolean changeUsername(final String oldUsername, final String newUsername) {
		if (oldUsername == newUsername) {
			return false;
		}
		RxData out = nm.exec("changeUserName", new HashMap<String, String>()
											{{
											   put("usrName", oldUsername);
											   put("newUsrName", newUsername);
											}});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get user account list of the camera
	 * @return {@literal List<String>} with user accounts
	 */
	public List<String> getUserList() {
		RxData out = nm.exec("getUserList", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		List<String> l = new ArrayList<String>();
		for (int n = 0; n < (Integer.parseInt(p.getTagValue(out.xml, "usrCnt"))); n++) {
			l.add(p.getTagValue(out.xml, "usr" + (n + 1)));
		}
		return l;
	}
	
	/** 
	 * Get user account list of the camera with indexes
	 * @return {@literal HashMap<Integer (index), String (username)>} with user accounts
	 */
	public HashMap<Integer, String> getUserListWithIndexes() {
		RxData out = nm.exec("getUserList", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		HashMap l = new HashMap<Integer, String>();
		for (int n = 0; n < (Integer.parseInt(p.getTagValue(out.xml, "usrCnt"))); n++) {
			l.put(n + 1, p.getTagValue(out.xml, "usr" + (n + 1)));
		}
		return l;
	}
	
	/** 
	 * Get current session list of the camera
	 * @return {@literal List<String>} with logged in user accounts
	 */
	public List<String> getSessionList() {
		RxData out = nm.exec("getSessionList", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		List<String> l = new ArrayList<String>();
		for (int n = 0; n < (Integer.parseInt(p.getTagValue(out.xml, "usrCnt"))); n++) {
			l.add(p.getTagValue(out.xml, "usr" + (n + 1)));
		}
		return l;
	}
	
	/** 
	 * Get current session list of the camera with indexes
	 * @return {@literal HashMap<Integer (index), String (username)>} with logged in user accounts
	 */
	public HashMap<Integer, String> getSessionListWithIndexes() {
		RxData out = nm.exec("getSessionList", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		HashMap l = new HashMap<Integer, String>();
		for (int n = 0; n < (Integer.parseInt(p.getTagValue(out.xml, "usrCnt"))); n++) {
			l.put(n + 1, p.getTagValue(out.xml, "usr" + (n + 1)));
		}
		return l;
	}
	
	/** 
	 * PTZ: Move up
	 * @return True if moved up
	 */
	public boolean ptzMoveUp() {
		RxData out = nm.exec("ptzMoveUp", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move down
	 * @return True if moved down
	 */
	public boolean ptzMoveDown() {
		RxData out = nm.exec("ptzMoveUp", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move left
	 * @return True if moved left
	 */
	public boolean ptzMoveLeft() {
		RxData out = nm.exec("ptzMoveLeft", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move right
	 * @return True if moved right
	 */
	public boolean ptzMoveRight() {
		RxData out = nm.exec("ptzMoveRight", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move top-left
	 * @return True if moved top-left
	 */
	public boolean ptzMoveTopLeft() {
		RxData out = nm.exec("ptzMoveTopLeft", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move top-right
	 * @return True if moved top-right
	 */
	public boolean ptzMoveTopRight() {
		RxData out = nm.exec("ptzMoveTopRight", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move bottom-left
	 * @return True if moved bottom-left
	 */
	public boolean ptzMoveBottomLeft() {
		RxData out = nm.exec("ptzMoveBottomLeft", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Move bottom-right
	 * @return True if moved bottom-right
	 */
	public boolean ptzMoveBottomRight() {
		RxData out = nm.exec("ptzMoveBottomRight", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Stop moving
	 * @return True if stopped moving
	 */
	public boolean ptzStopMoving() {
		RxData out = nm.exec("ptzStopRun", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * PTZ: Reset to default position
	 * @return True if reset to default position
	 */
	public boolean ptzResetPosition() {
		RxData out = nm.exec("ptzReset", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's PTZ move speed
	 * @return PTZSpeed enum entry instance
	 */
	public PTZSpeed getPTZSpeed() {
		RxData out = nm.exec("getPTZSpeed", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return PTZSpeed.match(Integer.parseInt(p.getTagValue(out.xml, "speed")));
	}
	
	/**
	 * Set Foscam's PTZ move speed
	 * @param speed = PTZSpeed enum entry instance
	 * @return True if succeeded
	 */
	public boolean setPTZSpeed(final PTZSpeed speed) {
		if (speed == null) {
			return false;
		}
		RxData out = nm.exec("setPTZSpeed", new HashMap<String, String>() {{ put("speed", speed.getValue() + ""); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Zoom in <br>
	 * Only if camera has zoom lens and supports zoom
	 * @return True if moved bottom-left
	 */
	public boolean zoomIn() {
		RxData out = nm.exec("zoomIn", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Zoom out
	 * Only if camera has zoom lens and supports zoom
	 * @return True if moved bottom-right
	 */
	public boolean zoomOut() {
		RxData out = nm.exec("zoomOut", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Stop zoom run
	 * Only if camera has zoom lens and supports zoom
	 * @return True if stopped moving
	 */
	public boolean zoomStop() {
		RxData out = nm.exec("zoomStop", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's zoom speed
	 * @return ZoomSpeed enum entry instance
	 */
	public ZoomSpeed getZoomSpeed() {
		RxData out = nm.exec("getZoomSpeed", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return ZoomSpeed.match(Integer.parseInt(p.getTagValue(out.xml, "speed")));
	}
	
	/**
	 * Set Foscam's zoom speed
	 * @param speed = ZoomSpeed entry
	 * @return True if succeeded
	 */
	public boolean setZoomSpeed(final ZoomSpeed speed) {
		if (speed == null) {
			return false;
		}
		RxData out = nm.exec("setZoomSpeed", new HashMap<String, String>() {{ put("speed", speed.getValue() + ""); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's PTZ self-test mode
	 * @return PTZSelfTestMode enum entry instance
	 */
	public PTZSelfTestMode getPTZSelfTestMode() {
		RxData out = nm.exec("getPTZSelfTestMode", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return PTZSelfTestMode.match(Integer.parseInt(p.getTagValue(out.xml, "mode")));
	}
	
	/**
	 * Set Foscam's PTZ self-test mode
	 * @param mode = PTZSelfTestMode entry
	 * @return True if succeeded
	 */
	public boolean setPTZSelfTestMode(final PTZSelfTestMode mode) {
		RxData out = nm.exec("setPTZSelfTestMode", new HashMap<String, String>() {{ put("mode", mode.getValue() + ""); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's presentpoint for selftest of PTZ
	 * @return String with name of presetpoint
	 */
	public String getPTZPrePointForSelfTest() {
		RxData out = nm.exec("getPTZPrePointForSelfTest", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "name");
	}
	
	/**
	 * Set Foscam's presetpoint for selftest of PTZ
	 * @param name = Name of presetpoint
	 * @return True if succeeded
	 */
	public boolean setPTZPrePointForSelfTest(final String name) {
		RxData out = nm.exec("setPTZPrePointForSelfTest", new HashMap<String, String>() {{ put("mode", name ); }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's 485 information<br><br>
	 * <b>NOTE:</b> Only supported by ipc that can enable 485
	 * @return Info485 object
	 */
	public Info485 get485Info() {
		RxData out = nm.exec("get485Info", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new Info485(p.getTagValue(out.xml, "rs485Protocol"),
						   p.getTagValue(out.xml, "rs485Addr"),
						   p.getTagValue(out.xml, "rs485Baud"),
						   p.getTagValue(out.xml, "rs485DataBit"),
						   p.getTagValue(out.xml, "rs485StopBit"),
						   p.getTagValue(out.xml, "rs485Check"));
	}
	
	/**
	 * Set Foscam's 485 information<br><br>
	 * <b>NOTE:</b> Only supported by ipc that can enable 485
	 * @param info = Info485 object
	 * @return True if succeeded
	 */
	public Boolean set485Info(final Info485 info) {
		RxData out = nm.exec("set485Info", new HashMap<String, String>()
										{{
										   put("rs485Protocol", info.protocol);
										   put("rs485Addr", info.address);
										   put("rs485Baud", info.baudRate);
										   put("rs485DataBit", info.dataBit);
										   put("rs485StopBit", info.stopBit);
										   put("rs485Check", info.parity);
										}});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Scan for Wi-Fi access points and refresh the Wi-Fi list. <br><br>
	 * <b>NOTE:</b> This operation may take a while, about 20 seconds or above, the other operation on this device will be <b>blocked</b> during the period.
	 * @return True if succeeded, false if not succeeded.
	 */
	public boolean refreshWifiList() {
		RxData out = nm.exec("refreshWifiList", null);
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Wi-Fi access point list near the camera using default start number <br><br>
	 * <b>NOTE:</b> This feature is untested and unsupported because my camera's don't support it.
	 * @return {@literal List<AccessPoint>} with <b>the first 10 AP's</b>. Use getWifiList(startNo) if you want to get weaker AP's. If an empty list is returned, there are no AccessPoints nearby.
	 */
	public List<AccessPoint> getWifiList() {
		return this.getWifiList(0);
	}
	
	/** 
	 * Get Wi-Fi access point list near the camera <br><br>
	 * <b>NOTE:</b> This feature is untested and unsupported because my camera's don't support it.
	 * @param startNo = Start number of AP list
	 * @return {@literal List<AccessPoint>} with <b>maximum of 10 AP's</b>. Use startNo if you want to get weaker AP's. If an empty list is returned, there are no AccessPoints nearby.
	 */
	public List<AccessPoint> getWifiList(final int startNo) {
		RxData out = nm.exec("getWifiList", new HashMap<String, String>() {{ put("startNo", startNo + ""); }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		List<AccessPoint> l = new ArrayList<AccessPoint>();
		for (int n = 0; n < (Integer.parseInt(p.getTagValue(out.xml, "curCnt"))); n++) {
			l.add(p.parseFromXML(p.getTagValue(out.xml, "ap" + (n + 1))));
		}
		return l;
	}
	
	/** 
	 * Get sorted Wi-Fi access point list near the camera with indexes, using default start number. <br><br>
	 * <b>NOTE:</b> This feature is untested and unsupported because my camera's don't support it.
	 * @return {@literal HashMap<Integer (index), AccessPoint>} with <b>maximum of 10 AP's</b>. Use getWifiList(startNo) if you want to get weaker AP's. If an empty list is returned, there are no AccessPoints nearby.
	 */
	public HashMap<Integer, AccessPoint> getWifiListWithIndexes() {
		return this.getWifiListWithIndexes(0);
	}
	
	/** 
	 * Get sorted Wi-Fi access point list near the camera with indexes, using a user-defined start number (first param) <br><br>
	 * <b>NOTE:</b> This feature is untested and unsupported because my camera's don't support it.
	 * @param startNo = Start number of returned map
	 * @return {@literal HashMap<Integer (index), AccessPoint>} with <b>maximum of 10 AP's</b>. If an empty list is returned, there are no AccessPoints nearby.
	 */
	public HashMap<Integer, AccessPoint> getWifiListWithIndexes(final int startNo) {
		RxData out = nm.exec("getWifiList", new HashMap<String, String>() {{ put("startNo", startNo + ""); }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		HashMap l = new HashMap<Integer, AccessPoint>();
		for (int n = 0; n < (Integer.parseInt(p.getTagValue(out.xml, "curCnt"))); n++) {
			l.put(n + 1, p.parseFromXML(p.getTagValue(out.xml, "ap" + (n + 1))));
		}
		return l;
	}
	
	/** 
	 * Get Foscam's Wifi Configuration<br><br>
	 * @return WifiConfig object
	 */
	public WifiConfig getWifiConfig() {
		RxData out = nm.exec("getWifiConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new WifiConfig("1".equals(p.getTagValue(out.xml, "isEnable")), 
							  "1".equals(p.getTagValue(out.xml, "isUseWifi")),
							  "1".equals(p.getTagValue(out.xml, "isConnected")),
							  p.getTagValue(out.xml, "connectedAP"),
							  p.getTagValue(out.xml, "ssid"),
							  NetType.INFRANET, // Infranet is the only type supported right now according to user guide. Un-hardcode this when it gets supported.
							  AccessPoint.EncryptType.match(Integer.parseInt(p.getTagValue(out.xml, "encryptType"))),
							  p.getTagValue(out.xml, "psk"),
							  AuthMode.match(Integer.parseInt(p.getTagValue(out.xml, "authMode"))),
							  KeyFormat.match(Integer.parseInt(p.getTagValue(out.xml, "keyFormat"))),
							  Integer.parseInt(p.getTagValue(out.xml, "defaultKey")),
							  p.getTagValue(out.xml, "key1"),
							  p.getTagValue(out.xml, "key2"),
							  p.getTagValue(out.xml, "key3"),
							  p.getTagValue(out.xml, "key4"),
							  Integer.parseInt(p.getTagValue(out.xml, "key1Len")),
							  Integer.parseInt(p.getTagValue(out.xml, "key1Len")),
							  Integer.parseInt(p.getTagValue(out.xml, "key1Len")),
							  Integer.parseInt(p.getTagValue(out.xml, "key1Len")));
	}
	
	/**
	 * Set Foscam's Wi-Fi configuration <br><br>
	 * <b>NOTE:</b> Camera will not connect to AP unless you eject your ethernet cable
	 * @param cfg = WifiConfig object
	 * @return True if succeeded
	 */
	public Boolean setWifiConfig(final WifiConfig cfg) {
		RxData out = nm.exec("setWifiSetting", new HashMap<String, String>() 
											   {{
												   put("isEnable", (cfg.isEnabled ? 1 : 0) + "");
												   put("isUseWifi", (cfg.isUseWifi ? 1 : 0) + "");
												   put("ssid", cfg.ssid + "");
												   put("netType", cfg.netType.getValue() + "");
												   put("encryptType", cfg.encryptType.getValue() + "");
												   put("psk", cfg.psk + "");
												   put("authMode", cfg.authMode.getValue() + "");
												   put("keyFormat", cfg.keyFormat.getValue() + "");
												   put("defaultKey", cfg.defaultKey + "");
												   put("key1", cfg.key1);
												   put("key2", cfg.key2);
												   put("key3", cfg.key3);
												   put("key4", cfg.key4);
												   put("key1Len", cfg.key1Len + "");
												   put("key2Len", cfg.key2Len + "");
												   put("key3Len", cfg.key3Len + "");
												   put("key4Len", cfg.key4Len + "");
											   }});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Get if camera's Wi-Fi is enabled
	 * @return True if Wi-Fi is enabled, false if not enabled, null if error
	 */
	public Boolean isWifiEnabled() {
		RxData out = nm.exec("getWifiConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isEnable").contains("1");
	}
	
	/** 
	 * Get if camera's Wi-Fi is currently connected
	 * @return True if Wi-Fi is connected, false if not enabled, null if error
	 */
	public Boolean isWifiConnected() {
		RxData out = nm.exec("getWifiConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isConnected").contains("1");
	}
	
	/** 
	 * Get the SSID of the Access Point which the Foscam is currently connected to.
	 * @return SSID of connected AP
	 */
	public String getWifiApSSID() {
		RxData out = nm.exec("getWifiConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		//return p.getTagValue(out.xml, "connectedAP");  <-- also works, same output
		return p.getTagValue(out.xml, "ssid");
	}
	
	/** 
	 * Get the private key (PSK) of the Access Point which the Foscam is currently connected to.
	 * @return Private key of connected AP
	 */
	public String getWifiApKey() {
		RxData out = nm.exec("getWifiConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "psk");
	}
	
	/** 
	 * Get the system time of the camera.
	 * @return SystemTime object
	 */
	public SystemTime getSystemTime() {
		RxData out = nm.exec("getSystemTime", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new SystemTime(TimeSource.match(p.getTagValue(out.xml, "timeSource")),
							  NTPServer.match(p.getTagValue(out.xml, "ntpServer")),
							  DateFormat.match(p.getTagValue(out.xml, "dateFormat")),
							  TimeFormat.match(p.getTagValue(out.xml, "timeFormat")),
							  Integer.parseInt(p.getTagValue(out.xml, "timeZone")),
							  p.getTagValue(out.xml, "isDst").contains("1"),
							  "", // "dst" is not in use currently.
							  Integer.parseInt(p.getTagValue(out.xml, "year")),
							  Integer.parseInt(p.getTagValue(out.xml, "mon")),
							  Integer.parseInt(p.getTagValue(out.xml, "day")),
							  Integer.parseInt(p.getTagValue(out.xml, "hour")),
							  Integer.parseInt(p.getTagValue(out.xml, "minute")),
							  Integer.parseInt(p.getTagValue(out.xml, "sec")));
	}
	
	/**
	 * Set camera's system time <br><br>
	 * @param st = SystemTime object
	 * @return True if succeeded, false if not succeeded or error
	 */
	public boolean setSystemTime(final SystemTime st) {
		RxData out = nm.exec("setSystemTime", new HashMap<String, String>() 
											  {{  
												  put("timeSource", st.timeSource.getValue() + "");
											      put("ntpServer", st.ntpServer.getValue());
											      put("dateFormat", st.dateFormat.getValue() + "");
											      put("timeFormat", st.timeFormat.getValue() + "");
											      put("timeZone", st.timeZone + "");
											      put("isDst", (st.isDST ? 1 : 0) + "");
											      put("dst", st.dst);
											      put("year", st.year + "");
											      put("mon", st.month + "");
											      put("day", st.day + "");
											      put("hour", st.hour + "");
											      put("minute", st.minute + "");
											      put("sec", st.second + "");
											  }});
		if (out.result != Result.SUCCESS) {
			return false;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's motion detect configuration
	 * @return MotionDetectConfig object (or null if not succeeded)
	 */
	// Camera gives ACCESS_DENIED, even though authenticated.
	// Put this method on hold for now.
	/*public String getMotionDetectConfig() {
		RxData out = nm.exec("getMotionDetectConfig");
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "linkage");
	}*/
	
	/**
	 * Set Foscam's motion detect configuration
	 * @param mdc = MotionDetectConfiguration object
	 * @return True if succeeded
	 */
	// Camera gives ACCESS_DENIED, even though authenticated.
	// Put this method on hold for now.
	/*public Boolean setMotionDetectConfig(MotionDetectConfig mdc) {
		RxData out = nm.exec("setMotionDetectConfig", "isEnable", mdc.isEnable,
													  "linkage", mdc.linkage,
													  "snapInterval", mdc.snapInterval,
													  "triggerInterval", mdc.triggerInterval);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}*/
	
	/** 
	 * Get Foscam's local alarm-record config
	 * @return LocalAlarmRecordConfig object
	 */
	public LocalAlarmRecordConfig getLocalAlarmRecordConfig() {
		RxData out = nm.exec("getLocalAlarmRecordConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new LocalAlarmRecordConfig(p.getTagValue(out.xml, "isEnableLocalAlarmRecord"), 
										  p.getTagValue(out.xml, "localAlarmRecordSecs"));
	}
	
	/**
	 * Set Foscam's local alarm-record config
	 * @param LocalAlarmRecordConfig object
	 * @return True if succeeded
	 */
	public Boolean setLocalAlarmRecordConfig(final LocalAlarmRecordConfig larc) {
		RxData out = nm.exec("setLocalAlarmRecordConfig", new HashMap<String, String>() 
														{{
															put("isEnableLocalAlarmRecord", larc.isEnabled);
															put("localAlarmRecordSecs", larc.recordSecs);
														}});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's snap configuration
	 * @return SnapConfig object
	 */
	public SnapConfig getSnapConfig() {
		RxData out = nm.exec("getSnapConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new SnapConfig(PicQuality.fromInt(Integer.parseInt(p.getTagValue(out.xml, "snapPicQuality").trim())), 
							  SaveLocation.fromInt(Integer.parseInt(p.getTagValue(out.xml, "saveLocation").trim())));
	}
	
	/**
	 * Set Foscam's snap configuration
	 * @param SnapConfig object
	 * @return True if succeeded
	 */
	public Boolean setSnapConfig(final SnapConfig sc) {
		RxData out = nm.exec("setSnapConfig", new HashMap<String, String>()
											{{
												put("snapPicQuality", sc.pq.getValue() + "");
												put("saveLocation", sc.sl.getValue() + "");
											}});
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return true;
	}
	
	/** 
	 * Get Foscam's device info
	 * @return DeviceInfo object
	 */
	public DeviceInfo getDeviceInfo() {
		RxData out = nm.exec("getDevInfo", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return new DeviceInfo(p.getTagValue(out.xml, "productName"), p.getTagValue(out.xml, "serialNo"), p.getTagValue(out.xml, "devName"),
							  p.getTagValue(out.xml, "mac"), p.getTagValue(out.xml, "year"), p.getTagValue(out.xml, "mon"),
							  p.getTagValue(out.xml, "day"), p.getTagValue(out.xml, "hour"), p.getTagValue(out.xml, "min"), p.getTagValue(out.xml, "sec"),
							  p.getTagValue(out.xml, "timeZone"), p.getTagValue(out.xml, "firmwareVer"), p.getTagValue(out.xml, "hardwareVer"));
	}
	
	/** 
	 * Get Foscam's product model
	 * @return Camera model number
	 */
	public String getProductModel() {
		RxData out = nm.exec("getProductModel", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "model");
	}
	
	/** 
	 * Get Foscam's product model name
	 * @return Camera model name
	 */
	public String getProductModelName() {
		RxData out = nm.exec("getProductModelName", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "modelName");
	}
	
	/** 
	 * Get Foscam's product language
	 * @return Camera main language
	 */
	public String getProductLanguage() {
		RxData out = nm.exec("getProductLanguage", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "language");
	}
	
	/** 
	 * Get Foscam's product sensor type
	 * @return Camera sensor type number
	 */
	public String getProductSensorType() {
		RxData out = nm.exec("getProductSensorType", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "sensorType");
	}
	
	/** 
	 * Get Foscam's product wifi type
	 * @return Camera wifi type number
	 */
	public String getProductWifiType() {
		RxData out = nm.exec("getProductWifiType", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "wifiType");
	}
	
	/** 
	 * Test if camera supports SD card.
	 * @return Whether camera supports SD card
	 */
	public Boolean isSdcardSupported() {
		RxData out = nm.exec("getProductSdFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "sdFlag").contains("1");
	}
	
	/** 
	 * Test if camera is outdoor machine.
	 * @return Whether camera is outdoor machine
	 */
	public Boolean isProductOutdoorModel() {
		RxData out = nm.exec("getProductOutdoorFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "OutdoorFlag").contains("1");
	}
	
	/** 
	 * Test if camera is pt machine.
	 * @return Whether camera is pt machine
	 */
	public Boolean isProductPtModel() {
		RxData out = nm.exec("getProductPtFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "ptFlag").contains("1");
	}
	
	/** 
	 * Test if camera is zoom machine.
	 * @return Whether camera is zoom machine
	 */
	public Boolean isProductZoomModel() {
		RxData out = nm.exec("getProductZoomFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "zoomFlag").contains("1");
	}
	
	/** 
	 * Test if rs485 is supported
	 * @return Whether rs485 is supported
	 */
	public Boolean isRs485Supported() {
		RxData out = nm.exec("getProductRs485Flag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "rs485Flag").contains("1");
	}
	
	/** 
	 * Test if IO alarm is supported
	 * @return Whether IO alarm is supported
	 */
	public Boolean isIoAlarmSupported() {
		RxData out = nm.exec("getProductIoAlarmFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "ioAlarmFlag").contains("1");
	}
	
	/** 
	 * Test if Onvif is supported
	 * @return Whether Onvif is supported
	 */
	public Boolean isOnvifSupported() {
		RxData out = nm.exec("getProductOnvifFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "onvifFlag").contains("1");
	}
	
	/** 
	 * Test if P2P is supported
	 * @return Whether P2P is supported
	 */
	public Boolean isP2PSupported() {
		RxData out = nm.exec("getProductP2pFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "p2pFlag").contains("1");
	}
	
	/** 
	 * Test if WPS is supported
	 * @return Whether WPS is supported
	 */
	public Boolean isWPSSupported() {
		RxData out = nm.exec("getProductWpsFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "wpsFlag").contains("1");
	}
	
	/** 
	 * Test if audio-speak is supported
	 * @return Whether audio-speak is supported
	 */
	public Boolean isAudioSupported() {
		RxData out = nm.exec("getProductAudioFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "audioFlag").contains("1");
	}
	
	/** 
	 * Test if audio-talk is supported
	 * @return Whether audio-talk is supported
	 */
	public Boolean isTalkSupported() {
		RxData out = nm.exec("getProductTalkFlag", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "talkFlag").contains("1");
	}
	
	/** 
	 * Get if this Foscam supports RTSP.
	 * Warning: RTSP is not listed inside of the CGI docs. Use with caution!
	 * @return True if camera supports onvif and false if it doesn't.
	 */
	public Boolean isRtspSupported() {
		RxData out = nm.exec("getPortInfo", null);
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
	 * Get camera's firewall status
	 * @return True if firewall is enabled
	 */
	public Boolean isFirewallEnabled() {
		RxData out = nm.exec("getFirewallConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "isEnable").contains("1");
	}
	
	/** 
	 * Get camera's firewall rule
	 * @return Returns rule (0=blacklist, 1=whitelist)
	 */
	public String getFirewallRule() {
		RxData out = nm.exec("getFirewallConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "rule").trim();
	}
	
	/** 
	 * Get specific entry in camera's firewall IP list.
	 * @param number - number of entry
	 * @return Returns IP address in String (can be empty if none is set)
	 */
	public String getFirewallEntry(int number) {
		if (number < 0 || number > 7) {
			return null;
		}
		RxData out = nm.exec("getFirewallConfig", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "ipList" + number).trim();
	}
	
	/** 
	 * Get camera's application version
	 * @return Camera application version
	 */
	public String getProductAppVer() {
		RxData out = nm.exec("getProductAppVer", null);
		if (out.result != Result.SUCCESS) {
			return null;
		}
		return p.getTagValue(out.xml, "appVer");
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
