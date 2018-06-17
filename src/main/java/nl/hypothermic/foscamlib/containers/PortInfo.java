package nl.hypothermic.foscamlib.containers;

/******************************\
 * > PortInfo.java			< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class PortInfo {
	
	// Store ports in Strings, not as ints, because we will send them in String format as well.
	// Names are the same as in the User Guide.
	public String webPort;
	public String httpsPort;
	public String mediaPort;
	public String onvifPort;
	public String rtspPort;

	public PortInfo(String webPort, String httpsPort, String mediaPort, String onvifPort, String rtspPort) {
		this.webPort = webPort;
		this.httpsPort = httpsPort;
		this.mediaPort = mediaPort;
		this.onvifPort = onvifPort;
		this.rtspPort = rtspPort;
	}

	@Override public String toString() {
		return "PortInfo [webPort=" + this.webPort + ", httpsPort=" + this.httpsPort + ", mediaPort=" + this.mediaPort + ", onvifPort=" + this.onvifPort + ", rtspPort=" + this.rtspPort + "]";
	}
}
