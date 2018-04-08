package nl.hypothermic.foscamlib.containers;

/******************************\
 * > PTZSelfTestMode.java	< *
 * FoscamLib by hypothermic	  *
 * www.github.com/hypothermic *
\******************************/

/**
 * Enum of PTZ self-test modes with their respective integers attached.
 */
public enum PTZSelfTestMode {
	
	// Javadoc descriptions of enum entries are directly from user guide.
	
	/** No selftest */
	NONE(0),
	/** Normal selftest */
	NORMAL(1),
	/** After normal selftest, then go to presetpoint-appointed */
	PRESETPOINT(2);
	
	private final int value;
	
	private PTZSelfTestMode(final int value) {
		this.value = value;
	}
	
	/**
	 * Returns int value of PTZSelfTestMode instance
	 * @return Value of PTZSelfTestMode
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Get PTZSelfTestMode instance from int
	 * @param x = int to get PTZSelfTestMode instance from
	 * @return PTZSelfTestMode instance or null if error
	 */
	public static PTZSelfTestMode match(int x) {
        switch(x) {
        case 0:
            return NONE;
        case 1:
            return NORMAL;
        case 2:
            return PRESETPOINT;
        }
        return null;
    }
}