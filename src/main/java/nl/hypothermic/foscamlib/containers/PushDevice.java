package nl.hypothermic.foscamlib.containers;

/******************************\
 * > PushDevice.java        < *
 * FoscamAPI by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

/**
 * Enum of Push Device Types with their respective integers attached.
 */
public enum PushDevice {
	
	// Javadoc comments are directly from the user guide
	
	/** Web browsers */
	WEB_BROWSER(1),
	/** PC */
	PC(2),
	/** Android */
	ANDROID(3),
	/** iOS */
	IOS(4),
	/** Windows Phone */
	WINDOWS_PHONE(5);
	
	private final int value;
	
	private PushDevice(final int value) {
		this.value = value;
	}
	
	/**
	 * Returns int value of PushDevice instance
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get PushDevice instance from int
	 * @param x int to get PushDevice instance from
	 * @return PushDevice instance or null if error
	 */
	public static PushDevice match(int x) {
        switch(x) {
        case 1:
            return WEB_BROWSER;
        case 2:
            return PC;
        case 3:
            return ANDROID;
        case 4:
            return IOS;
        case 5:
            return WINDOWS_PHONE;
        }
        return null;
    }
	
	/**
	 * Get PushDevice instance from String
	 * @param value String to get PushDevice instance from
	 * @return PushDevice instance or null if error
	 */
	public static PushDevice match(String value) {
		if (value.contains("1")) return WEB_BROWSER;
		if (value.contains("2")) return PC;
		if (value.contains("3")) return ANDROID;
		if (value.contains("4")) return IOS;
		if (value.contains("5")) return WINDOWS_PHONE;
		return null;
	}
}