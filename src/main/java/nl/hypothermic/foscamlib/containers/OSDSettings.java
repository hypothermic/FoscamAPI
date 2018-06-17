package nl.hypothermic.foscamlib.containers;

/******************************\
 * > OSDSettings.java		< *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

public class OSDSettings {
	
	/** Is time stamp shown on OSD */
	public String isEnableTimeStamp;
	/** Warning: unofficial feature. Not in the user guide.
	Might not work on all devices. */
	public String isEnableTempAndHumid;
	/** Is camera name on OSD */
	public String isEnableDevName;
	/** "OSD display position, currently can only be 0" */
	public String dispPos;
	/** Is OSD mask effective */
	public String isEnableOSDMask;
	
	public OSDSettings(String isEnableTimeStamp, String isEnableTempAndHumid, String isEnableDevName, String dispPos, String isEnableOSDMask) {
		this.isEnableTimeStamp = isEnableTimeStamp;
		this.isEnableTempAndHumid = isEnableTempAndHumid;
		this.isEnableDevName = isEnableDevName;
		this.dispPos = dispPos;
		this.isEnableOSDMask = isEnableOSDMask;
	}

	@Override public String toString() {
		return "OSDSettings [isEnableTimeStamp=" + this.isEnableTimeStamp + ", isEnableTempAndHumid=" + this.isEnableTempAndHumid + ", isEnableDevName=" + this.isEnableDevName + ", dispPos=" + this.dispPos + ", isEnableOSDMask=" + this.isEnableOSDMask + "]";
	}
}
