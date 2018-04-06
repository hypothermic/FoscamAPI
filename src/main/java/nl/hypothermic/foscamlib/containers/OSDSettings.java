package nl.hypothermic.foscamlib.containers;

public class OSDSettings {
	
	/** Is time stamp shown on OSD */
	public final String isEnableTimeStamp;
	/** Warning: unofficial feature. Not in the user guide.
	Might not work on all devices. */
	public final String isEnableTempAndHumid;
	/** Is camera name on OSD */
	public final String isEnableDevName;
	/** "OSD display position, currently can only be 0" */
	public final String dispPos;
	/** Is OSD mask effective */
	public final String isEnableOSDMask;
	
	public OSDSettings(final String isEnableTimeStamp, final String isEnableTempAndHumid, final String isEnableDevName, final String dispPos, final String isEnableOSDMask) {
		this.isEnableTimeStamp = isEnableTimeStamp;
		this.isEnableTempAndHumid = isEnableTempAndHumid;
		this.isEnableDevName = isEnableDevName;
		this.dispPos = dispPos;
		this.isEnableOSDMask = isEnableOSDMask;
	}
}
