package nl.hypothermic.foscamlib.containers;

public class PortInfo {
	
	// Store ports in Strings, not in ints, because we will send them in String format as well.
	// Names are the same as in the User Guide.
	public final String webPort;
	public final String httpsPort;
	public final String mediaPort;
	public final String onvifPort;
	public final String rtspPort;

	public PortInfo(final String webPort, final String httpsPort, final String mediaPort, final String onvifPort, final String rtspPort) {
		this.webPort = webPort;
		this.httpsPort = httpsPort;
		this.mediaPort = mediaPort;
		this.onvifPort = onvifPort;
		this.rtspPort = rtspPort;
	}
}
